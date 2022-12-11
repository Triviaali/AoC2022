(ns aoc2022.day09
  (:require [clojure.string :as str]))

(def test-data "R 4\nU 4\nL 3\nD 1\nR 4\nD 1\nL 5\nR 2")

(defn process-data [d]
  (->> d
       (str/split-lines)
       (map #(str/split % #" "))
       (map (fn [[dir times]] [dir (parse-long times)]))))

(def td (process-data test-data))

(def movement-map
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 -1]
   "D" [0 1]}
  )




(defn mover [dir coords]
  "Produces head directions"
  (map + coords (movement-map dir)))

(def testi ["R R R R"])

(defn paska [starting-point movement-list])