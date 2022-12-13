(ns aoc2022.day09
  (:require [clojure.string :as str]))

(def test-data "R 4\nU 4\nL 3\nD 1\nR 4\nD 1\nL 5\nR 2")
(def input-data (slurp "data/day09input.txt"))

(defn process-data [d]
  (->> d
       (str/split-lines)
       (map #(str/split % #" "))
       (map (fn [[dir times]] [dir (parse-long times)]))))

(def td (process-data test-data))
(def id (process-data input-data))
(def movement-map
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 -1]
   "D" [0 1]})

(defn sign [x]
  (Math/signum (double x)))

(defn mover [[tail-x tail-y] [head-x head-y]]
  (if (or (> (abs (- tail-x head-x)) 1) (> (abs (- tail-y head-y)) 1))
    [(+ tail-x (sign (- head-x tail-x))) (+ tail-y (sign (- head-y tail-y)))]
    [tail-x tail-y]))


(defn solve-p1 [d]
  (->> d
       (mapcat (fn [[dir num]] (repeat num dir)))
       (map movement-map)
       (reductions (fn [e t] (map + e t)) [0 0])
       (reductions mover)
       (map (fn [x] (map int x)))
       set
       count))

(def path-dir (mapcat (fn [[dir num]] (repeat num dir)) td))
(def path-dir-nums (map movement-map path-dir))
(def td-path (reductions (fn [e t] (map + e t)) [0 0] path-dir-nums))

(defn moves-parsed [raw-data]
  (->> raw-data
       (mapcat (fn [[dir num]] (repeat num dir)))
       (map movement-map)
       (reductions (fn [e t] (map + e t)) [0 0])))

(->> (reductions mover td-path)
     (map (fn [x] (map int x)))
     set
     count)

(solve-p1 id)
(def p2-p (moves-parsed id))

(defn solve-p2 [p2]
  (->> (nth (iterate (partial reductions mover) p2) 9)
       (map str)
       (set)
       (count)))

(solve-p2 p2-p)