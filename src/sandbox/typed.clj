(ns typed-clojure.core
  (:require [clojure.core.typed :as t]))

; TODO treat ann as def for indentation
; Cursive Clojure

(t/defalias NInts
  (U nil (t/Coll t/Int)))

(t/ann summarise
       (t/IFn [NInts -> t/Int]
              [NInts t/Int -> t/Int]))

(defn summarise
  ([nseq] (summarise nseq 0))
  ([nseq acc] (if (seq nseq)
                (* (summarise (next nseq)
                              (inc acc))
                   (first nseq))
                42)))

(t/ann get-x
       (t/IFn ['{:x t/Num} -> t/Num]))

(defn get-x
  [m]
  (:x m))

(get-x {:x "a"})

(def x (assoc {} :a 1 :b "foo" :c "baz"))

