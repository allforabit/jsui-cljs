;; tickle: examine objects in the enclosing patcher, annotate them (eventually).

(ns example.tickle)

(defn with-rotate
  "Call a function with a rotation in place, then back out of it.
   (I guess calls to this won't nest!)"
  [g amt f]
  (.rotate g amt)
  (f)
  (.identity_matrix g))

(defn do-text [g t1 t2]
  (let [_ (.text_measure g t1)]
    (.text_path g t1)
    (.move_by g -10 5)
    (.text_path g t2)))

(defn paint
  "Repaint on demand."
  [me]
  (let [data (.-data me)
        [my-l my-t my-r my-b] (.-rect (.-box me))
        g (.-mgraphics me)]
    ;; Cycle through the rectangles: pad out each one, and offset
    ;; it by our own position, so that we actually draw over the
    ;; objects in the patcher when we're underneath them.
    (doseq [d data]
      (let [[l t r b] (:rect d)
            padding 5
            bead 10
            l (- (- l padding) my-l)
            t (- (- t padding) my-t)
            r (- (+ r padding) my-l)
            b (- (+ b padding) my-t)]
        (.set_source_rgba g 0.8 0.5 0.0 0.7)
        (.rectangle_rounded g l t (- r l) (- b t) bead bead)
        (.move_to g (+ r padding) (* 0.5 (+ t b)))
        (with-rotate g -0.4 (fn [] (.text_path g (:class d))))))
    (.stroke g))
  nil)

(defn find-all
  "Find all objects in the patcher. For each, return a map containing the
   object's class and its bounding rectangle."
  [obj]
  (if obj (cons {:class (.-maxclass obj)
                 :rect (vec (.-rect obj))}
                (find-all (.-nextobject obj)))
      nil))

(defn bang
  "Force a re-examine and a redraw. The only way to initiate a draw is
   via a `(.redraw)` on `mgraphics`, and we can't pass through any
   parameters, so we plant the data into `this` first."
  [me]
  (set! (.-data me) (find-all (.-firstobject (.-patcher me))))
  (.redraw (.-mgraphics me)))

(defn setup
  "Set up all drawing modes, `paint`, `bang` and the autowatch state."
  [me]
  (let [g (.-mgraphics me)]
    (.init g)
    (set! (.-relative_coords g) 0)      ; Work in pixel coordinates.
    (set! (.-autofill g) 0)
    (set! (.-paint me) (fn [] (paint me)))
    (set! (.-bang me) (fn [] (bang me)))
    (set! (.-autowatch me) 1)
    (bang me))
  (let [d (js/Date.)]
    (.post me (str "Loaded example.tickle at " d "\n"))))
