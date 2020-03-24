(ns shokunin-april-2020.path-finder)

(defn path-exists? [office]
    true)

; Flood-fill (node, target-color, replacement-color):
;  1. If target-color is equal to replacement-color, return.
;  2. ElseIf the color of node is not equal to target-color, return.
;  3. Else Set the color of node to replacement-color.
;  4. Perform Flood-fill (one step to the south of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the north of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the west of node, target-color, replacement-color).
;     Perform Flood-fill (one step to the east of node, target-color, replacement-color).
;  5. Return.
(defn flood-fill [x y status])