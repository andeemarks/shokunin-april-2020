(ns shokunin-april-2020.office-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as test]
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

(test/defspec twer-always-in-back-row
  100
  (prop/for-all [number-of-rows (gen/fmap inc gen/nat)
                 number-of-desks (gen/fmap inc gen/nat)]
                (let [office (populate-office number-of-rows number-of-desks 1)]
                  (= 0 (:row (find-twer office))))))

(defn- approx [actual expected tolerance]
  (<= (dec expected) actual (inc expected)))

(deftest office-population
  (testing "office has specified dimensions"
    (is (= (count (populate-office 10 15 0.1)) 10))
    (is (= (count (first (populate-office 10 15 1.0))) 15)))

  (testing "randomly populates the office each time"
    (let [populated-office-1 (populate-office 3 3 0.4)
          populated-office-2 (populate-office 3 3 0.4)]
      (is (not (= populated-office-1 populated-office-2)))))

  (testing "randomly assigns the TWer in a desk in the back row"
    (is (not (= (:column (find-twer (populate-office 10 10 0.5)))
                (:column (find-twer (populate-office 10 10 0.5)))
                (:column (find-twer (populate-office 10 10 0.5)))))))

  (testing "populates the office according to the p value"
    (is (approx (count-occupied (populate-office 2 5 0.4)) 4 1))
    (is (approx (count-occupied (populate-office 10 10 0.2)) 20 1))
    (is (approx (count-occupied (populate-office 25 40 0.8)) 800 1))))

