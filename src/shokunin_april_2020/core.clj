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

(defn offices-with-paths [population-factor sample-size]
  (count (filter #(office-has-path? population-factor %) (range 1 sample-size))))

(defn run-sample [population-factor sample-size]
  (let [offices-with-paths (offices-with-paths population-factor sample-size)
        offices-paths-percentage (float (/ offices-with-paths sample-size))]
    (println (format "%.1f -> %.4f " population-factor offices-paths-percentage))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [sample-size (Integer/parseInt (or (first args) "1000"))
        _ (println (str "Running with sample size of " sample-size))]
    (run-sample 0.0 sample-size)
    (run-sample 0.1 sample-size)
    (run-sample 0.2 sample-size)
    (run-sample 0.3 sample-size)
    (run-sample 0.4 sample-size)
    (run-sample 0.5 sample-size)
    (run-sample 0.6 sample-size)
    (run-sample 0.7 sample-size)
    (run-sample 0.8 sample-size)
    (run-sample 0.9 sample-size)
    (run-sample 1.0 sample-size)))