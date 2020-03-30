(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.coordinate :as coord :refer (->Coordinate)]
            [shokunin-april-2020.office :as office]
            [clojure.tools.logging :as log]))

; needed because of cyclic calls between flood-fill and visit-neighbour
(declare flood-fill)

(defn- visit-neighbour [office current-coordinate direction]
  (let [neighbour (coord/neighbour office current-coordinate direction)]
    (when-not (nil? neighbour)
      (flood-fill office neighbour))))

; Taken from https://en.wikipedia.org/wiki/Flood_fill
(defn flood-fill [office current-coordinate]
  (let [current-location (office/desk-at office current-coordinate)]
    (when (desk/visitable? current-location)
      (office/mark-desk-as-visited! office current-coordinate)
      (visit-neighbour office current-coordinate :north)
      (visit-neighbour office current-coordinate :south)
      (visit-neighbour office current-coordinate :east)
      (visit-neighbour office current-coordinate :west))
    office))

(defn try-find-path [office]
  (flood-fill office (office/find-twer office)))