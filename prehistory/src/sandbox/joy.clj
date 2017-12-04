(ns sandbox.joy
  (:require [criterium.core :as c]))

(defn asum-sq [^ints xs]
  (let [^ints dbl (amap xs i ret
                  (* (aget xs i)
                     (aget xs i)))]
    (areduce dbl i ret 0
             (+ ret (aget dbl i)))))

;; 278

(defn shuffle-test [coll]
  (seq (doto (java.util.ArrayList. coll)
         java.util.Collections/shuffle)))


(defn zencat1 [x y]
  (loop [src y ret x]
    (if (seq src)
      (recur (next src) (conj ret (first src)))
      ret)))

(defn zencat2 [x y]
  (loop [src y ret (transient x)]
    (if (seq src)
      (recur (next src) (conj! ret (first src)))
      (persistent! ret))))

(defn concat1 [[x & xs] ys]
  (if x
    (cons x (concat1 xs ys))
    ys))

(defn seq1 [s]
  (lazy-seq
    (when-let [[x] (seq s)]
      (cons x (seq1 (rest s))))))

(defn seq2 [s]
  (lazy-seq
    (when-let [[x] (seq s)]
      (cons x (seq1 (next s))))))


;; 12.4 Memoization

(defprotocol CacheProtocol
  (lookup [cache e])
  (has?   [cache e])
  (hit    [cache e])
  (miss   [cache e ret]))

(deftype BasicCache [cache]
  Object
  (toString [_] (str cache))
  CacheProtocol
  (lookup [_ item]
    (get cache item))
  (has? [_ item]
    (contains? cache item))
  (hit [this item] this)
  (miss [_ item result]
    (BasicCache. (assoc cache item result))))

(deftype PluggableMemoization [f cache]
  CacheProtocol
  (has? [_ item] (has? cache item))
  (hit [this item] this)
  (miss [_ item result]
    (PluggableMemoization. f (miss cache item result)))
  (lookup [_ item]
    (lookup cache item)))

(defn through [cache f item]
  (if (has? cache item)
    (hit cache item)
    (miss cache item (delay (f item)))))



(defn occur-count [words]
  (let [res (atom {})]
    (doseq [w words] (swap! res assoc w (inc (@res w 0))))
    @res))


(defn roll [n d]
  (reduce + (repeatedly n #(inc (rand-int d)))))


(defn length1 [^double x ^double y ^double z]
  (Math/sqrt (+ (* x x) (* y y) (* z z))))





(key (first {:a 1}))
(val (first {:a 1}))
(map key {:a 1 :b 2})

;; Using into to convert the seq back into a vector is O(n), which is less than
;; ideal for every pop .
