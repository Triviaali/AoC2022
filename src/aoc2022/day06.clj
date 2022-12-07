(ns aoc2022.day06
  (:require [aoc2022.utils :as utls]
            [clojure.string :as str]
            [clojure.pprint :as pprint]))


(defn find-marker
  ([s size] (find-marker 0 size s size))
  ([start end stringi size] (let [s (subvec (vec stringi) start end)]
                         (if (= (count (set s)) size)
                           end
                           (recur (inc start) (inc end) stringi size)))))


(def input-data (utls/read-file "data/day06input.txt"))

(find-marker input-data 14)
(find-marker input-data 4)

