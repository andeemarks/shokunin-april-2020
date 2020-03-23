(ns shokunin-april-2020.core
  (:gen-class))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn populate-office [rows-per-office desks-per-row population-factor]
  (let [office-size (* rows-per-office desks-per-row)
        number-colleages (* office-size population-factor)
        populated-desks (boolean-array number-colleages true)
        unpopulated-desks (boolean-array (- office-size number-colleages) false)
        ]
  (partition rows-per-office (shuffle (concat populated-desks unpopulated-desks)))))

(defn count-population [office]
  (count (filter true? (flatten office))))