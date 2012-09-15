;; A clock using internal tasks.

(ns example.clock)

(defn start-task
  "Kick off a repeating task. Note the use of a lambda passed to the `Task` constructor."
  [me]
  (let [ticker (js/Task. #(.redraw %) me (.-mgraphics me))]
    (js/post ticker)
    (set! (.-interval ticker) 250)
    (.repeat ticker)
    ;; I don't know why I have to kick it off with `execute`: other examples don't.
    (.execute ticker)))

(defn paint
  "Paint does the work: print the date."
  [me]
  (let [g (.-mgraphics me)]
    (.move_to g 10 30)
    (.set_font_size g 24)
    (.text_path g (str (js/Date.)))
    (.fill g)))

(defn setup
  "Set up all drawing modes, `paint`, and the autowatch state. Start the
   Task."
  [me]
  (let [g (.-mgraphics me)]
    (.init g)
    (set! (.-relative_coords g) 0)      ; Work in pixel coordinates.
    (set! (.-autofill g) 0)
    (set! (.-paint me) (fn [] (paint me)))
    (start-task me)
    (set! (.-autowatch me) 1))
  (let [d (js/Date.)]
    (.post me (str "Loaded example.clock at " d "\n"))))
