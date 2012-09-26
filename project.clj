(defproject eu.cassiel/jsui-cljs "1.0.0"
  :description "JSUI example for MaxMSP using ClojureScript"
  :url "https://github.com/cassiel/jsui-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :plugins [[lein-cljsbuild "0.2.7"]
            [lein-marginalia "0.7.1"]]
  :cljsbuild {:builds
              {:dev  {:source-path "src-cljs"
                      :compiler {:externs ["externs.js"]
                                 :output-to "projects/jsui-cljs/code/_main-dev.js"
                                 :optimizations :whitespace
                                 :pretty-print true}}
               :prod {:source-path "src-cljs"
                      :compiler {:externs ["externs.js"]
                                 :output-to "projects/jsui-cljs/code/_main.js"
                                 :optimizations :simple
                                 :pretty-print false}}}})
