#!/usr/bin/env boot

(set-env!
 :resource-paths #{"src"}
 :source-paths #{"test"}
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                 [adzerk/boot-test "1.1.2" :scope "test"]
                 [aero "1.0.1"]
                 [boot/core "2.6.0" :scope "test"]
                 [com.fzakaria/slf4j-timbre "0.3.2"]
                 [com.stuartsierra/component "0.3.2"]
                 [environ "1.1.0"]
                 [http-kit "2.2.0"]
                 [metosin/compojure-api "1.2.0-alpha1"]
                 [metosin/ring-swagger-ui "2.2.8"]
                 [mount "0.1.10"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]
                 [org.slf4j/jul-to-slf4j "1.7.22"]
                 [org.slf4j/jcl-over-slf4j "1.7.22"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]
                 [prismatic/schema "1.1.3"]
                 [ring/ring-json "0.4.0"]
                 [ring-logger-timbre "0.7.5"]])

(require '[adzerk.boot-test :refer [test]]
         '[stack-calc.meta :refer [version]])

(task-options!
 aot {:namespace   #{'stack-calc.core}}
 pom {:project     'stack-calc
      :version     version
      :description "FIXME: write description"}
 jar {:main        'stack-calc.core
      :file        (str "stack-calc-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[stack-calc.core :as app])
  (apply (resolve 'app/-main) args))
