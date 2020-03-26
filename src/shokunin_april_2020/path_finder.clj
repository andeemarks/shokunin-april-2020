(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.location :refer :all]
            [shokunin-april-2020.coordinate :refer :all]
            [shokunin-april-2020.office :as office]
            [clojure.tools.logging :as log]))

(defn- count-visited-in-row [row] (count (filter #(:visited? %) row)))

(defn count-visited [office]
  (reduce + (map #(count-visited-in-row %) office)))

(defn- path-to-first-row-found? [first-row]
  (not (= 0 (count-visited-in-row first-row))))

(defn path-exists? [office]
  (path-to-first-row-found? (office/first-row office)))

(defn neighbour [office coordinate direction]
  (let [neighbour-coordinates (neighbour-in-direction coordinate direction)]
    (if (within-office? neighbour-coordinates office)
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
        (office/mark-location-as-visited! office current-coordinate)
        (visit-neighbour office current-coordinate :north)
        (visit-neighbour office current-coordinate :south)
        (visit-neighbour office current-coordinate :east)
        (visit-neighbour office current-coordinate :west)))
    office))
