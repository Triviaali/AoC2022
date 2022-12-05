(ns aoc2022.day04
  (:require [aoc2022.utils :as utls]
            [clojure.string :as str]
            [com.rpl.specter :as sp]
            [clojure.walk :as cw]))

(defn parse-section [s]
  (map #(str/split % #"-") s))

(defn process-ds [x]
  (if (string? x)
    (Integer/parseInt x)
    (identity x)))

(defn process-data [s]
  (->> s
       (map #(str/split % #","))
       (map parse-section)
       (cw/postwalk process-ds))
  )

(defn contained? [[f s]]
  (let [[f1 f2] f
        [s1 s2] s]
    (or (<= f1 s1 s2 f2) (<= s1 f1 f2 s2))))

(defn overlap? [[f s]]
  (let [[f1 f2] f
        [s1 s2] s]
    (not (cond
           (= f1 f2) (or (< f1 s1) (> f1 s2))
           (= s1 s2) (or (< s1 f1) (> s2 f2))
           :else (or (< f1 f2 s1 s2) (< s1 s2 f1 f2))))
    ))

(defn solve-part-1 [p-data]
  (count (filter contained? p-data)))

(defn solve-part-2 [p-data]
  (count (filter overlap? p-data)))

(def test-data (str/split-lines "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8"))
(def parsed-data (process-data test-data))
(solve-part-1 parsed-data)
(solve-part-2 parsed-data)

(def input-data (str/split-lines (utls/read-file "data/day04input.txt")))
(solve-part-1 (process-data input-data))
(solve-part-2 (process-data input-data))


;Jeesus ratkasu

(let [d (->> (slurp "data/day04input.txt")
             (re-seq #"\d+")
             (map parse-long)
             (partition 4))
      f (fn [[a1 a2 b1 b2]]
          (or (<= a1 b1 b2 a2)
              (<= b1 a1 a2 b2)))
      g (fn [[a1 a2 b1 b2]]
          (or (<= a1 b2 a2)
              (<= b1 a2 b2)))]
  (map #(count (filter % d)) [f g]))