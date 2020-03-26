(ns shokunin-april-2020.coordinate
  (:gen-class))

(defrecord Coordinate [row column])

(defn within-office? [coordinate office]
  (let [max-row (dec (alength office))
        max-column (dec (alength (aget office 0)))]
    (and
     (<= 0 (:row coordinate) max-row)
     (<= 0 (:column coordinate) max-column))))

(defn neighbour-in-direction [coordinate direction]
  (let [row (:row coordinate)
        column (:column coordinate)]
    (case direction
      :north (->Coordinate (inc row) column)
      :south (->Coordinate (dec row) column)
      :east (->Coordinate row (inc column))
      :west (->Coordinate row (dec column)))))