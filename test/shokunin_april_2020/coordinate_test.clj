(ns shokunin-april-2020.coordinate-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.coordinate :refer :all]))

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
