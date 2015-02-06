(defproject eu.cassiel/jsui-cljs "1.0.1"
  :description "JSUI example for MaxMSP using ClojureScript"
  :url "https://github.com/cassiel/jsui-cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-marginalia "0.8.0"]]
  :cljsbuild {:builds [{:source-paths ["src-cljs"],
                        :id "prod",
                        :compiler {:externs ["externs.js"],
                                   :optimizations :simple,
                                   :output-to "projects/jsui-cljs/code/_main.js",
                                   :pretty-print false}}
                       {:source-paths ["src-cljs"],
                        :id "dev",
                        :compiler {:externs ["externs.js"],
                                   :optimizations :whitespace,
                                   :output-to "projects/jsui-cljs/code/_main-dev.js",
                                   :pretty-print true}}]})
