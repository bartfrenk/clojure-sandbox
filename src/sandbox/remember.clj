(ns sandbox.remember)


;; slices
(subvec [1 2 3 4] 1 3)

;; check if any elements of a set is contained in a seq
(some #{:a :b} [:a :b])


(def very-lazy (->  (iterate #(do (print \.) (inc %)) 1)
                    rest rest rest))

(println (first very-lazy))

(def s (iterate #(do (print \.) (inc %)) 1))
