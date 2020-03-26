(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.location :as loc]))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (loc/populated-location))
        unpopulated-desks (repeat (- office-size number-colleagues) (loc/empty-location))
        all-desks (concat populated-desks unpopulated-desks)
        random-desks (shuffle all-desks)
        desks-in-rows (partition desks-per-row random-desks)]
    (office/populate-twer! (to-array-2d desks-in-rows))))

(defn- count-occupied-in-row [row] (count (filter #(:occupied? %) row)))

(defn count-population [office]
  (reduce + (map #(count-occupied-in-row %) office)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
