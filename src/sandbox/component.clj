(ns sandbox.component
  (:require [clojure.core.async :refer [chan <! >! go-loop close! >!!]]
            [taoensso.timbre :as log]
            [mount.core :as mount]
            [com.stuartsierra.component :as component]))


(defrecord Channel [capacity label ch]
  component/Lifecycle

  (start [this]
    (log/infof "Starting channel %s" label)
    (assoc this :ch (chan capacity)))

  (stop [this]
    (log/infof "Stopping channel %s" label)
    (close! ch)))

(defn make-channel
  [capacity label]
  (map->Channel {:capacity capacity :label label}))

(defrecord Reader [opts trigger-ch out-ch]
  component/Lifecycle

  (start [this]
   (log/info "Starting reader")
    (let [market (:market opts)]
      (go-loop [i 1]
        (if-let [trigger (<! (:ch trigger-ch))]
          (do (log/infof "Received trigger %s" trigger)
              (>! (:ch out-ch) i)
              (recur (inc i)))
          (log/info "Stopping reader"))))
    this)

  (stop [this] this))

(defn make-reader
  [opts]
  (map->Reader {:opts opts}))

(defrecord Parser [in-ch out-ch]
  component/Lifecycle
  (start [this]
    (log/info "Starting parser")
    (go-loop []
      (if-let [line (<! (:ch in-ch))]
        (do (log/infof "Parser received %s" line)
            (>! (:ch out-ch) line)
            (recur))
        (log/info "Stopping parser")))
    this)

  (stop [this] this))

(defn make-parser
  []
  (map->Parser {}))

(defn push-delta
  [market delta]
  (log/infof "Writing %s for market %s" delta market))

(defrecord Writer [opts in-ch]
  component/Lifecycle

  (start [this]
    (log/infof "Starting writer for market %s" (:market opts))
    (let [market (:market opts)]
      (go-loop []
        (if-let [delta (<! (:ch in-ch))]
          (do (push-delta market delta)
              (recur))
          (log/info "Stopping writer"))))
    this)

  (stop [this] this))

(defn make-writer
  [opts]
  (map->Writer {:opts opts}))

(defn make-system
  [config]
  (component/system-map
    :trigger-ch (make-channel 1 "trigger-ch")
    :reader (component/using
              (make-reader config) {:trigger-ch :trigger-ch
                                    :out-ch :line-ch})
    :line-ch (make-channel 1 "line-ch")
    :parser (component/using
              (make-parser) {:in-ch :line-ch
                             :out-ch :delta-ch})
    :delta-ch (make-channel 1 "delta-ch")
    :writer (component/using
              (make-writer config) {:in-ch :delta-ch})))

(mount/defstate system
  :start (component/start (make-system {:market "bla"}))
  :stop (component/stop system))


(defn trigger
  [trigger]
  (>!! (get-in system [:trigger-ch :ch]) trigger))
