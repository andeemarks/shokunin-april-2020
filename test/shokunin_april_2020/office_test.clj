(ns shokunin-april-2020.office-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :as core]
            [shokunin-april-2020.coordinate :refer (->Coordinate)]
            [shokunin-april-2020.office :refer :all]))

(deftest path-finding
  (testing "path found when visited location found in first row"
    (let [office (core/populate-office 2 2 0.0)
          _ (mark-location-as-visited! office (->Coordinate 1 0))]
      (is (= true (path-exists? office)))))

  (testing "path not found when no visited location found in first row"
    (let [office (core/populate-office 2 2 0.0)]
      (is (= false (path-exists? office))))))
