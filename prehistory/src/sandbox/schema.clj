(ns sandbox.schema
  (:require [schema.core :as s]
            [schema-generators.complete :as c]
            [schema.coerce :as coerce]
            [schema-generators.generators :as g]))

(def p (s/pred #(> % 0)))

(def ProductId s/Int)

(s/defschema ProductAttributes
  {:product-id ProductId
   :category s/Str
   :uuid s/Uuid
   (s/optional-key :url) s/Str})

(g/generate [ProductAttributes])

(c/complete {:product-id 1} ProductAttributes)


(coerce/string-coercion-matcher "1")



