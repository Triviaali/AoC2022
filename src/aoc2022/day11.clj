(ns aoc2022.day11
  (:require [clojure.string :as str]))

(def test-monkey "Monkey 0:\n  Starting items: 79, 98\n  Operation: new = old * 19\n  Test: divisible by 23\n    If true: throw to monkey 2\n    If false: throw to monkey 3")
(str/split-lines test-monkey)


(defn monkey-str->m [[monkey, starting-items operation worry-test bool-first bool-second]])