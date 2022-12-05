(ns aoc2022.day02
  (:require [aoc2022.utils]
            [clojure.string :as str]))

;Example data

(def test-data "A Y\nB X\nC Z")

; Actual input

(def inp-data (aoc2022.utils/read-file "data/day02input.txt"))

(def letter-move-map {"A" :Rock "B" :Paper "C" :Scissors
                      "X" :Rock "Y" :Paper "Z" :Scissors})

(def move-condition-map {"A" :Rock "B" :Paper "C" :Scissors
                         "X" :Lose "Y" :Draw "Z" :Win})

(def move-points-map {:Rock 1 :Paper 2 :Scissors 3})

(def loser-map {:Paper    :Rock
                :Rock     :Scissors
                :Scissors :Paper})

(defn move-finder [p]
  (cond
    (apply = p) :Draw
    (= (set p) #{:Paper :Rock}) :Paper
    (= (set p) #{:Rock :Scissors}) :Rock
    (= (set p) #{:Paper :Scissors}) :Scissors))

(defn points-solver [pair]
  (let [[_ us] pair
        winner (move-finder pair)
        points (move-points-map us)]
    (cond
      (= winner :Draw) (+ points 3)
      (= winner us) (+ points 6)
      :else points)))

(defn return-correct-move [[opp strat]]
  (case strat
    :Draw opp
    :Win (loser-map (loser-map opp))
    :Lose (loser-map opp)))


(defn letter-to-move-parser [[fst snd]]
  [(letter-move-map fst) (letter-move-map snd)])

(defn letter-to-move-parser-p2 [[fst snd]]
  [(move-condition-map fst) (move-condition-map snd)])

(defn strat-to-move [p]
  (let [[eka _] p]
    [eka (return-correct-move p)]))

(defn solve-p1 [str-data]
  (let [split-str (str/split-lines str-data)]
    (->> split-str
         (map #(str/split % #" "))
         (map letter-to-move-parser)
         (map points-solver)
         (reduce +))))


(defn solve-p2 [str-data]
  (let [split-str (str/split-lines str-data)]
    (->> split-str
         (map #(str/split % #" "))
         (map letter-to-move-parser-p2)
         (map strat-to-move)
         (map points-solver)
         (reduce +))))

(solve-p1 test-data)
(solve-p1 inp-data)

(solve-p2 test-data)
(solve-p2 inp-data)
