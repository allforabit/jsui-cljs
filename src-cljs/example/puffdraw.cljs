;; Port of Darwin's puffdraw example. We've not done much in terms of
;; structural change apart from reverse the history list (so that we
;; can `cons` the new values).
;; The only other tidy-up we've done is to use a map for the
;; queue entries and the stash, rather than a triplet array,
;; making structuring/destructuring much neater.


(ns example.puffdraw)

(def TRAIL-LENGTH 50)

(defn stash
  "Stash a state."
  [me state]
  (set! (.-check_stash me) state))

(defn bucket-add
  "Cycle a new button/x/y into the point stash and force redraw.
   Unlike the original Javascript, we
   add to the front and remove from the end.
   "
  [me state]
  (let [old (.-point_stash me)]
    ;; Danger Will Robinson: this is a lazy structure and must be forced (in `paint()`).
    (set! (.-point_stash me) (take TRAIL-LENGTH (cons state old)))
    (stash me state)
    (.redraw (.-mgraphics me))))

(defn start-task
  "Kick off a repeating task to add the stash to the queue.
   Note the use of a lambda passed to the `Task` constructor."
  [me]
  (let [ticker (js/Task. #(bucket-add % (.-check_stash %)) me me)]
    (set! (.-interval ticker) 30)
    (.repeat ticker)
    ;; I don't know why I have to kick it off with `execute`: other examples don't.
    (.execute ticker)))

(defn paint
  "Paint does the work. Iterate through the circular queue of states, drawing
   a fat line from each point to the next (if button down).

   In our first version we retained the 'for-loop' structure of the iteration,
   looking at the first `TRAIL-LENGTH` items only. This wasn't forcing evaluation
   of `point_stash` and we were running out of heap or blowing up the garbage
   collector as suspended tails accumulated. This version does a proper traversal
   with a `rest` call to force the tail. Moral of this story: stay idiomatic."
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

    ;; I bet this can be done as a reduce:
    (letfn [(iter [pts i last-x last-y]
              (let [{:keys [button x y]} (first pts)]
                ;; Draw segment if click/drag (first value in step) is positive:
                (when button
                  (.set_source_rgba g 0.0 0.0 1.0 (- 1.0 (* i puff-size)))
                  ;; Adjustment here for the list being in reverse order:
                  (.set_line_width g (- 100.0 (* 100.0 (- TRAIL-LENGTH i) puff-size)))
                  (.move_to g last-x last-y)
                  (.line_to g x y)
                  (.stroke g))
                ;; Danger Will Robinson: (rest '(x)) is (), not nil.
                (let [r (seq (rest pts))]
                  (when r (recur r (inc i) x y)))))]
      (iter points 1
            (:x (first points))
            (:y (first points))))))

(defn button-x-y-wrapper
  "Wrap a function taking (me, {x, y, button}) into one taking all the click/drag/idle
   key modifier args, which is what JSUI expects to call. (We don't care about
   any modifier keys.) Our functions take `me` (a.k.a. `this`) to get hold of
   'global' bindings. Oh: we transform `button` into a boolean. Because you're worth it."
  [me f]
  (fn [x y button cmd shift capslock option ctrl]
    (f me {:button (> button 0) :x x :y y})))

(defn setup
  "Set up all drawing modes, `paint`, and the autowatch state. Start the
   Task."
  [me]
  (let [g (.-mgraphics me)
        init-state {:button false :x 0 :y 0}]
    ;; Application setup:
    (set! (.-check_stash me) init-state)
    (set! (.-point_stash me) (repeat TRAIL-LENGTH init-state))

    ;; Graphics setup.
    (.init g)
    (set! (.-relative_coords g) 0)      ; Work in pixel coordinates.
    (set! (.-autofill g) 0)
    (set! (.-paint me) (fn [] (paint me)))
    ;; Wrap the mouse handler functions.
    (set! (.-onclick me) (button-x-y-wrapper me bucket-add))
    (set! (.-ondrag me) (button-x-y-wrapper me bucket-add))
    (set! (.-onidle me) (button-x-y-wrapper me stash))
    (set! (.-onidleout me) (button-x-y-wrapper me stash))

    ;; Kick off task:
    (start-task me)
    (set! (.-autowatch me) 1))
  (let [d (js/Date.)]
    (.post me (str "Loaded example.puffdraw at " d "\n"))))
