(ns sandbox.modeling
  (:refer-clojure :exclude [resolve]))

(defn fetch-dynamic [dynamic-id])

(defprotocol Dynamic
  (resolve [this impr]))

(defrecord RankedProducts [category source]
  Dynamic
  (resolve [this impr] nil))

(defrecord UniformChoice [dynamic]
  Dynamic
  (resolve [this impr] (resolve dynamic impr)))

(defrecord WeightedChoice [dynamic weights]
  Dynamic
  (resolve [this impr] (resolve dynamic impr)))

(defrecord Filtered [dynamic excluded]
  Dynamic
  (resolve [this impr]))

(defrecord Limited [dynamic limit]
  Dynamic
  (resolve [this impr]))

(def dyn
  (-> (->RankedProducts nil 1)
      (->Filtered [1 5])
      (->Limited 10)))

(defmulti to-cache type)

(defmethod to-cache sandbox.modeling.Limited
  [{:keys [dynamic limit]}]
  (let [{:keys [base bound]} (to-cache dynamic)]
    {:base base :bound (min bound limit)}))

(defmethod to-cache sandbox.modeling.Filtered
  [{:keys [dynamic excluded]}]
  (let [{:keys [base bound]} (to-cache dynamic)]
    {:base base :bound (+ bound (count excluded))}))

(defmethod to-cache sandbox.modeling.RankedProducts
  [dynamic]
  {:base dynamic :bound nil})

