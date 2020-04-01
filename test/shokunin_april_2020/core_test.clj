(ns shokunin-april-2020.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as test]
            [shokunin-april-2020.office :as office]
            [shokunin-april-2020.core :refer :all]))

(test/defspec twer-always-in-back-row
  100
  (prop/for-all [number-of-rows (gen/fmap inc gen/nat)
                 number-of-desks (gen/fmap inc gen/nat)]
                (let [office (office/populate-office number-of-rows number-of-desks 1)]
                  (= 0 (:row (office/find-twer office))))))

(defn- approx [actual expected tolerance]
  (<= (dec expected) actual (inc expected)))

(deftest office-population
  (testing "office has specified dimensions"
    (is (= (count (office/populate-office 10 15 0.1)) 10))
    (is (= (count (first (office/populate-office 10 15 1.0))) 15)))

  (testing "randomly populates the office each time"
    (let [populated-office-1 (office/populate-office 3 3 0.4)
          populated-office-2 (office/populate-office 3 3 0.4)]
      (is (not (= populated-office-1 populated-office-2)))))

  (testing "randomly assigns the TWer in a desk in the back row"
    (is (not (= (:column (office/find-twer (office/populate-office 10 10 0.5)))
                (:column (office/find-twer (office/populate-office 10 10 0.5)))
                (:column (office/find-twer (office/populate-office 10 10 0.5)))))))

  (testing "populates the office according to the p value"
    (is (approx (office/count-occupied (office/populate-office 2 5 0.4)) 4 1))
    (is (approx (office/count-occupied (office/populate-office 10 10 0.2)) 20 1))
    (is (approx (office/count-occupied (office/populate-office 25 40 0.8)) 800 1))))
