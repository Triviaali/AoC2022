(ns aoc2022.day08
  (:require [clojure.string :as str]))

(def test-data "30373\n25512\n65332\n33549\n35390")
(def forest (->> test-data
                 str/split-lines
                 (map (fn [s] (map #(parse-long (str %)) s)))
                 (map vec)))


(defn prepare-input [d] (vec (->> d
                                  str/split-lines
                                  (map (fn [s] (map #(parse-long (str %)) s)))
                                  (map vec))))

(def input-data (prepare-input (slurp "data/day08input.txt")))

(def forst (vec forest))

(defn w-h [f]
  (let [height (count f)
        width (count (get f 0))]
    [width height]))

(defn taller? [tree vision-trees]
  (every? true? (map #(> tree %) vision-trees)))

(defn viewing-distance-row [tree blockers]
  (let [[vis blkd] (split-with #(< % tree) blockers)]
  (if
    (empty? blkd)
    (count vis)
    (+ (count vis) 1))))

(viewing-distance-row 5 [3 3])

(defn visible? [forest coordinates]
  (let [[x y] coordinates
        tree (get-in forest [y x])
        left (reverse (take x (get forest y)))
        right (drop (inc x) (get forest y))
        vert (map #(get % x) forest)
        top (reverse (take y vert))
        bottom (drop (inc y) vert)]
    (do
      (println left)
      (println right)
      (println top)
      (println bottom)
      )
    (some true? (map #(taller? tree %) [left right top bottom]))))

(defn viewing-distance [forest coordinates]
  (let [[x y] coordinates
        tree (get-in forest [y x])
        left (reverse (take x (get forest y)))
        right (drop (inc x) (get forest y))
        vert (map #(get % x) forest)
        top (reverse (take y vert))
        bottom (drop (inc y) vert)]
    (do
      (println left)
      (println right)
      (println top)
      (println bottom)
      )
    (reduce * (map #(viewing-distance-row tree %) [left right top bottom]))))

(defn iter-trees [forest]
  (let [[w h] (w-h forest)]
    (for [x (range 1 (dec w))
          y (range 1 (dec h))]
      (visible? forest [x y]))))

(defn iter-trees-p2 [forest]
  (let [[w h] (w-h forest)]
    (for [x (range 1 (dec w))
          y (range 1 (dec h))]
      (viewing-distance forest [x y]))))

(apply max (iter-trees-p2 forst))

(iter-trees forst)

(defn solve-p1 [forest]
  (let [outer (* 4 ((comp dec first) (w-h forest)))
        visible-from-inside (iter-trees forest)
        countti (count (filter true? visible-from-inside))]
    (do (println outer)
        (println countti))
    (+ outer countti)))

(defn solve-p2 [forest]
  (apply max (iter-trees-p2 forest)))

(solve-p1 forst)
(solve-p1 input-data)
(w-h input-data)


(solve-p2 input-data)