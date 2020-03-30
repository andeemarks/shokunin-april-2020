(ns shokunin-april-2020.office
  (:require [shokunin-april-2020.desk :as desk]
            [clojure.tools.logging :as log]))

(defn from-desks [desks desks-per-row]
  (to-array-2d (partition desks-per-row desks)))

(defn- mark-as [office coordinate location]
  (aset office (:row coordinate) (:column coordinate) location)
  office)

(defn mark-location-as-visited! [office coordinate]
  ; (log/infof "Marking %d %d as visited" row column)
  (mark-as office coordinate (desk/visited))
  office)

(defn mark-location-as-populated! [office coordinate]
  (mark-as office coordinate (desk/populated))
  office)

(defn mark-location-as-empty! [office coordinate]
  (mark-as office coordinate (desk/empty))
  office)

(defn width [office] (alength (aget office 0)))
(defn depth [office] (alength office))
(defn first-row [office] (aget office (dec (depth office))))
(defn last-row [office] (aget office 0))
(defn desk-at
  [office coordinate]
  (aget office (:row coordinate) (:column coordinate)))

(defn populate-twer! [office]
  (let [twer-desk-index (rand-int (width office))]
    (aset office 0 twer-desk-index (desk/twer))
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

(defn- row-to-string [row]
  (str (apply pr-str row) "\n"))

(defn to-string [office]
  (doall (map row-to-string office)))

(defn- count-visited-in-row [row] (count (filter :visited? row)))

(defn count-visited [office]
  (reduce + (map count-visited-in-row office)))

(defn- path-to-first-row-found? [first-row]
  (not= 0 (count-visited-in-row first-row)))

(defn path-exists? [office]
  (path-to-first-row-found? (first-row office)))
