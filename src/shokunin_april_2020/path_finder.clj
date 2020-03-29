(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.desk :as loc]
            [shokunin-april-2020.coordinate :as coord :refer (->Coordinate)]
            [shokunin-april-2020.office :as office]
            [clojure.tools.logging :as log]))

(defn- count-visited-in-row [row] (count (filter :visited? row)))

(defn count-visited [office]
  (reduce + (map count-visited-in-row office)))

(defn- path-to-first-row-found? [first-row]
  (not= 0 (count-visited-in-row first-row)))

(defn path-exists? [office]
  (path-to-first-row-found? (office/first-row office)))

(defn neighbour [office coordinate direction]
  (let [neighbour-coordinates (coord/neighbour coordinate direction)]
    (when (coord/within-office? neighbour-coordinates office)
      neighbour-coordinates)))

; needed because of cyclic calls between flood-fill and visit-neighbour
(declare flood-fill)

(defn- visit-neighbour [office current-coordinate direction]
  (let [neighbour (neighbour office current-coordinate direction)]
    (when-not (nil? neighbour)
      (flood-fill office neighbour))))

; Taken from https://en.wikipedia.org/wiki/Flood_fill
(defn flood-fill [office current-coordinate]
  (let [current-location (office/location-at office current-coordinate)]
    (when (loc/visitable? current-location)
      (office/mark-location-as-visited! office current-coordinate)
      (visit-neighbour office current-coordinate :north)
      (visit-neighbour office current-coordinate :south)
      (visit-neighbour office current-coordinate :east)
      (visit-neighbour office current-coordinate :west))
    office))

(defn try-find-path [office]
  (flood-fill office (office/find-twer office)))