(defproject eu.cassiel/jsui-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :plugins [[lein-cljsbuild "0.2.7"]]
  :cljsbuild {:builds
              {:dev  {:source-path "src-cljs"
                      :compiler {:output-to "projects/jsui-cljs/code/_main-dev.js"
                                 :optimizations :whitespace
                                 :pretty-print true}}
               :prod {:source-path "src-cljs"
                      :compiler {:output-to "projects/jsui-cljs/code/_main.js"
                                 :optimizations :advanced
                                 :pretty-print true}}}})
