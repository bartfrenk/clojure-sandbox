(ns sicp.sets)

(defn entry
  [tree]
  (get tree 0))

(defn left-branch
  [tree]
  (get tree 1))

(defn right-branch
  [tree]
  (get tree 2))

(defn make-tree
  [entry left right]
  [entry left right])

(defn element-of-set?
  [x set]
  (cond (empty? set) false
        (= x (entry set)) true
        (< x (entry set))
        (element-of-set? x (left-branch set))

        )
  )
