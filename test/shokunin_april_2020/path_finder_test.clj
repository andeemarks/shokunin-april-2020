(ns shokunin-april-2020.path-finder-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]
            [shokunin-april-2020.path-finder :refer :all]))

(deftest path-finding
  (testing "path found with completely empty office"
    (is (= true (path-exists? (populate-office 10 10 0)))))
  (testing "path not found with completely empty office"
    (is (= false (path-exists? (populate-office 10 10 1))))))
