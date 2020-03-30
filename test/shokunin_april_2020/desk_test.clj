(ns shokunin-april-2020.desk-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.desk :refer :all]))

(deftest visitable-status
  (testing "empty desks are visitable"
    (is (visitable? (empty))))

  (testing "desks with the TWer are visitable"
    (is (visitable? (twer))))

  (testing "visited desks are not visitable"
    (is (not (visitable? (visited)))))

  (testing "occupied desks are not visitable"
    (is (not (visitable? (populated))))))

(deftest string-representation
  (testing "each type of desk has different representation"
    (is (= " " (to-string (empty))))
    (is (= "*" (to-string (twer))))
    (is (= "X" (to-string (populated))))
    (is (= "." (to-string (visited))))))
