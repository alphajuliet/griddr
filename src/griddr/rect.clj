(ns griddr.rect
  (:require [griddr.core :as g]))

;;--------------------------------
;; Locations relative to [0 0] that share an edge
(def nn
  [[0 1] [1 0] [0 -1] [-1 0]])

(defn- edge-locations
  "Locations that share an edge."
  [[x y]]
  (mapv #(g/addv [x y] %) nn))

(defn edge-tiles
  "Get contents of all the locations with a shared edge"
  [grid [x y]]
  (mapv #(get grid %) (edge-locations [x y])))

(defn occupied-edge-locations
  "Return the occupied neighbour coords at a given coordinate."
  [grid [x y]]
  (filterv #(contains? grid %) (edge-locations [x y])))

(defn unoccupied-edge-locations
  "Return the unoccupied neighbour coords at a given coordinate.
   This is the opposite of `neighbours`."
  [grid [x y]]
  (remove #(contains? grid %) (edge-locations [x y])))

(defn perimeter-locations
  "List the coordinates of the perimeter locations, i.e. unoccupied spaces
  that border the existing grid."
  [grid]
  (->> (keys grid)
       (mapcat #(unoccupied-edge-locations grid %))
       distinct))

(defn neighbouring-edges
  "Return the neighbouring tile edges at a given location."
  ;; A little messy because of the different indices.
  [grid [x y]]
  (let [nn (edge-tiles grid [x y])]
    (vector (nth (:edges (nth nn 0)) 2)
            (nth (:edges (nth nn 1)) 3)
            (nth (:edges (nth nn 2)) 0)
            (nth (:edges (nth nn 3)) 1))))

(defn edge-match?
  "Does a given tile's edges fit at the given coordinate?"
  [grid [x y] tile]
  {:pre [(and (map? tile) (contains? tile :edges))]}
  (let [tile-edges (:edges tile)
        hole-edges (neighbouring-edges grid [x y])]
    (every? true? (map g/edge=? tile-edges hole-edges))))

(defn allowed-locations
  "Return the coordinates where `tile` can be placed on the grid."
  [grid tile]
  (filter #(edge-match? grid % tile) (perimeter-locations grid)))

;; The End
