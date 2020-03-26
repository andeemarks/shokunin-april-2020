(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.location :refer :all]))

(defn- count-visited-in-row [row] (count (filter #(:visited? %) row)))

(defn- path-to-first-row-found? [first-row]
  (not (= 0 (count-visited-in-row first-row))))

(defn path-exists? [office]
  (let [first-row (aget office (dec (alength office)))]
    (path-to-first-row-found? first-row)))

(defn- mark-location-as-visited [office row column]
  (aset office row column (visited-location))
  office)

(defn- location-exists? [office row column]
  (let [max-row (dec (alength office))
        max-column (dec (alength (aget office 0)))]
    (and
     (<= 0 row max-row)
     (<= 0 column max-column))))

(defn neighbour [office row column direction]
  (let [neighbour-coordinates
        (case direction
          :north {:row (inc row) :column column}
          :south {:row (dec row) :column column}
          :east {:row row :column (inc column)}
          :west {:row row :column (dec column)})]
    (if (location-exists? office (:row neighbour-coordinates) (:column neighbour-coordinates))
      neighbour-coordinates
      nil)))

(declare flood-fill) ; needed because of cyclic call relationship between flood-fill and visit-neighbour

(defn- visit-neighbour [office current-row current-column direction]
  (let [neighbour (neighbour office current-row current-column direction)]
    (when (not (nil? neighbour))
      (flood-fill office (:row neighbour) (:column neighbour)))))

; Flood-fill (node, target-color, replacement-color):
;  1. If target-color is equal to replacement-color, return.
;  2. ElseIf the color of node is not equal to target-color, return.
;  3. Else Set the color of node to replacement-color.
;  4. Perform Flood-fill (one step to the south of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the north of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the west of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the east of node, target-color, replacement-color).
;  5. Return.
(defn flood-fill [office current-row current-column]
  (let [current-location (aget office current-row current-column)]
    (if (visitable? current-location)
      (do
        (mark-location-as-visited office current-row current-column)
        ; (visit-neighbour office current-row current-column :north)
        ; (visit-neighbour office current-row current-column :south)
        ; (visit-neighbour office current-row current-column :east)
        ; (visit-neighbour office current-row current-column :west)
        )
      office)))
