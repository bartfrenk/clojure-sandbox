(def project 'clj-sandbox)
(def version "0.1.0-SNAPSHOT")


(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.8.0" :scope "provided"]
                            [org.clojure/core.async "0.2.395"]
                            [org.clojure/test.check "0.9.0"]
                            [com.taoensso/timbre "4.8.0"]
                            [clojure-future-spec "1.9.0-alpha14"]
                            [com.stuartsierra/component "0.3.2"]
                            [prismatic/schema-generators "0.1.0"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [criterium "0.4.4"]
                            [prismatic/schema "1.1.3"]
                            [mount "0.1.11"]
                            [slingshot "0.12.2"]])


(task-options!
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/clj-sandbox"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])
