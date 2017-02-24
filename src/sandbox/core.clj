(ns sandbox.core
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as g]
            [clojure.string :as string]
            [clojure.spec.test :as t]))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(s/def ::suit #{:club :diamond :heart :spade})

(s/def ::nat (s/and integer? #(>= % 0)))


(s/def ::name-or-id (s/or :name string?
                          :id integer?))

(s/def ::string-opt (s/nilable string?))


(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")

(s/def ::email-type (s/and string? #(re-matches email-regex %)))

(s/def ::acctid integer?)

(s/def ::first-name string?)

(s/def ::last-name string?)

(s/def ::email ::email-type)

(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))

(s/def :unq/person-too (s/keys :req-un [::first-name ::last-name ::email]))

(s/def ::id (s/and integer? pos?))

(s/def ::product-id ::id)

(s/def ::timestamp (s/and integer? pos?))

(declare byte-array?)

(s/def ::hash (s/and string? #(= 10 (count %))))

(s/def :unq/scrape (s/keys :req-un [::dynamic-input-id ::hash ::timestamp]))

(defn test-array
  [t]
  (let [check (type (t []))]
    (fn [arg] (instance? check arg))
    ))

(s/def :animal/kind string?)
(s/def :animal/says string?)

(s/def :animal/common (s/keys :req [:animal/kind :animal/says]))

(def byte-array?
  (test-array byte-array))

(defn ranged-rand
  "Returns random int in range start <= rand <= end"
  [start end]
  (+ start (long (rand (- start end)))))

(s/fdef ranged-rand
  :args (s/and (s/cat :start ::nat :end ::nat)
               #(< (:start %) (:end %)))
  :ret integer?
  :fn (s/and #(>= (:ret %) (-> % :args :start))
             #(< (:ret %) (-> % :args :end))))

(s/def ::kws (s/with-gen (s/and keyword? #(= (namespace %) "my.domain"))
               #(s/gen #{:my.domain/name :my.domain/occupation :my.domain/id})))

(def kw-gen-2 (gen/fmap #(keyword "my.domain" %) (gen/string-alphanumeric)))


(defn fix-str
  [n]
  (gen/fmap string/join (gen/vector (gen/char-alphanumeric) n)))


(def hex-digit
  (set "0123456789abcdef"))

(defn hex-digit?
  [x]
  (contains? hex-digit x))

(defn hex-str?
  [s]
  (every? hex-digit? (seq s)))

(defn hex-str-gen
  [n]
  (let [digit (gen/elements hex-digit)]
    (gen/fmap string/join (gen/vector digit n))))

(s/def ::hash (s/with-gen (s/and hex-str? #(= (count %) 20))
                #(hex-str-gen 20)))
