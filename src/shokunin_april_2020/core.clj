(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [shokunin-april-2020.location :as loc]))

(defn- populate-twer [office]
  (let [row-width (count (aget office 0))
        twer-desk-index (rand-int row-width)]
    (aset office 0 twer-desk-index (loc/twer-location))
    office))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (loc/populated-location))
        unpopulated-desks (repeat (- office-size number-colleagues) (loc/empty-location))
        all-desks (concat populated-desks unpopulated-desks)
        random-desks (shuffle all-desks)
        desks-in-rows (partition desks-per-row random-desks)]
    (populate-twer (to-array-2d desks-in-rows))))

(defn- count-occupied-in-row [row] (count (filter #(:occupied? %) row)))

(defn count-population [office]
  (reduce + (map #(count-occupied-in-row %) office)))

(defn- index-if-twer [row current-index]
  (if (:has-twer? (aget row current-index))
    current-index
    0))

(defn find-twer [office]
  (let [back-row (aget office 0)
        index-of-twer (areduce back-row i ret 0 (+ ret (index-if-twer back-row i)))]
    {:row 0 :column index-of-twer}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
