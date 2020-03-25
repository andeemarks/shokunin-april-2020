(ns shokunin-april-2020.path-finder
  (:require [shokunin-april-2020.location :refer :all]))


(defn path-exists? [office]
  true)

(defn- mark-location-as-visited [office row column]
  (aset office row column (visited-location))
  office)

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