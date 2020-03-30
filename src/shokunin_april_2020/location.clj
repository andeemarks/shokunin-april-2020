(ns shokunin-april-2020.location
  (:require [shokunin-april-2020.office :as office]))

(defrecord Location [row column])

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
                               :north (->Location (inc row) column)
                               :south (->Location (dec row) column)
                               :east (->Location row (inc column))
                               :west (->Location row (dec column)))]
    (when (within-office? neighbour-coordinate office)
      neighbour-coordinate)))