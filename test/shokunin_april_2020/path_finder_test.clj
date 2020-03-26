(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]
            [shokunin-april-2020.location :refer :all]
            [shokunin-april-2020.coordinate :refer :all]
            [shokunin-april-2020.office :refer :all]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "path found when visited location found in first row"
    (let [office (populate-office 2 2 0.0)
          _ (mark-location-as-visited! office (->Coordinate 1 0))]
      (is (= true (path-exists? office)))))

  (testing "path not found when no visited location found in first row"
    (let [office (populate-office 2 2 0.0)]
      (is (= false (path-exists? office))))))

(deftest finding-neighbours
  (testing "northern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= (->Coordinate 2 1) (neighbour office (->Coordinate 1 1) :north)))))

  (testing "southern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= (->Coordinate 0 1) (neighbour office (->Coordinate 1 1) :south)))))

  (testing "western neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= (->Coordinate 1 0) (neighbour office (->Coordinate 1 1) :west)))))

  (testing "eastern neighbour returned when one exists"
    (let [office (populate-office 3 3 1)]
      (is (= (->Coordinate 1 2) (neighbour office (->Coordinate 1 1) :east)))))

  (testing "nil returned when neighbour requested beyond office boundary"
    (let [office (populate-office 1 1 1)]
      (is (nil? (neighbour office (->Coordinate 0 0) :east)))
      (is (nil? (neighbour office (->Coordinate 0 0) :west)))
      (is (nil? (neighbour office (->Coordinate 0 0) :south)))
      (is (nil? (neighbour office (->Coordinate 0 0) :north)))))

  (testing "invalid directions generate errors"
    (let [office (populate-office 3 3 1)]
      (is (thrown? IllegalArgumentException (neighbour office 1 1 :up))))))

(deftest flood-filling
  (testing "an empty office is completely filled"
    (let [office (populate-office 10 10 0.0)
          visited-office (flood-fill office (->Coordinate 0 0))]
      (is (= 100 (count-visited visited-office)))))

  (testing "a fully occupied office is completely unfilled"
    (let [office (populate-office 10 10 1.0)
          visited-office (flood-fill office (->Coordinate 0 0))]
      (is (= 0 (count-visited visited-office)))))

  (testing "visiting an already visited location does nothing"
    (let [office (populate-office 2 2 0.0)
          _ (mark-location-as-visited! office (->Coordinate 0 0))
          visited-office (flood-fill office (->Coordinate 0 0))]
      (is (= (visited-location) (location-at visited-office (->Coordinate 0 0))))))

  (testing "visiting an occupied location does nothing"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (populated-location))
          visited-office (flood-fill office (->Coordinate 0 0))]
      (is (= (populated-location) (location-at visited-office (->Coordinate 0 0))))))

  (testing "visiting an unvisited location marks it as visited"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (empty-location))
          visited-office (flood-fill office (->Coordinate 0 0))]
      (is (= (visited-location) (location-at visited-office (->Coordinate 0 0)))))))
