(ns shokunin-april-2020.location
  (:gen-class))

(defrecord Location [occupied? has-twer? visited?])

(defn- to-string [loc]
  (if (:occupied? loc)
    "X"
    (if (:has-twer? loc)
      "*"
      (if (:visited? loc)
        "."
        " "))))

(defmethod print-method Location [loc ^java.io.Writer writer]
  (print-method (to-string loc) writer))

(defmethod print-dup Location [loc ^java.io.Writer writer]
  (print-dup (to-string loc) writer))

(defn empty-location [] (->Location false false false))
(defn populated-location [] (->Location true false false))
(defn twer-location [] (->Location false true false))
(defn visited-location [] (->Location false false true))

(defn visitable? [location]
  (and (not (:visited? location)) (not (:occupied? location))))
