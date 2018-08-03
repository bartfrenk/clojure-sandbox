(def project 'cljs-boot)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.9.0"]
                            [adzerk/boot-cljs-repl "0.3.3"]
                            [com.cemerick/piggieback "0.2.1" :scope "test"]
                            [weasel "0.7.0" :scope "test"]
                            [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])


(task-options!
 aot {:namespace   #{'cljs-boot.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/cljs-boot"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'cljs-boot.core
      :file        (str "cljs-boot-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[cljs-boot.core :as app])
  (apply (resolve 'app/-main) args))
