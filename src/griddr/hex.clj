(ns griddr.hex
  (:require [griddr.core]))

;;--------------------------------
;; Definitions of neighbours in different grids, starting from the immediate right (0 degrees) and going counterclockwise.
;; See https://www.redblobgames.com/grids/parts/
;;

(def nn
  [[1 0] [0 1] [-1 1] [-1 0] [0 -1] [1 -1]])


;; The End
