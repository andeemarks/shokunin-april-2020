(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.location :as loc]
            [shokunin-april-2020.coordinate :refer (->Coordinate)]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "path found when visited location found in first row"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-location-as-visited! office (->Coordinate 1 0))]
      (is (= true (path-exists? office)))))

  (testing "path not found when no visited location found in first row"
    (let [office (core/populate-office 2 2 0.0)]
      (is (= false (path-exists? office))))))

(deftest finding-neighbours
  (testing "northern neighbour returned when one exists"
    (let [office (core/populate-office 3 3 1)]
      (is (= (->Coordinate 2 1) (neighbour office (->Coordinate 1 1) :north)))))

  (testing "southern neighbour returned when one exists"
    (let [office (core/populate-office 3 3 1)]
      (is (= (->Coordinate 0 1) (neighbour office (->Coordinate 1 1) :south)))))

  (testing "western neighbour returned when one exists"
    (let [office (core/populate-office 3 3 1)]
      (is (= (->Coordinate 1 0) (neighbour office (->Coordinate 1 1) :west)))))

  (testing "eastern neighbour returned when one exists"
    (let [office (core/populate-office 3 3 1)]
      (is (= (->Coordinate 1 2) (neighbour office (->Coordinate 1 1) :east)))))

  (testing "nil returned when neighbour requested beyond office boundary"
    (let [office (core/populate-office 1 1 1)]
      (is (nil? (neighbour office (->Coordinate 0 0) :east)))
      (is (nil? (neighbour office (->Coordinate 0 0) :west)))
      (is (nil? (neighbour office (->Coordinate 0 0) :south)))
      (is (nil? (neighbour office (->Coordinate 0 0) :north)))))

  (testing "invalid directions generate errors"
    (let [office (core/populate-office 3 3 1)]
      (is (thrown?
          IllegalArgumentException
          (neighbour office (->Coordinate 1 1) :up))))))

(def ^:const origin (->Coordinate 0 0))

(deftest flood-filling
  (testing "an empty office is completely filled"
    (let [office (core/populate-office 10 10 0.0)
          visited-office (flood-fill office origin)]
      (is (= 100 (count-visited visited-office)))))

  (testing "a fully occupied office is completely unfilled"
    (let [office (core/populate-office 10 10 1.0)
          visited-office (flood-fill office origin)]
      (is (= 0 (count-visited visited-office)))))

  (testing "visiting an already visited location does nothing"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-location-as-visited! office origin)
          visited-office (flood-fill office origin)]
      (is (= (loc/visited) (office/location-at visited-office origin)))))

  (testing "visiting an occupied location does nothing"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-location-as-populated! office origin)
          visited-office (flood-fill office origin)]
      (is (= (loc/populated) (office/location-at visited-office origin)))))

  (testing "visiting an unvisited location marks it as visited"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-location-as-empty! office origin)
          visited-office (flood-fill office origin)]
      (is (= (loc/visited) (office/location-at visited-office origin))))))
