(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.location :refer :all]
            [shokunin-april-2020.coordinate :refer :all]
            [clojure.tools.logging :as log]))

(defn- count-visited-in-row [row] (count (filter #(:visited? %) row)))

(defn count-visited [office]
  (reduce + (map #(count-visited-in-row %) office)))

(defn- path-to-first-row-found? [first-row]
  (not (= 0 (count-visited-in-row first-row))))

(defn path-exists? [office]
  (let [first-row (aget office (dec (alength office)))]
    (path-to-first-row-found? first-row)))

(defn- mark-location-as-visited! [office coordinate]
  ; (log/infof "Marking %d %d as visited" row column)
  (aset office (:row coordinate) (:column coordinate) (visited-location))
  office)

(defn- location-exists? [office coordinate]
  (let [max-row (dec (alength office))
        max-column (dec (alength (aget office 0)))]
    (and
     (<= 0 (:row coordinate) max-row)
     (<= 0 (:column coordinate) max-column))))

(defn neighbour [office coordinate direction]
  (let [row (:row coordinate)
        column (:column coordinate)
        neighbour-coordinates
        (case direction
          :north (->Coordinate (inc row) column)
          :south (->Coordinate (dec row) column)
          :east (->Coordinate row (inc column))
          :west (->Coordinate row (dec column)))]
    (if (location-exists? office neighbour-coordinates)
      neighbour-coordinates
      nil)))

(declare flood-fill) ; needed because of cyclic call relationship between flood-fill and visit-neighbour

(defn- visit-neighbour [office current-coordinate direction]
  (let [neighbour (neighbour office current-coordinate direction)]
    (when (not (nil? neighbour))
      (flood-fill office neighbour))))

; Taken from stack-based recursive algo at https://en.wikipedia.org/wiki/Flood_fill
(defn flood-fill [office current-coordinate]
  (let [current-location (aget office (:row current-coordinate) (:column current-coordinate))]
    (when (visitable? current-location)
      (do
        (mark-location-as-visited! office current-coordinate)
        (visit-neighbour office current-coordinate :north)
        (visit-neighbour office current-coordinate :south)
        (visit-neighbour office current-coordinate :east)
        (visit-neighbour office current-coordinate :west)))
    office))
