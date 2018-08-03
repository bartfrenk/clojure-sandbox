(ns stack-calc.config
  (:require [aero.core :refer [read-config root-resolver]]
            [environ.core :refer [env]]
            [taoensso.timbre :as log]))

(declare read-profile)

(defn load-config
  "Loads the configuration."
  ([] (load-config (read-profile)))
  ([profile]
   (let [config (read-config "config/config.edn"
                             {:resolver root-resolver
                              :profile profile})]
     (log/infof "Using configuration for profile %s" profile)
     config)))

(defn- read-profile
  "Reads profile from the environment."
  []
  (let [profile (env :profile "dev")]
    (keyword profile)))
