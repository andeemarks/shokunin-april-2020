(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]
            [shokunin-april-2020.location :refer :all]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "path found when visited location found in first row"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 1 0 (visited-location))]
      (is (= true (path-exists? office)))))

  (testing "path not found when no visited location found in first row"
    (let [office (populate-office 2 2 0.0)]
      (is (= false (path-exists? office))))))

(deftest finding-neighbours
  (testing "northern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= {:row 2 :column 1} (neighbour office 1 1 :north)))))

  (testing "southern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= {:row 0 :column 1} (neighbour office 1 1 :south)))))

  (testing "western neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= {:row 1 :column 0} (neighbour office 1 1 :west)))))

  (testing "eastern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= {:row 1 :column 2} (neighbour office 1 1 :east)))))

  (testing "nil returned when neighbour requested beyond office boundary"
    (let [office (populate-office 1 1 1)]
      (is (nil? (neighbour office 0 0 :east)))
      (is (nil? (neighbour office 0 0 :west)))
      (is (nil? (neighbour office 0 0 :south)))
      (is (nil? (neighbour office 0 0 :north)))))

  (testing "invalid directions generate errors"
    (let [office (populate-office 3 3 1)]
      (is (thrown? IllegalArgumentException (neighbour office 1 1 :up))))))

(deftest flood-filling
  (testing "visiting an already visited location does nothing"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (visited-location))
          visited-office (flood-fill office 0 0)]
      (is (= (visited-location) (aget visited-office 0 0)))))

  (testing "visiting an occupied location does nothing"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (populated-location))
          visited-office (flood-fill office 0 0)]
      (is (= (populated-location) (aget visited-office 0 0)))))

  (testing "visiting an unvisited location marks it as visited"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (empty-location))
          visited-office (flood-fill office 0 0)]
      (is (= (visited-location) (aget visited-office 0 0))))))
