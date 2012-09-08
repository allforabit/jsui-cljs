;; tickle: examine objects in the enclosing patcher, annotate them (eventually).

(ns example.tickle)

(defn paint
  "Repaint on demand."
  [me]
  (let [data (.-data me)
        rects (map :rect data)
        g (.-mgraphics me)]
    (doseq [[l t r b] rects]
      (js/post (str "in-doseq: " g "\n"))
      (.rectangle g l t (- r l) (- b t)))
    (.stroke g))
  nil)

(defn find-all [obj]
  (if obj (cons {:class (.-maxclass obj)
                 :rect (vec (.-rect obj))}
                (find-all (.-nextobject obj)))
      nil))

(defn bang
  "Force a re-examine and a redraw."
  [me]
  (js/post (str "bang.mgraphics: " (.-mgraphics me) "\n"))
  (set! (.-data me) (find-all (.-firstobject (.-patcher me))))
  (.redraw (.-mgraphics me)))

(defn setup
  [me]
  (let [g (.-mgraphics me)]
    (.init g)
    (set! (.-relative_coords g) 0)      ; Work in pixel coordinates.
    (set! (.-autofill g) 0)
    (set! (.-paint me) (fn [] (paint me)))
    (set! (.-bang me) (fn [] (bang me)))
    (set! (.-autowatch me) 1))
  (let [d (js/Date.)]
    (.post me (str "Loaded example.tickle at " d "\n"))))
