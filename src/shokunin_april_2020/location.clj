(ns shokunin-april-2020.location
  (:gen-class))

(defrecord Location [occupied? has-twer?])

(defn empty-location [] (->Location false false))
(defn populated-location [] (->Location true false))
(defn twer-location [] (->Location false true))
