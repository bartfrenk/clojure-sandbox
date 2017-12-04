(ns clj-sandbox.core-test
  (:require [clojure.test :refer :all]
            [clojure.spec.test :as stest]
            [clojure.pprint :as pprint]
            [clj-sandbox.core :as sut]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= zero? 1))))

(defn summarize-results' [spec-check]
  (->> spec-check
       (map stest/abbrev-result)
       (map #(pprint/write % :stream nil))))

(defn check' [spec-check]
  (is (nil? (-> spec-check first :failure))
      (summarize-results' spec-check)))

(deftest ranged-rand-spec (check' (stest/check `ranged-rand)))

(deftest spec-test
  (is (every? (comp nil? :failure)
              (->> (stest/enumerate-namespace 'clj-sandbox.core)
                   stest/check
                   (map stest/abbrev-result)
                   (map #(select-keys % [:sym :failure]))))))
