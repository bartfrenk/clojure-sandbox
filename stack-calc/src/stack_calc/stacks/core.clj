(ns stack-calc.stacks.core
  (:import [java.util.concurrent ConcurrentHashMap]))

(defprotocol StackMap
  (push [i x])
  (pop [i])
  (replace [i n fn])
  (sum [i] (replace i 2 +)))

;; Finding the arity of a function at runtime
;; https://stackoverflow.com/questions/1696693/clojure-how-to-find-out-the-arity-of-function-at-runtime

;; Implement StackMap with java.util.ConcurrentHashMap


(def m (ConcurrentHashMap.))

(.put m 1 [1 2])

(.computeIfPresent m 1 inc)

(ancestors (type inc))

