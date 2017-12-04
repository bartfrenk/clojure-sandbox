(ns sandbox.transducers
  :require [core.async :as a])

(reduce ((map inc) conj) [] [1 2 3])

;; inc is not applied to the first element
(reduce ((map inc) +) [1 2 3])

;; this is like (reduce ((map inc) +) (+) [1 2 3])
(transduce (map inc) + [1 2 3])
