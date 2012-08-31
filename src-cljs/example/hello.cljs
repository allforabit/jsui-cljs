(ns example.hello)

(.init js/mgraphics)
(set! (.-relative_coords js/mgraphics) 1)
(set! (.-autofill js/mgraphics) 0)

(defn line [m x y]
  (.line_to m x y)
  m)

(defn move [m x y]
  (.move_to m x y)
  m)

(defn stroke [m]
  (.stroke m)
  m)

(defn paint []
  (-> js/mgraphics
      (move -1.0 -1.0)
      (line 1.0 1.0)
      (stroke)
      (move 1.0 -1.0)
      (line -1.0 1.0)
      (stroke)))

(this-as me
         (set! (.-autowatch me) 1)
         (set! (.-paint me) paint))

(js/post "Done\n")
