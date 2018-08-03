(ns next-steps.core
  (:gen-class)
  (:require [clojure.repl :refer :all]
            [clojure.java.io :as io]
            [clojure.pprint :as pp])
  (:import [java.util.concurrent Executors TimeUnit]
           [java.util.concurrent.atomic AtomicLong]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(println {:a "one" :b "two"})
(prn {:a "one" :b "two"})

(source pr)

(binding [*print-dup* true]
  (prn {:a 1 :b 2}))

(def a 1)

(def x)

(def f 3)

(def g f)

(def h #'f)

(println g)

(type g)

(type h)

(ns-unmap *ns* 'f)

(println (var-get h))

(defn fn []
  (println "B"))

(def g "")

(ns-unmap *ns* 'fn)

(def gn fn)

(def hn #'fn)

(type gn)
(type hn)

(gn)
(hn)

(i)
(h)

(type (fn [] (println "A")))

(h)

(type x)

(type (ns-resolve *ns* 'a))

(binding [*read-eval* false]
  (read-string "#=(clojure.lang.PersistentArrayMap/create {:a 1, :b 2})"))

(read-string "{:a 1 :b 2}")

(spit "/tmp/data.edn" (with-out-str
                        (binding [*print-dup* true
                                  *print-readably* false]
                          (prn h))))

(let [x (read-string (slurp "/tmp/data.edn"))]
  [x (type x)])

(def h (hash-map :a 1 :b 2))

;; See https://dev.clojure.org/jira/browse/CLJ-807
(binding [*print-dup* true]
  (prn (hash-map :a 1 :b 2)))

(defrecord Test [x])

(type (Test. 1))

(defmethod print-dup next_steps.core.Test
  [o, ^java.io.Writer w]
  (.write w "Test"))

(def t (Test. 1))

(with-out-str (print-dup t *out*))

(let [url (io/resource "clojure/core.clj")]
  (with-open [r (java.io.PushbackReader. (io/reader url))]
    (dotimes [_ 10]
      (print (read r)))))

(print *1)

(ancestors (class []))

(assoc [] :h 1)

(pp/pprint (ancestors (class [])))

(-> (Executors/newScheduledThreadPool 1)
    (.scheduleAtFixedRate #(prn :tick) 1 1 TimeUnit/SECONDS))

(let [a (AtomicLong. 0)]
  (defn counter []
    (.incrementAndGet a)))

