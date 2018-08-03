(ns logic.core
  (:gen-class)
  (:require [clojure.core.logic :refer [run* membero == conde]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(run* [q]
  (membero q [1 2 3])
  (membero q [3 4 5]))

(run* [q r]
  (== q r))

(run* [q r]
  (fresh [a]
    (== q a)
    (== r a)))


(run* [q]
  (conde
    [(== q 1)]
    [(== q 2)]
    [succeed]
    ))

(run* [q]
  (conde
    [(conso 1 q '(1 2 3))]
    [(conso 1 q '(1 4 5))]))
