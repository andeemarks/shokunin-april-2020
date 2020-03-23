(ns shokunin-april-2020.core-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]))

(deftest office-construction
  (testing "office has specified dimensions"
    (is (= (count(build-office 10 15)) 10)))
    (is (= (count(first (build-office 10 15))) 15))
    )

(deftest office-population
  (testing "changes the previous office but not the size"
    (let [office (build-office 3 3)
          populated-office (populate-office office 0.4)]
      (is (not (nil? populated-office)))
      (is (not (= populated-office office))))
      )

  (testing "randomly populates the office each time"
    (let [office (build-office 3 3)
          populated-office-1 (populate-office office 0.4)
          populated-office-2 (populate-office office 0.4)
          ]
      (is (not (= populated-office-1 populated-office-2)))
      
      )
  ))
