;; This example lifted from Darwin Grosse: [Patch-a-day][pad], November 10.
;; [pad]: http://cycling74.com/2011/11/11/november-patch-a-day/

(ns example.surface-work)

(defn bang [me]
  (.redraw js/mgraphics)
  )

(defn paint [me]
  )

(defn setup
  "
Set up the context, including `mgraphics`. Set `autowatch` to
`1` (to allow auto-reloading)
and plant the (arg-less) `paint` function at the top level.

Original `mgraphics` setup code:

    mgraphics.init();
    mgraphics.relative_coords = 0;
    mgraphics.autofill = 0;
"
  [me]
  (.init js/mgraphics)
  (set! (.-relative_coords js/mgraphics) 0)
  (set! (.-autofill js/mgraphics) 0)
  (set! (.-paint me) (fn [] (paint me)))
  (set! (.-bang me) (fn [] (bang me)))
  (set! (.-autowatch me) 1)
  (let [d (js/Date.)]
    (js/post (str "Loaded example.cross at " d "\n"))))
