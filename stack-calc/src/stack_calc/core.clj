(ns stack-calc.core
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [mount.core :as mount]
            [taoensso.timbre :as log]
            [stack-calc.web.server :refer [make-server]]
            [stack-calc.config :refer [load-config]]
            [stack-calc.meta :refer [version]]))

(declare make-system)

(mount/defstate system
  :start (component/start (make-system (load-config)))
  :stop (component/stop system))

(defn make-system
  [config]
  (component/system-map
    :web (component/using (make-server (:server config)) [])))

(defn add-shutdown-hook
  "Add a shutdown hook"
  [hook]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. hook)))

(defn keep-alive
  "Runs a keep alive thread to prevent boot from exiting."
  []
  (let [t (Thread. (fn []
                     (while (not (Thread/interrupted))
                       (Thread/sleep 60000))))]
    (doto t
      (.setDaemon false)
      (.start)
      (.join))))

(defn init-logging
  "Initialize logging."
  [level]
  (log/set-level! level)
  (log/merge-config! {:output-fn (partial log/default-output-fn
                                          {:stacktrace-fonts {}})}))

(defn -main [& args]
  (init-logging :info)
  (log/infof "Starting stack-calc version %s" version)
  (add-shutdown-hook mount/stop)
  (mount/start)
  (keep-alive))
