(ns sicp.chapter2)

(rest '(a b))

(defn memq [item x]
  (cond (nil? x) false
        true 1))

(defn variable? [x] (symbol? x))

(defn addend [s] (first s))

(defn same-variable? [v1 v2]
  (and (variable? v1) (variable? v2) (= v1 v2)))

(defn make-sum [a1 a2] (list '+ a1 a2))

(defn make-product [m1 m2] (list '* m1 m2))

(defn sum? [x] (and ))

(defn error [msg] (throw (Exception. msg)))

(defn deriv [exp var]
  (cond (number? exp) 0
        (variable? exp) (if (same-variable? exp var) 1 0)
        (sum? exp) (make-sum (deriv (addend exp) var)
                             (deriv (augend exp) var))
        (product? exp) (make-sum
                        (make-product (multiplier exp)
                                      (deriv multiplicand exp) var)
                        (make-product (deriv (multiplier exp) var)
                                      (multiplicand exp)))
        :else (error (str "unknown expression type" exp))))
