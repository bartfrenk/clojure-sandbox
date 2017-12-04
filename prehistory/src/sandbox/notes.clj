(ns sandbox.notes
  (:require [sandbox.core :refer [symbol*]]
            [clojure.walk :refer [macroexpand-all]]))

;; References:
;; - Houser, The Joy of Clojure

;; slices
(subvec [1 2 3 4] 1 3)

;; check if any elements of a set is contained in a seq
(some #{:a :b} [:a :b])


(def very-lazy (->  (iterate #(do (print \.) (inc %)) 1)
                    rest rest rest))

(println (first very-lazy))

(def s (iterate #(do (print \.) (inc %)) 1))

;; to hide Clojure functions
;; (ns joy.locks
;;   (:refer-clojure :exclude [aget aset count mseq])
;;   (:require [clojure.core :as clj]))

(def x 42)

;;(.start (Thread. #(println "Answer:" x)))

(def big 991778647261948849222819828311491035886734385827028118707676848307166514)

(def reference sandbox.core/symbol*)

(with-redefs [big 1]
  (println big))


;; (defmacro do-until
;;   [& clauses]
;;   (print (class clauses))
;;   (when clauses
;;      `(when (first '~clauses)
;;        (if (next '~clauses)
;;          (second '~clauses)
;;          (throw (IllegalArgumentException. "error"))))))


(macroexpand '(do-until
                    false (println 1)
                    true 2
                    false 3))

(doseq)

;; (defmacro do-until [& clauses]
;;   (when-let [[first second & rest] clauses]
;;     (list 'when first
;;           (if second
;;             second
;;             (throw (IllegalArgumentException. "do-until requires an even number of forms")))
;;           (cons 'do-until rest))))

(defmacro do-until [& clauses]
  (when-let [[first second & rest] clauses]
    `(when ~first
          ~(or second
            second
            (throw (IllegalArgumentException. "do-until requires an even number of forms")))
          (do-until ~@rest))))

(defmacro def-watched [name value]
  `(do
     (def ~name ~value)
     (add-watch (var ~name)
                :re-bind
                (fn [~'key ~'r old# new#]
                  (println old# " -> " new#)))))


(defn sleeper [s thing] (Thread/sleep (* 1000 s)) thing)

(defn pvs []
  (pvalues
    (sleeper 2 :1st)
    (sleeper 3 :2nd)
    (keyword "3rd")))

(-> (pvs) time)

(->> [1 2 3]
     (pmap (comp inc (partial sleeper 2)))
     doall
     time)

(def ^:dynamic a 1)
(def ^:dynamic b 2)

(defn f
  [c]
  (+ a b c))

(f 3)

(with-precision 4
  (/ 1M 3))

;; very Clojure
;; laziness with dynamic scoping
(with-precision 4
  (map (fn [x] (/ x 3)) (range 1M 4M)))

(with-precision 4
  (map (bound-fn [x] (/ x 3)) (range 1M 4M)))


(binding [a 5 b 6]
  (f 3))

(defonce z 1)


(do-until
  1 (println "a")
  2 (println "b")
  nil (println "c")
  3 (println "d"))

(do-until
  false 1
  true 2)

(defn f
  [& more]
  more)

(f 1 2 3)
