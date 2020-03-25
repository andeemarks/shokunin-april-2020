(ns shokunin-april-2020.location
  (:gen-class))

(defrecord Location [occupied? has-twer? visited?])

(defn empty-location [] (->Location false false false))
(defn populated-location [] (->Location true false false))
(defn twer-location [] (->Location false true false))
(defn visited-location [] (->Location false false true))

(defn visitable? [location]
    (and (not (:visited? location)) (not (:occupied? location))))
