(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (boolean-array number-colleagues true)
        unpopulated-desks (boolean-array (- office-size number-colleagues) false)]
    (partition desks-per-row (shuffle (concat populated-desks unpopulated-desks)))))

(defn count-population [office]
  (count (filter true? (flatten office))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
