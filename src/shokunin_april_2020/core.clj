(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :as pf]
            [shokunin-april-2020.desk :as loc :refer (->Desk)]))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleagues (* office-size population-factor)
        populated-desks (repeat number-colleagues (loc/populated))
        unpopulated-desks (repeat (- office-size number-colleagues) (loc/empty))
        all-desks (concat populated-desks unpopulated-desks)
        random-desks (shuffle all-desks)]
    (office/populate-twer! (office/from-desks random-desks desks-per-row))))

(defn- office-has-path? [office _]
  (let [visited-office (pf/try-find-path office)]
    (pf/path-exists? visited-office)))

(defn offices-with-paths [population-factor sample-size]
  (count (filter
          #(office-has-path? (populate-office 10 10 population-factor) %)
          (range 0 sample-size))))

(defn run-sample [population-factor sample-size]
  (let [offices-with-paths (offices-with-paths population-factor sample-size)
        pathed-offices-% (float (/ offices-with-paths sample-size))]
    (println (format
              "%.1f -> %.4f "
              population-factor
              pathed-offices-%))))

(defn- find-sample-size [args]
  (Integer/parseInt (or (first args) "1000")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [sample-size (find-sample-size args)
        _ (println (str "Running with sample size of " sample-size))]
    (doseq [population-factor (range 0.0 1.0 0.1)]
      (run-sample population-factor sample-size))))