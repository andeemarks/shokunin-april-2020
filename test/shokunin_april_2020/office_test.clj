(ns shokunin-april-2020.office-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.location :refer (->Location)]
            [shokunin-april-2020.office :refer :all]))

(def ^:const origin (->Location 0 0))
(def office (empty-square-of-width 2))

(deftest path-finding
  (testing "path found when visited desk found in first row"
    (let [_ (mark-desk-as-visited! office (->Location 1 0))]
      (is (true? (path-exists? office)))))

  (testing "path not found when no visited desk found in first row"
    (let [empty-office (empty-square-of-width 2)]
      (is (false? (path-exists? empty-office))))))

(deftest marking-desks
  (testing "desks can be marked as visited"
    (let [updated-office (mark-desk-as-visited! office origin)]
      (is (= (desk/visited) (desk-at updated-office origin)))))

  (testing "desks can be marked as empty"
    (let [updated-office (mark-desk-as-empty! office origin)]
      (is (= (desk/unoccupied) (desk-at updated-office origin)))))

  (testing "desks can be marked as populated"
    (let [updated-office (mark-desk-as-populated! office origin)]
      (is (= (desk/occupied) (desk-at updated-office origin))))))

(deftest finding-twer
  (testing "fails if no TWer is found"
    (is (thrown?
         IllegalStateException
         (find-twer (empty-square-of-width 2))))))

(deftest placing-twer
  (testing "placement is in last row"
    (dotimes [i 100]
      (let [updated-office (place-twer! office)]
        (is (= 0 (:row (find-twer updated-office))))))))
