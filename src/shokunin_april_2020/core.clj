(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :as pf]
            [shokunin-april-2020.location :as loc :refer (->Location)]))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (loc/populated-location))
        unpopulated-desks (repeat (- office-size number-colleagues) (loc/empty-location))
        all-desks (concat populated-desks unpopulated-desks)
        random-desks (shuffle all-desks)]
    (office/populate-twer! (office/from-desks random-desks desks-per-row))))

(defn- office-has-path? [population-factor sample-id]
  (let [office (populate-office 10 10 population-factor)
        visited-office (pf/try-find-path office)]
    (pf/path-exists? visited-office)))

(defn offices-with-paths [population-factor sample-count]
  (count (filter #(office-has-path? population-factor %) (range 1 sample-count))))

(defn run-sample [population-factor sample-count]
  (println (str "For p of " population-factor " # of offices with paths = " (offices-with-paths population-factor sample-count))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-sample 0.1 1000)
  (run-sample 0.2 1000)
  (run-sample 0.3 1000)
  (run-sample 0.4 1000)
  (run-sample 0.5 1000)
  (run-sample 0.6 1000)
  (run-sample 0.7 1000)
  (run-sample 0.8 1000)
  (run-sample 0.9 1000)
  (run-sample 1.0 1000))