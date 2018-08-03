(set-env!
 :source-paths #{"src/cljs"}
 :resource-paths #{"html"}

 :dependencies '[[adzerk/boot-cljs "2.1.4"]])

(require '[adzerk.boot-cljs :refer [cljs]])
