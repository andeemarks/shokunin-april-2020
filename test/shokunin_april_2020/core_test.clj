(ns shokunin-april-2020.core-test
  (:require [clojure.test :refer :all]
            [shokunin-april-2020.core :refer :all]))

(deftest office-construction
  (testing "office has specified dimensions"
    (is (= (count(build-office 10 15)) 10)))
    (is (= (count(first (build-office 10 15))) 15))
    )
