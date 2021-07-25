(ns griddr.rect-test
  (:require [griddr.rect :as grid]
            #_[clojure.spec.alpha :as s]
            [clojure.test :refer [deftest testing is]]))

;;--------------------------------
;; Test grid
(def grid0 {[0 0] :a [1 0] :b})
(def grid1 {[0 0]  {:edges [1 0 1 1]}
            [0 1]  {:edges [1 0 1 1]}
            [1 0]  {:edges [0 0 0 0]}
            [1 -1] {:edges [0 0 1 0]}})

;;--------------------------------
(deftest rect-grid-tests
  (testing "Sanity tests"
    (is (= (+ 2 3) 5)))

  (testing "Neighbours"
    (let []
      (is (= 6 (count (grid/perimeter-locations grid0))))
      (is (= 1 (count (grid/occupied-edge-locations grid0 [0 0]))))
      (is (= 3 (count (grid/unoccupied-edge-locations grid0 [0 0]))))))

  (testing "Neighbouring edges"
    (let [t0 {:edges [0 0 0 0]}
          t1 {:edges [1 1 0 0]}
          t2 {:edges [1 0 1 0]}]
      (is (= 8 (count (grid/perimeter-locations grid1))))
      (is (= [nil nil 0 0] (grid/neighbouring-edges grid1 [1 1])))
      (is (grid/edge-match? grid1 [1 1] t1))
      (is (not (grid/edge-match? grid1 [1 1] t2)))
      (is '([1 1] [2 0] [2 -1]) (grid/allowed-locations grid1 t0))))

  (testing "Rotate and flip"
    (let [t0 {:edges [0 0 0 0]}
          t1 {:edges [1 1 0 0]}
          t2 {:edges [1 0 1 0]}]
      (is (= [5 8 7 6] (grid/hflip [5 6 7 8])))
      (is (= [7 6 5 8] (grid/vflip [5 6 7 8])))
      (is (= [8 5 6 7] (grid/rotate-right [5 6 7 8]))))))

;; The End
