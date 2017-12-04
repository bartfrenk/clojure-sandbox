(ns sandbox.macros
  (:require [clojure.walk :refer :all]
            [clojure.test :refer :all]
            [schema.core :as s]
            [schema.test :as t]
            [slingshot.slingshot :refer [throw+ try+]]))

(defn retry*
  [n act]
  (when (pos? n)
    (let [res (act)]
      (or res (recur (dec n) act)))))

(defmacro retry
  [n & body]
  `(retry* ~n (fn [] ~@body)))

(defmacro ignore
  [expr]
  nil)

;; from The Joy of Clojure (p. 172)
(defmacro with-resource
  [res-bind close-fn & body]
  `(let ~res-bind
     (try
       (do ~@body)
       (finally
         (~close-fn ~(res-bind 0))))))


(defn clear-test-db
  [con]
  (println "Cleared database" con))

(defn init-test-db
  []
  :con)


(defn countdown
  [n]
  (let [count (atom n)]
    (fn [z]
      (let [x (swap! count dec)]
        (print z)
        (neg? x)))))

(defmacro pre
  ([] true)
  ([pred & next]
   `(if ~pred
      (pre ~@next)
      (throw (IllegalArgumentException. ~(str pred))))))


;; turns on validation for function schemas (otherwise the schema annotations
;; are just handy pieces of documentation).
(s/set-fn-validation! true)

(def Point
  {:x s/Int
   :y s/Int})

(defmacro thrown+?
  [exc & body]
  `(try+
     ~@body
     false
     (catch ~exc {}
       true)
     (catch Object _#
       false)))


(defn boom
  []
  (throw+ {:type ::custom :msg ""}))




(s/defn plus :- Point
  [x :- s/Int y :- s/Int]
  {:x x :y y})


(def Scrape
  {:id s/Int :hash s/Str :dynamic-input-id s/Int :epoch-time s/Int})
