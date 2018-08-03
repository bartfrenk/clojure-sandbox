(ns cljs-boot.core
  (:gen-class)
  (:require [clojure.repl :refer :all]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(println {:a "one" :b "two"})
(prn {:a "one" :b "two"})

(source pr)

(binding [*print-dup* true]
  (prn {:a 1 :b 2}))



(binding [*read-eval* false]
  (read-string "#=(clojure.lang.PersistentArrayMap/create {:a 1, :b 2})"))


(read-string "{:a 1 :b 2}")

(spit "/tmp/data.edn" (with-out-str
                        (binding [*print-dup* true
                                  *print-readably* false]
                          (prn h))))

(let [x (read-string (slurp "/tmp/data.edn"))]
  [x (type x)]
  )

(def h (hash-map :a 1 :b 2))

;; See https://dev.clojure.org/jira/browse/CLJ-807
(binding [*print-dup* true]
  (prn (hash-map :a 1 :b 2)))

(defrecord Test [x])

(type (Test. 1))

(defmethod cljs_core.)
