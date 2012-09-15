;; Port of Darwin's puffdraw example.

(ns example.puffdraw)

(def TRAIL-LENGTH 50)

(defn bucket-add
  "Cycle a new [but x y] into the point stash. Unlike the original Javascript, we
   add to the front and remove from the end."
  [me but x y]
  (let [old (.-point_stash me)]
    (set! (.-point_stash me) (cons [but x y] (drop-last old)))
    (.redraw (.-mgraphics me))))

(defn stash [me but x y]
  (set! (.-check_stash me) [but x y]))

(defn start-task
  "Kick off a repeating task. Note the use of a lambda passed to the `Task` constructor."
  [me]
  (let [ticker (js/Task. #(bucket-add %
                                      (nth (.-check_stash %) 0)
                                      (nth (.-check_stash %) 1)
                                      (nth (.-check_stash %) 2)) me me)]
    (js/post ticker)
    (set! (.-interval ticker) 300)
    (.repeat ticker)
    ;; I don't know why I have to kick it off with `execute`: other examples don't.
    (.execute ticker)))

(defn paint
  "Paint does the work."
  [me]

  (let [rect (.-rect (.-box me))
        width (- (nth rect 2) (nth rect 0))
        height (- (nth rect 3) (nth rect 1))
        puff-size (/ 1.0 TRAIL-LENGTH)
        points (.-point_stash me)
        g (.-mgraphics me)]

    (.set_source_rgb g 1.0 1.0 1.0)
    (.rectangle g 0 0 width height)
    (.fill g)

    (letfn [(iter [i last-x last-y]
              (let [[but x y] (nth points i)]
                ;; Draw segment if click/drag (first value in step) is positive:
                (when (pos? but)
                  (.set_source_rgba g 0.0 0.0 1.0 (* i puff-size))
                  (.set_line_width g (* 100.0 (- TRAIL-LENGTH i) puff-size))
                  (.move_to g last-x last-y)
                  (.line_to g x y)
                  (.stroke g))
                ;; Iterate through steps:
                (when (< i (- TRAIL-LENGTH 1))
                  (recur (inc i) x y))))]
      (iter 1
            (nth (nth points 0) 1)
            (nth (nth points 0) 2)))))

(defn but-x-y-wrapper
  "Wrap a function taking (me, x, y, but) into one taking all the click/drag/idle
   key modifier args, which is what JSUI expect to call. (We don't care about
   any modifier keys.) Our functions take `me` (a.k.a. `this`) to get hold of
   'global' bindings."
  [me f]
  (fn [x y but cmd shift capslock option ctrl] (f me but x y)))

(defn setup
  "Set up all drawing modes, `paint`, and the autowatch state. Start the
   Task."
  [me]
  (let [g (.-mgraphics me)]
    ;; Application setup:
    (set! (.-check_stash me) [0 0 0])
    (set! (.-point_stash me) (repeat TRAIL-LENGTH [0 0 0]))

    ;; Graphics setup.
    (.init g)
    (set! (.-relative_coords g) 0)      ; Work in pixel coordinates.
    (set! (.-autofill g) 0)
    (set! (.-paint me) (fn [] (paint me)))
    ;; Wrap the mouse handler functions.
    (set! (.-onclick me) (but-x-y-wrapper me bucket-add))
    (set! (.-ondrag me) (but-x-y-wrapper me bucket-add))
    (set! (.-onidle me) (but-x-y-wrapper me stash))
    (set! (.-onidleout me) (but-x-y-wrapper me stash))

    ;; Kick off task:
    #_ (start-task me)
    (set! (.-autowatch me) 1))
  (let [d (js/Date.)]
    (.post me (str "Loaded example.puffdraw at " d "\n"))))
