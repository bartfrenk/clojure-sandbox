(ns try-spec.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(s/conform even? 1)


(s/def ::data inst?)

(import java.util.Date)

(s/valid? ::date (Date.))
