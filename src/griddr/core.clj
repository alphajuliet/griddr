(ns griddr.core
  (:require [clojure.spec.alpha :as s]))

;; The grid is stored as a sparse array by a map with the coordinates as keys.
;; i.e. `{[0 0] :val-a [1 0] :val-b ...}`
;;
;; When matching edges, the tiles should be a map with a key `:edges`.
;; This value is used to match the edges of different tiles. `nil` is used to show there
;; is no edge present; this means it is a "don't care" value when matching.

;;--------------------------------
;; Utilities

(defn addv
  "Add two vectors"
  [u v]
  (mapv + u v))

(defn edge=?
  [a b]
  (if (nil? b) true
      (= a b)))

;;--------------------------------
;; Specs

(s/def ::coord-2d (s/tuple integer? integer?))

;;--------------------------------


;; The End
