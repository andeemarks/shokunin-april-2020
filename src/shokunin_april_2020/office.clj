(ns shokunin-april-2020.office
  (:require [shokunin-april-2020.desk :as desk]
            [clojure.tools.logging :as log]))

(defn from-desks [desks desks-per-row]
  (to-array-2d (partition desks-per-row desks)))

(defn empty-square-of-width 
  "Helper constructor for tests needing to build offices without caring about contents or shape."
  [width]
  (from-desks (repeatedly (* width width) desk/unoccupied) width))

(defn- mark-as [office location desk]
  (aset office (:row location) (:column location) desk)
  office)

(defn mark-desk-as-visited! [office location]
  ; (log/infof "Marking %d %d as visited" row column)
  (mark-as office location (desk/visited)))

(defn mark-desk-as-populated! [office location]
  (mark-as office location (desk/occupied)))

(defn mark-desk-as-empty! [office location]
  (mark-as office location (desk/unoccupied)))

(defn width
  "The number of desks across the width of the office."
  [office] (alength (aget office 0)))

(defn depth
  "The number of rows of desks across the depth of the office."
  [office] (alength office))

(defn- row [office n] (aget office n))
(defn exit-row [office] (row office (dec (depth office))))
(defn row-with-twer [office] (row office 0))
(defn desk-at [office location] (aget office (:row location) (:column location)))

(defn place-twer! [office]
  (let [twer-desk-index (rand-int (width office))]
    (aset office 0 twer-desk-index (desk/twer))
    office))

(def ^:const twer-not-found -1)

(defn- index-if-twer [row current-index]
  (if (:has-twer? (aget row current-index))
    current-index
    twer-not-found))

(defn- index-of-twer [row-with-twer office]
  (areduce row-with-twer i ret twer-not-found (max ret (index-if-twer row-with-twer i))))

(defn find-twer [office]
  (let [row-with-twer (row-with-twer office)
        index-of-twer (index-of-twer row-with-twer office)]
    (if (<= index-of-twer twer-not-found)
      (throw (IllegalStateException. "No TWer found in office"))
      {:row 0 :column index-of-twer})))

(defn- count-occupied-in-row [row] (count (filter :occupied? row)))
(defn- row-to-string [row] (str (apply pr-str row) "\n"))
(defn- count-visited-in-row [row] (count (filter :visited? row)))
(defn- path-to-exit-row-found? [exit-row] (not= 0 (count-visited-in-row exit-row)))

(defn path-exists? [office] (path-to-exit-row-found? (exit-row office)))
(defn to-string [office] (doall (map row-to-string office)))
(defn count-visited [office] (reduce + (map count-visited-in-row office)))
(defn count-occupied [office] (reduce + (map count-occupied-in-row office)))
