(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.desk :as desk]
            [shokunin-april-2020.coordinate :refer (->Coordinate)]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "an empty office is completely visited"
    (let [office (core/populate-office 10 10 0.0)
          visited-office (try-find-path office)]
      (is (= 100 (office/count-visited visited-office)))))

  (testing "a fully occupied office is completely unvisited"
    (dotimes [i 100]
      (let [office (core/populate-office 10 10 1.0)
            visited-office (try-find-path office)
            visited-count (office/count-visited visited-office)]
        (is (= 1 visited-count))))))

(def ^:const origin (->Coordinate 0 0))

(deftest flood-filling

  (testing "visiting an already visited location does nothing"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-desk-as-visited! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/visited) (office/desk-at visited-office origin)))))

  (testing "visiting an occupied location does nothing"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-desk-as-populated! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/populated) (office/desk-at visited-office origin)))))

  (testing "visiting an unvisited location marks it as visited"
    (let [office (core/populate-office 2 2 0.0)
          _ (office/mark-desk-as-empty! office origin)
          visited-office (flood-fill office origin)]
      (is (= (desk/visited) (office/desk-at visited-office origin))))))
