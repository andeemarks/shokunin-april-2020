(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]
            [shokunin-april-2020.location :refer :all]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "path found with completely empty office"
    (is (= true (path-exists? (populate-office 10 10 0)))))
  (testing "path not found with completely empty office"
    (is (= true (path-exists? (populate-office 10 10 1))))))

(deftest flood-filling
  (testing "visiting an already visited location does nothing"
    (let [office (populate-office 2 2 0.0)
          _ (aset office 0 0 (visited-location))
          visited-office (flood-fill office 0 0)]
      (is (= (visited-location) (aget office 0 0)))))
    
  (testing "visiting an occupied location does nothing"
    (let [office (populate-office 2 2 1.0)
          _ (aset office 0 0 (populated-location))
          visited-office (flood-fill office 0 0)]
      (is (= (populated-location) (aget office 0 0)))))

  (testing "visiting an unvisited location marks it as visited"
    (let [office (populate-office 2 2 1.0)
          _ (aset office 0 0 (empty-location))
          visited-office (flood-fill office 0 0)]
      (is (= (visited-location) (aget office 0 0))))))
