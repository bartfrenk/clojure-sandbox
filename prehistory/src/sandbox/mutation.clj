(ns sandbox.mutation
  (:import [java.util.concurrent Executors]))

;; Notes on Chapter 11. Mutation from The Joy of Clojure
;; 1. Refs
;; 2. Agents
;; 3. Atoms
;; 4. Vars

;; io! macro wrapped expressions give a runtime error in a dosync block
(def main #(io! (println "x")))

(try
  (dosync (main))
  (catch IllegalStateException e
    (println (.getMessage e))))

;; p. 239
;; The use of I/O and instance mutation is often an essential part of many
;; applications; it’s important to work to separate your programs into logical
;; partitions, keeping I/O and its ilk on one side, and transaction processing
;; and mutation on the other.

;; p. 240
;; |              | Ref | Agent | Atom | Var |
;; |--------------+-----+-------+------+-----|
;; | Coordinated  |  x  |       |      |     |
;; | Asynchronous |     |   x   |      |     |
;; | Retriable    |  x  |       |  x   |     |
;; | Thread-local |     |       |      |  x  |

(def ^:dynamic *pool* (Executors/newFixedThreadPool
                        (+ 2 (.availableProcessors (Runtime/getRuntime)))))

(defn dothreads!
  [f & [{thread-count :threads
         exec-count :times
         :or {thread-count 1 exec-count 1}}]]
  (dotimes [t thread-count]
    (.submit *pool* #(dotimes [_ exec-count] (f)))))


(def initial-board
  [[:- :k :-]
   [:- :- :-]
   [:- :K :-]])

(defn board-map
  [f bd]
  (vec (map #(vec (for [s %] (f s))) bd)))

(defn reset!
  []
  (def board (board-map ref initial-board))
  (def to-move (ref [[:K [2 1]] [:k [0 1]]]))
  (def num-moves (ref 0)))

(defn neighbors
  ([size yx] (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size yx))
  ([deltas size yx]
   (filter (fn [new-yx]
             (every? #(< -1 % size) new-yx))
           (map #(map + yx %) deltas))))


(def king-moves
  (partial neighbors
           [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]] 3))

(defn good-move?
  [to enemy-sq]
  (when (not= to enemy-sq) to))

(defn choose-move
  [[[mover mpos] [_ enemy-pos]]]
  [mover (some #(good-move? % enemy-pos)
              (shuffle (king-moves mpos)))])
