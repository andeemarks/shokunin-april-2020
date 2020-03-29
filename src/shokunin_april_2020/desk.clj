(ns shokunin-april-2020.desk
  (:gen-class))

(defrecord Desk [occupied? has-twer? visited?])

(defn- to-string [desk]
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

(defn empty [] (->Desk false false false))
(defn populated [] (->Desk true false false))
(defn twer [] (->Desk false true false))
(defn visited [] (->Desk false false true))

(defn visitable? [desk]
  (and (not (:visited? desk)) (not (:occupied? desk))))
