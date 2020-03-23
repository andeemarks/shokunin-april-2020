(ns shokunin-april-2020.core-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]))

(deftest office-construction
  (testing "office has specified dimensions"
    (is (= (count(build-office 10 15)) 10)))
    (is (= (count(first (build-office 10 15))) 15))
    )

(deftest office-population
  (testing "changes the previous office"
    (let [office (build-office 3 3)]
      (is (not (= (populate-office office 0.4) office))))))
