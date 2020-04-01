(ns shokunin-april-2020.runresult
  (:gen-class))

(defrecord RunResult [p offices-with-paths sample-size])

(defn to-string
  [result]
  (format "%.1f -> %.4f "
          (:p result)
          (float (/ (:offices-with-paths result) (:sample-size result)))))

(defmethod print-method RunResult [result ^java.io.Writer writer]
  (print-method (to-string result) writer))

(defmethod print-dup RunResult [result ^java.io.Writer writer]
  (print-dup (to-string result) writer))
