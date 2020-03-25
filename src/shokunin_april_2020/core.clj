(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]))

(defrecord Location [occupied? has-twer?])

(defn empty-location [] (->Location false false))
(defn populated-location [] (->Location true false))
(defn twer-location [] (->Location true true))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (populated-location))
        unpopulated-desks (repeat (- office-size number-colleagues) (empty-location))]
    (to-array-2d (partition desks-per-row (shuffle (concat populated-desks unpopulated-desks))))))

(defn count-occupied-in-row [row] (count (filter #(:occupied? %) row)))

(defn count-population [office]
  (reduce + (map #(count-occupied-in-row %) office)))

(defn find-twer [office]
  (let [back-row (aget office 0)])
  {:row 1 :column 2})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
