(ns shokunin-april-2020.office
  (:require [shokunin-april-2020.location :refer :all]
            [clojure.tools.logging :as log]))

(defn mark-location-as-visited! [office coordinate]
  ; (log/infof "Marking %d %d as visited" row column)
  (aset office (:row coordinate) (:column coordinate) (visited-location))
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