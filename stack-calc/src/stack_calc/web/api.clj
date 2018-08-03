(ns stack-calc.web.api
  (:require [compojure.api.sweet :refer :all]
            [ring.logger.timbre :refer [wrap-with-logger]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [stack-calc.meta :refer [version]]))

(defn- make-routes
  "Creates the routes."
  []
  (api
    {:swagger
     {:ui "/api-docs"
      :spec "/swagger.json"
      :data {:info {:title "Stack Calc"}
             :tags [{:name "generic"}]}}}
    (context "/" []
      :tags ["generic"]
      (GET "/version" []
        :summary "returns application version"
        (ok {:stack-calc version})))))

(defn make-api
  "Creates the API."
  []
  (-> (make-routes)
      (wrap-params)
      (wrap-with-logger)
      (wrap-json-response)))

