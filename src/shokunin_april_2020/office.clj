(ns shokunin-april-2020.office
  (:require [shokunin-april-2020.desk :as loc]
            [clojure.tools.logging :as log]))

(defn from-desks [desks desks-per-row]
  (to-array-2d (partition desks-per-row desks)))

(defn- mark-as [office coordinate location]
  (aset office (:row coordinate) (:column coordinate) location)
  office)

(defn mark-location-as-visited! [office coordinate]
  ; (log/infof "Marking %d %d as visited" row column)
  (mark-as office coordinate (loc/visited))
  office)

(defn mark-location-as-populated! [office coordinate]
  (mark-as office coordinate (loc/populated))
  office)

(defn mark-location-as-empty! [office coordinate]
  (mark-as office coordinate (loc/empty))
  office)

(defn width [office] (alength (aget office 0)))
(defn depth [office] (alength office))
(defn first-row [office] (aget office (dec (depth office))))
(defn last-row [office] (aget office 0))
(defn location-at
  [office coordinate]
  (aget office (:row coordinate) (:column coordinate)))

(defn populate-twer! [office]
  (let [twer-desk-index (rand-int (width office))]
    (aset office 0 twer-desk-index (loc/twer))
    office))

(defn- index-if-twer [row current-index]
  (if (:has-twer? (aget row current-index))
    current-index
    0))

(defn- index-of-twer [last-row office]
  (areduce last-row i ret 0 (+ ret (index-if-twer last-row i))))

(defn find-twer [office]
  (let [last-row (last-row office)
        index-of-twer (index-of-twer last-row office)]
    {:row 0 :column index-of-twer}))

(defn- count-occupied-in-row [row] (count (filter :occupied? row)))

(defn population-size [office]
  (reduce + (map count-occupied-in-row office)))

(defn to-string [office]
  (doseq [row office]
    (println (apply pr-str row))))