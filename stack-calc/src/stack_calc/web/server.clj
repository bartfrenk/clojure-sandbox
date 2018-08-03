(ns stack-calc.web.server
  (:require  [org.httpkit.server :as web]
             [com.stuartsierra.component :as component]
             [taoensso.timbre :as log]
             [stack-calc.web.api :refer [make-api]]))

(defrecord Server [config server]
  component/Lifecycle

  (start [this]
    (let [serv (web/run-server (make-api) config)]
      (log/infof "Web server started on %s:%s" (:host config) (:port config))
      (assoc this :server serv)))

  (stop [this]
    (when-let [server (:server this)]
      (server)
      (log/infof "Web server stopped"))
    (assoc this :server nil)))

(defn make-server [config]
  (map->Server {:config config}))
