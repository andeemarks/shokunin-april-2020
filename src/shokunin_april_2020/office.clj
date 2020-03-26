(ns shokunin-april-2020.office
  (:require [shokunin-april-2020.location :refer :all]
            [clojure.tools.logging :as log]))

(defn- mark-as [office coordinate location]
  (aset office (:row coordinate) (:column coordinate) location)
  office)

(defn mark-location-as-visited! [office coordinate]
  ; (log/infof "Marking %d %d as visited" row column)
  (mark-as office coordinate (visited-location))
  office)

(defn mark-location-as-populated! [office coordinate]
  (mark-as office coordinate (populated-location))
  office)

(defn mark-location-as-empty! [office coordinate]
  (mark-as office coordinate (empty-location))
  office)

(defn width [office] (alength (aget office 0)))
(defn depth [office] (alength office))
(defn first-row [office] (aget office (dec (depth office))))
(defn last-row [office] (aget office 0))
(defn location-at [office coordinate] (aget office (:row coordinate) (:column coordinate)))

(defn populate-twer! [office]
  (let [twer-desk-index (rand-int (width office))]
    (aset office 0 twer-desk-index (twer-location))
    office))

(defn- index-if-twer [row current-index]
  (if (:has-twer? (aget row current-index))
    current-index
    0))

(defn find-twer [office]
  (let [back-row (last-row office)
        index-of-twer (areduce back-row i ret 0 (+ ret (index-if-twer back-row i)))]
    {:row 0 :column index-of-twer}))
