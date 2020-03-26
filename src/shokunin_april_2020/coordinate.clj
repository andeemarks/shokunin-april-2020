(ns shokunin-april-2020.coordinate
  (:gen-class))

(defrecord Coordinate [row column])

(defn within-office? [office coordinate]
  (let [max-row (dec (alength office))
        max-column (dec (alength (aget office 0)))]
    (and
     (<= 0 (:row coordinate) max-row)
     (<= 0 (:column coordinate) max-column))))