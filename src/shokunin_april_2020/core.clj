(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]))

(defrecord Location [occupied? has-twer?])

(defn empty-location [] (->Location false false))
(defn populated-location [] (->Location true false))
(defn twer-location [] (->Location false true))

(defn populate-twer [office]
  (let [row-width (count (aget office 0))]
    (aset office 0 (rand-int row-width) (twer-location))
    office))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (populated-location))
        unpopulated-desks (repeat (- office-size number-colleagues) (empty-location))
        all-desks (concat populated-desks unpopulated-desks)
        random-desks (shuffle all-desks)
        desks-in-rows (partition desks-per-row random-desks)]
    (populate-twer (to-array-2d desks-in-rows))))

(defn count-occupied-in-row [row] (count (filter #(:occupied? %) row)))

(defn count-population [office]
  (reduce + (map #(count-occupied-in-row %) office)))

(defn find-twer [office]
  (let [back-row (aget office 0)
        index-of-twer (areduce back-row i ret 0 (+ ret (if (:has-twer? (aget back-row i)) i 0)))]
    {:row 0 :column index-of-twer}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (pp/pprint (populate-office 10 10 0.5)))
