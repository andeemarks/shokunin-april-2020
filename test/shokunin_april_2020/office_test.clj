(ns shokunin-april-2020.office-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.coordinate :refer (->Coordinate)]
            [shokunin-april-2020.office :refer :all]))

(deftest path-finding
  (testing "path found when visited desk found in first row"
    (let [office (core/populate-office 2 2 0.0)
          _ (mark-desk-as-visited! office (->Coordinate 1 0))]
      (is (= true (path-exists? office)))))

  (testing "path not found when no visited desk found in first row"
    (let [office (core/populate-office 2 2 0.0)]
      (is (= false (path-exists? office))))))

(def ^:const origin (->Coordinate 0 0))
(def office (core/populate-office 1 1 0.0))

(deftest marking-desks
  (testing "desks can be marked as visited"
    (let [updated-office (mark-desk-as-visited! office origin)]
      (is (= (desk/visited) (desk-at updated-office origin)))))

  (testing "desks can be marked as empty"
    (let [updated-office (mark-desk-as-empty! office origin)]
      (is (= (desk/empty) (desk-at updated-office origin)))))

  (testing "desks can be marked as populated"
    (let [updated-office (mark-desk-as-populated! office origin)]
      (is (= (desk/populated) (desk-at updated-office origin))))))
