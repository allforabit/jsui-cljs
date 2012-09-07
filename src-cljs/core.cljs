(ns core
  (:require [example.cross :as c]))

;; Bind `me`, set everything up. (`this-as` doesn't seem well documented;
;; I found some discussion
;; [here](http://www.chris-granger.com/2012/02/20/overtone-and-clojurescript/).)
;;
;; This is a bit hacky: I wanted a single codebase (for Marginalia) but a way
;; of choosing different setup functions per `js`/`jsui` instance, so I'm dispatching
;; on the first JS argument.

(this-as me
         (let [arg (nth (.-jsarguments me) 1)]
           (cond (= arg "CROSS") (c/setup me)
                 :else (.post me (str "no matching argument for jsui patcher arg \"" arg "\"\n")))))
