(ns aoc2022.day01
  (:require [clojure.string :as str]))

(def test-input "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")
(def parsittu (map str/split-lines (str/split test-input #"\n\n")))

test-input
(defn string-splitter [s]
  (map str/split-lines (str/split s #"\n\n")))
(defn inttaaja [lista] (map #(Integer/parseInt %) lista))
(def parsittu-int (map #(reduce + %) (map inttaaja parsittu)))
(def vastaus (apply max parsittu-int))

(def day01input (slurp "data/day01input"))

(->> day01input
     inttaaja
     string-splitter)

(apply max (map #(reduce + %) (map inttaaja (string-splitter day01input))))

(->> day01input
     (string-splitter)
     (map inttaaja)
     (map #(reduce + %))
     (apply max))


(->> test-input
     (string-splitter)
     (map inttaaja)
     (map #(reduce + %))
     (sort)
     (reverse)
     (take 3)
     (reduce +))

(->> day01input
     (string-splitter)
     (map inttaaja)
     (map #(reduce + %))
     (sort)
     (reverse)
     (take 3)
     (reduce +))

