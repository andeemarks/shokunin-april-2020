(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.location :as coord :refer (->Location)]
            [shokunin-april-2020.office :as office]
            [clojure.tools.logging :as log]))

; needed because of cyclic calls between flood-fill and visit-neighbour
(declare flood-fill)

(defn- visit-neighbour [office current-location direction]
  (let [neighbour (coord/neighbour office current-location direction)]
    (when-not (nil? neighbour)
      (flood-fill office neighbour))))

; Taken from https://en.wikipedia.org/wiki/Flood_fill
(defn flood-fill [office current-location]
  (let [current-desk (office/desk-at office current-location)]
    (when (desk/visitable? current-desk)
      (office/mark-desk-as-visited! office current-location)
      (visit-neighbour office current-location :north)
      (visit-neighbour office current-location :south)
      (visit-neighbour office current-location :east)
      (visit-neighbour office current-location :west))
    office))

(defn try-find-path [office]
  (flood-fill office (office/find-twer office)))