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

(defn neighbour [office row column direction]
  (try
    (case direction
      :north (aget office (inc row) column)
      :south (aget office (dec row) column)
      :east (aget office row (inc column))
      :west (aget office row (dec column)))
    (catch java.lang.ArrayIndexOutOfBoundsException e
        ; TODO Technically, this is not an exceptional situation: it is anticipated.
        ; I cannot think of a more elegant way to detect it though :-
      nil)))

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
      (mark-location-as-visited office current-row current-column)
      office)))