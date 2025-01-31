(ns shokunin-april-2020.core
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :as pf]
            [shokunin-april-2020.runresult :refer [->RunResult]]
            [shokunin-april-2020.desk :as desk]))

(defn- office-has-path? [office _]
  (let [visited-office (pf/try-find-path office)]
    (office/path-exists? visited-office)))

(defn offices-with-paths [population-factor sample-size]
  (count (filter
          #(office-has-path? (office/populate-office 10 10 population-factor) %)
          (range 0 sample-size))))

(defn run-sample [population-factor sample-size]
  (let [offices-with-paths (offices-with-paths population-factor sample-size)]
    (println (->RunResult population-factor offices-with-paths sample-size))))

(defn- find-sample-size [args]
  (Integer/parseInt (or (first args) "1000")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [sample-size (find-sample-size args)
        _ (println (str "Running with sample size of " sample-size))]
    (doseq [population-factor (range 0.0 1.0 0.1)]
      (run-sample population-factor sample-size))))