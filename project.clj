(defproject eu.cassiel/jsui-cljs "1.0.1"
  :description "JSUI example for MaxMSP using ClojureScript"
  :url "https://github.com/cassiel/jsui-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]]
  :plugins [[lein-cljsbuild "1.1.2"]
            [lein-marginalia "0.8.0"]]
  :cljsbuild {:builds [{:id "prod",
                        :compiler {:externs ["externs.js"],
                                   :optimizations :simple,
                                   :output-to "projects/jsui-cljs/code/_main.js",
                                   :pretty-print false}}
                       {:id "dev",
                        :compiler {:externs ["externs.js"],
                                   :optimizations :whitespace,
                                   :output-to "projects/jsui-cljs/code/_main-dev.js",
                                   :pretty-print true}}]})
