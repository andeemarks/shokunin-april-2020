(ns shokunin-april-2020.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn build-office [rows-per-office desks-per-row]
  (make-array Boolean/TYPE rows-per-office desks-per-row ))
