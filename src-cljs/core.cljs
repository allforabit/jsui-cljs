(ns core
  (:require [example.cross :as c]
            [example.tickle :as t]))

;; Bind `me`, set everything up. (`this-as` doesn't seem well documented;
;; I found some discussion
;; [here](http://www.chris-granger.com/2012/02/20/overtone-and-clojurescript/).)
;;
;; This is a bit hacky: I wanted a single codebase (for Marginalia) but a way
;; of choosing different setup functions per `js`/`jsui` instance, so I'm dispatching
;; on the first JS argument.


(def projects
  "Map project name keywords to their setup functions (which establish `paint` etc.)."
  {:CROSS c/setup
   :TICKLE t/setup})

;; Check jsarguments[1]: if present, map it through `projects` to get a setup function to run.

(this-as me
         (let [arg (nth (.-jsarguments me) 1)]
           (if arg
             (let [setup ((keyword arg) projects)]
               (if setup
                 (setup me)
                 (.post me (str "no matching argument for jsui patcher arg \"" arg "\"\n"))))
             (.post me "jsui patcher argument needed\n"))))
