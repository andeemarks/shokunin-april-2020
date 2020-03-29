(ns shokunin-april-2020.desk
  (:gen-class))

(defrecord Desk [occupied? has-twer? visited?])

(defn- to-string [loc]
  (if (:occupied? loc)
    "X"
    (if (:has-twer? loc)
      "*"
      (if (:visited? loc)
        "."
        " "))))

(defmethod print-method Desk [loc ^java.io.Writer writer]
  (print-method (to-string loc) writer))

(defmethod print-dup Desk [loc ^java.io.Writer writer]
  (print-dup (to-string loc) writer))

(defn empty [] (->Desk false false false))
(defn populated [] (->Desk true false false))
(defn twer [] (->Desk false true false))
(defn visited [] (->Desk false false true))

(defn visitable? [location]
  (and (not (:visited? location)) (not (:occupied? location))))
