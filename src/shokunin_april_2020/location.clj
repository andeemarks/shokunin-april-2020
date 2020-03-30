(ns shokunin-april-2020.location
  (:require [shokunin-april-2020.office :as office]))

(defrecord Location [row column])

(defn- within-office? [location office]
  (let [max-row (dec (office/depth office))
        max-column (dec (office/width office))]
    (and
     (<= 0 (:row location) max-row)
     (<= 0 (:column location) max-column))))

(defn neighbour [office location direction]
  (let [row (:row location)
        column (:column location)
        neighbour-location (case direction
                               :north (->Location (inc row) column)
                               :south (->Location (dec row) column)
                               :east (->Location row (inc column))
                               :west (->Location row (dec column)))]
    (when (within-office? neighbour-location office)
      neighbour-location)))