(ns shokunin-april-2020.desk
  (:gen-class))

(defrecord Desk [occupied? has-twer? visited?])

(defn to-string [desk]
  (if (:occupied? desk)
    "X"
    (if (:has-twer? desk)
      "*"
      (if (:visited? desk)
        "."
        " "))))

(defmethod print-method Desk [desk ^java.io.Writer writer]
  (print-method (to-string desk) writer))

(defmethod print-dup Desk [desk ^java.io.Writer writer]
  (print-dup (to-string desk) writer))

(defn unoccupied [] (map->Desk {}))
(defn occupied [] (map->Desk {:occupied? true}))
(defn twer [] (map->Desk {:has-twer? true}))
(defn visited [] (map->Desk {:visited? true}))

(defn visitable? [desk]
  (not
   (or
    (:visited? desk)
    (:occupied? desk))))