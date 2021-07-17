(ns griddr.core-test
  (:require [griddr.core :as grid]
            #_[clojure.spec.alpha :as s]
            [clojure.test :refer [deftest testing is]]))

(def tiles [{:edges [1 0 1 1]}
            {:edges [1 0 1 1]}
            {:edges [0 0 0 0]}
            {:edges [0 0 1 0]}])

(deftest grid-tests
  (testing "Sanity tests"
    (is (= (+ 2 3) 5)))

  (testing "Neighbours"
    (let [m {[0 0] :a [1 0] :b}]
      (is (= 1 (count (grid/neighbours m [0 0]))))
      (is (= 6 (count (grid/perimeter m))))))

  (testing "Neighbouring edges"
    (let [grid {[0 0]  (nth tiles 0)
                [0 1]  (nth tiles 1)
                [1 0]  (nth tiles 2)
                [1 -1] (nth tiles 3)}
          t0 {:edges [0 0 0 0]}
          t1 {:edges [1 0 0 0]}
          t2 {:edges [1 0 1 0]}]
      (is (= 8 (count (grid/perimeter grid))))
      (is (= [nil nil 0 0]
             (grid/neighbouring-edges grid [1 1])))
      (is (grid/edge-match? grid [1 1] t1))
      (is (not (grid/edge-match? grid [1 1] t2)))
      (is '([1 1] [2 0] [2 -1]) (grid/allowed-placement grid t0)))))


;; The End
