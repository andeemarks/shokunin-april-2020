(ns shokunin-april-2020.location-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.location :refer :all]))

(deftest finding-neighbours
  (testing "northern neighbour returned when one exists"
    (let [office (office/empty-square-of-width 3)]
      (is (= (->Location 2 1) (neighbour office (->Location 1 1) :north)))))

  (testing "southern neighbour returned when one exists"
    (let [office (office/empty-square-of-width 3)]
      (is (= (->Location 0 1) (neighbour office (->Location 1 1) :south)))))

  (testing "western neighbour returned when one exists"
    (let [office (office/empty-square-of-width 3)]
      (is (= (->Location 1 0) (neighbour office (->Location 1 1) :west)))))

  (testing "eastern neighbour returned when one exists"
    (let [office (office/empty-square-of-width 3)]
      (is (= (->Location 1 2) (neighbour office (->Location 1 1) :east)))))

  (testing "nil returned when neighbour requested beyond office boundary"
    (let [office (office/empty-square-of-width 1)]
      (is (nil? (neighbour office (->Location 0 0) :east)))
      (is (nil? (neighbour office (->Location 0 0) :west)))
      (is (nil? (neighbour office (->Location 0 0) :south)))
      (is (nil? (neighbour office (->Location 0 0) :north)))))

  (testing "invalid directions generate errors"
    (let [office (office/empty-square-of-width 3)]
      (is (thrown?
           IllegalArgumentException
           (neighbour office (->Location 1 1) :up))))))
