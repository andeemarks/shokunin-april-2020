(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]))

(defrecord Location [occupied?])

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (->Location true))
        unpopulated-desks (repeat (- office-size number-colleagues) (->Location false))]
    (partition desks-per-row (shuffle (concat populated-desks unpopulated-desks)))))

(defn count-population [office]
  (aget office 0 3)
  (count (filter #(true? (:occupied? %)) (flatten office))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
