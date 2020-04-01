(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.location :refer (->Location)]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :refer :all]))

(def ^:const origin (->Location 0 0))

(deftest path-finding
  (testing "an empty office is completely visited"
    (let [office (core/populate-office 10 10 0)
          visited-office (try-find-path office)]
      (is (= 100 (office/count-visited visited-office)))))

  (testing "a fully occupied office is completely unvisited"
    (dotimes [i 100]
      (let [office (core/populate-office 10 10 1)
            visited-office (try-find-path office)
            visited-count (office/count-visited visited-office)]
        (is (= 1 visited-count))))))

(deftest flood-filling

  (testing "visiting an already visited desk does nothing"
    (let [office (office/empty-square-of-width 2)
          _ (office/mark-desk-as-visited! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/visited) (office/desk-at visited-office origin)))))

  (testing "visiting an occupied desk does nothing"
    (let [office (office/empty-square-of-width 2)
          _ (office/mark-desk-as-populated! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/occupied) (office/desk-at visited-office origin)))))

  (testing "visiting an unvisited desk marks it as visited"
    (let [office (office/empty-square-of-width 2)
          _ (office/mark-desk-as-empty! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/visited) (office/desk-at visited-office origin))))))
