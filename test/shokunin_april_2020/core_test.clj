(ns shokunin-april-2020.core-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]))

(deftest office-population
  (testing "office has specified dimensions"
    (is (= (count (populate-office 10 15 0.1)) 10))
    (is (= (count (first (populate-office 10 15 1.0))) 15)))

  (testing "randomly populates the office each time"
    (let [populated-office-1 (populate-office 3 3 0.4)
          populated-office-2 (populate-office 3 3 0.4)]
      (is (not (= populated-office-1 populated-office-2)))))

  (testing "randomly located the TWers in the back row of desks"
    (is (= 0 (:row (find-twer (populate-office 2 5 0.4)))))
    (is (= 0 (:row (find-twer (populate-office 10 10 0.5)))))
    (is (= 0 (:row (find-twer (populate-office 25 40 0.8))))))

  (testing "populates the office according to the p value"
    (is (= (count-population (populate-office 2 5 0.4)) 4))
    (is (= (count-population (populate-office 10 10 0.2)) 20))
    (is (= (count-population (populate-office 25 40 0.8)) 800))))
