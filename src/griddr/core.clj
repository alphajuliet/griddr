(ns griddr.core
  (:require [clojure.spec.alpha :as s]))

;; The grid is stored as a sparse array by a map with the coordinates as keys.
;; i.e. {[0 0] :val-a [1 0] :val-b ...}
;;
;; When looking at the edges, the stored items should be a map with a key :edges.
;; This value is used to match the edges of different tiles. `nil` is used to show there
;; is no edge present, and so is a "don't care" value when matching.

;;--------------------------------
;; Specs

(s/def ::coord-2d (s/tuple integer? integer?))

;;--------------------------------
(defn- neighbourhood
  "The immediate neighbourhood."
  [[x y]]
  (vector [x (inc y)] [(inc x) y] [x (dec y)] [(dec x) y]))

(defn all-neighbours
  [grid [x y]]
  (map #(get grid %) (neighbourhood [x y])))

(defn neighbours
  "Return the occupied neighbour coords at a given coordinate."
  [grid [x y]]
  (filter #(contains? grid %) (neighbourhood [x y])))

(defn no-neighbours
  "Return the unoccupied neighbour coords at a given coordinate.
   This is the opposite of `neighbours`."
  [grid [x y]]
  (remove #(contains? grid %) (neighbourhood [x y])))

(defn perimeter
  "List the coordinates of the perimeter locations, i.e. unoccupied spaces
  that border the existing grid."
  [grid]
  (->> (keys grid)
       (mapcat #(no-neighbours grid %))
       distinct))

(defn neighbouring-edges
  "Return the tile edges of the neighbours at the given coordinate."
  ;; A little messy because of the different indices.
  [grid [x y]]
  (let [nn (all-neighbours grid [x y])]
    (vector (nth (:edges (nth nn 0)) 2)
            (nth (:edges (nth nn 1)) 3)
            (nth (:edges (nth nn 2)) 0)
            (nth (:edges (nth nn 3)) 1))))

(defn- edge=?
  [a b]
  (if (nil? b) true
      (= a b)))

(defn edge-match?
  "Does a given tile's edges fit at the given coordinate?"
  [grid [x y] tile]
  {:pre [(and (map? tile) (contains? tile :edges))]}
  (let [tile-edges (:edges tile)
        hole-edges (neighbouring-edges grid [x y])]
    (every? true? (map edge=? tile-edges hole-edges))))

(defn allowed-placement
  "Return the coordinates where `tile` can be placed on the grid."
  [grid tile]
  (filter #(edge-match? grid % tile) (perimeter grid)))

;; The End
