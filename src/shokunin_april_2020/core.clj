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
        random-desks (shuffle all-desks)]
    (office/populate-twer! (office/from-desks random-desks desks-per-row))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
