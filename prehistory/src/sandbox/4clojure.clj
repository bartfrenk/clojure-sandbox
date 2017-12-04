(ns sandbox.4clojure
  (:require [criterium.core :as c]
            [clojure.string :as str]))


(def g (fn [& expr] (loop [[x op y & more] expr]
                      (if op
                        (recur (cons (op x y) more))
                        x))))



(for [[x y] (partition 2 (range 20))]
  (+ x y))

(def g (fn [n coll] (loop [[parts more] [[] coll]]
                      (if (>= (count more) n)
                        (let [[part x] (split-at n more)]
                          (recur [(cons part parts) x]))
                        (into '() parts)))))

(fn f
  ([n xs] (f n xs '()))
  ([n xs acc] ()))


(def g (fn f [n xs] (if (< (count xs) n)
                      '()
                      (cons (take n xs) (f n (drop n xs))))))


(def g (fn [coll]
         (loop [xs coll acc []]
           (if (seq xs)
             (recur (next xs) (cons (first xs) acc))
             (into coll acc)))))

(def g (partial reduce (fn [acc _] (inc acc)) 0))


(def g #(filter odd? %))


(def fib #(take % (map first (iterate (fn [[a b]] [b (+ a b)]) [1 1]))))

(def g #(= (reverse (seq %)) (seq %)))

(def g (fn [v coll] (reduce #(assoc %1 %2 v) {} coll)))

(assoc {} :a 1 :b 2)

(def g (comp (fn f [[x y & tail]]
               (if y
                 (recur (cons (if (< x y) y x) tail))
                 x)) vector))


(def g (fn [& xs] (reduce (fn [x y] (if (< x y) y x)) xs)))

(def g #(apply str (filter (set (map char (range 65 91))) %)))

(def g (fn [lo hi] (take-while #(< % hi) (iterate inc lo))))

(sorted-set (seq "Leeeroy"))

(def g (fn func
         ([xs] (let [v (vec xs)]
                 (func [(first v)] v)))
         ([acc [x y & more]]
          (println x y more)
          (if y
            (if (= x y)
              (recur acc (conj more y))
              (recur (conj acc y) (conj more y)))
            acc))))


(def g (fn f [n] (if (pos? n) (* n (f (dec n))) 1)))


(def g #(if (= %2 1) %1 (apply interleave (repeat %2 %1))))

(def g #(interleave %2 (repeat %1)))



(def g  #(take (* 2 (count %2)) (interleave %2 (repeat %1))))

(def g #(mapcat (partial vector %1) %2))


(def g  #(drop-last (interleave %2 (repeat %1))))

(def g (fn [xs n] (mapcat (partial take (dec n)) (partition-all n xs))))


(def g (fn [& more] (< 1 (count (group-by boolean more)))))



(def g (fn [m n]
         (if (zero? n)
           m
           (recur n (rem m n)))))

(sort [2 1])


(def g (fn [lt x y] (cond (lt x y) :lt (lt y x) :gt :else :eq)))

(def g (fn [n] (fn [x] (reduce * (repeat n x)))))



(def g (comp next (partial iterate (fn [[q r]] [(quot q 10) (rem q 10)]))))

(def g (comp (fn dig [q]
               (if (zero? q)
                 []
                 (conj (dig (quot q 10)) (rem q 10)))) *))


(map #(read-string (str %)) "abcd")

(map (comp read-string str) "123")

(def g (fn [f coll] (reduce #(update %1 %2 cons ))))

(def ^:dynamic x 1)


(def h (fn [bs] (apply + (map * (map #(- (int %) 48M) (reverse bs)) (iterate #(* 2 %) 1M)))))
(def g (fn [bs] (reduce #(+ (* 2 %1) (case %2 \0 0 \1 1)) 0M bs)))

(def g #(map vector % (iterate inc 0)))
(def h #(into () (zipmap % (range (count %)))))


(def g (fn [n] (nth (iterate (fn [r]
                               (concat [1]
                                       (map #(apply + %) (partition 2 1 r)) [1])) [1]) (dec n))))

(def g (fn [f xs] (reduce #(conj %1 (f %2)) []  xs)))

(def g (fn tree? [node]
         (cond
           (nil? node) true
           (sequential? node) (and (= 3 (count node))
                                   (every? tree? (rest node)))
           :else false)))

(def tree '(:a (:b nil nil) nil))

(def no-tree '(:a (:b nil nil)))


(def tree-2 [1 nil [2 [3 nil nil] [4 nil nil]]])


(def g #(count (filter
                 (fn [i]
                   (let [d (->> (iterate
                                  (fn [[q r]] [(quot q 10) (rem q 10)]) [i 0])
                                (take-while (complement (partial every? zero?)))
                                (map second)
                                rest)
                         s (apply + (map * d d))]
                     (< i s)
                     ))
                 %
                 )))


(def g (letfn
           [(gcd [()])]))


(def g (fn f [xs] (if (sequential? xs) (mapcat f xs) [xs])))

(def g (fn [[s r]] {:suit (case s \D :diamond \H :heart \S :spades)
                    :rank (case r \T 8 \J 9 \Q 10 \K 11 \A 12 (- (Integer. (str r)) 2))}))

(def g (fn [v] (iterate #(map (partial apply +) (partition 2 1 [0] (cons 0 %))) v)))

(def g (fn [[val left right]]
         (letfn [(mirror [x]
                   (if (coll? x)
                     (let [[v s d] x]
                       [v (mirror d) (mirror s)])
                     x))]
           (= left (mirror right)))))

(def g (fn [[x & b]] [x b]))


(def g #(let [t (fn t [[v l r]] [v (if r (t r)) (if l (t l))])
              [_ l r] %]
          (= l (t r))))


(def g #(loop [[x & ys] (seq %)]
          (if (seq ys)
            (if (some (partial some x) ys)
              false
              (recur ys))
            true)))

#_nil

(def g #(into {} (for [[k1 m] %
                       [k2 v] m] [[k1 k2] v])))

(def g (fn [& more] (let [d (apply * (map #(if (ratio? %) denominator 1) more))
                          gcd ]
                      (/ (reduce gcd (map (partial * d) more)) d))))


(def g (fn [& more] (letfn [(gcd [m n] (if (zero? n) m (gcd n (rem m n))))
                            (denom [p] (if (ratio? p) (denominator p) 1))
                            (lcm [p q]
                              (let [d (* (denom p) (denom q))]
                                (/ (* p q d d) (gcd (* p d) (* q d)) d)))]
                      (reduce lcm more))))

(fn [& a]
  (reduce
    (fn [x y] (* x (/ y
                      ((fn [a b] (if (zero? b) a (recur b (mod a b)))) x y)))) (seq a)))


(def g (fn [& more] (letfn [(gcd [m n] (if (zero? n) m (recur n (rem m n))))
                            (lcm [p q]
                              (/ (* p q) (gcd p q)))]
                      (reduce lcm more))))


(def g (fn [n xs]
         (let [k (count xs)]
           (map (fn [& more] more) (map #(rem (+ n %) k) (range k)) xs))))


(def g (fn [n xs]
         (let [k (count xs)]
           (map #(nth xs (mod (+ % n) k)) (range k)))))

(def g (fn [xs n]
         (map (range n))))

(def g (comp vals (partial group-by type)))

(def g (fn [xs] (reduce (fn [m x] (assoc m x (inc (get m x 0)))) {} xs)))

(def g (fn [xs] (reduce (fn [[s ys] x] (if (s x) [s ys] [(conj s x) (conj ys x)])) [#{} []] xs)))

(def g (fn [& fns]
         (fn [& x]
           (loop [r x
                  [f & more] (reverse fns)]
             (if (seq more)
               (recur [(apply f r)] more)
               (apply f r))))))

((g rest reverse) [1 2 3 4])


((g zero? #(mod % 8) +) 3 5 7 9)


(def g (fn [& fns]
         (fn [& args]
           (map #(apply % args) fns))))

((g + max min) 2 3 5 1 6 4)


(defn primes [m]
  (lazy-seq (cons m (remove #(zero? (mod % m)) (primes (inc m))))))

(def g
  (fn [n]
    (letfn [(primes [m]
               (lazy-seq (cons m (remove #(zero? (mod % m)) (primes (inc m))))))])
    (take n (primes 2))))

(def g
  (fn [n]
    (take n ((fn primes [m]
               (lazy-seq (cons m (remove #(zero? (mod % m)) (primes (inc m)))))) 2))))


(def g
  (fn [coll]
    (= (conj (rest coll) (first coll)) coll)))


(def g
  #({{} :map #{} :set [] :vector} (empty %) :list))


(def x "1,2,3,4,5,6,7,8,9,10")

(def g
  (fn [s]
    (->> s
         (partition-by #{\,})
         (take-nth 2)
         (map #(apply str %))
         (map #(Integer/parseInt %))
         (filter #(= (mod (Math/sqrt %) 1) 0.0))
         (interpose ",")
         (apply str))))




(g x)

(rseq [1 2])
