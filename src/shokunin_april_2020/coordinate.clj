(ns shokunin-april-2020.coordinate
  (:require [shokunin-april-2020.office :as office]))

(defrecord Coordinate [row column])

(defn- within-office? [coordinate office]
  (let [max-row (dec (office/depth office))
        max-column (dec (office/width office))]
    (and
     (<= 0 (:row coordinate) max-row)
     (<= 0 (:column coordinate) max-column))))

(defn neighbour [office coordinate direction]
  (let [row (:row coordinate)
        column (:column coordinate)
        neighbour-coordinate (case direction
                               :north (->Coordinate (inc row) column)
                               :south (->Coordinate (dec row) column)
                               :east (->Coordinate row (inc column))
                               :west (->Coordinate row (dec column)))]
    (when (within-office? neighbour-coordinate office)
      neighbour-coordinate)))