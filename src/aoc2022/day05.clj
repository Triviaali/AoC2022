(ns aoc2022.day05
  (:require [aoc2022.utils :as utls]
            [clojure.string :as str]))

(defn char-seq-to-str-and-parse [charsek]
  (let [s (apply str charsek)
        res (re-find #"[A-Z\d]" s)]
    (res)))

(char-seq-to-str-and-parse '(\space \space \space \space))
(char-seq-to-str-and-parse '(\space \1 \space \space))

(defn list-to-map [sekv, mappi]
  (assoc mappi (first sekv) (vec (rest sekv))))


(defn process-structure-input [stri]
  (->> stri
       (str/split-lines)
       (map #(partition-all 4 %))
       (map (fn [cs] (map char-seq-to-str-and-parse cs)))
       (apply map vector)
       (map reverse)
       (map (fn [v] (filter #(not (nil? %)) v)))
       (reduce (fn [acc elem] (list-to-map elem acc)) {})
       (into (sorted-map))))

(defn process-movement-input [stri]
  (->> stri
       (str/split-lines)
       (map #(re-seq #"\d+" %))))

(defn make-moves [structure movements]
  (let [[times from to] movements
        moves (take-last (parse-long times) (structure from))
        new-first (vec (drop-last (parse-long times) (structure from)))
        new-second (vec (concat (structure to) (reverse moves)))]
    (assoc structure to new-second from new-first)))

(defn make-moves-p2 [structure movements]
  (let [[times from to] movements
        moves (take-last (parse-long times) (structure from))
        new-first (vec (drop-last (parse-long times) (structure from)))
        new-second (vec (concat (structure to) moves))]
    (assoc structure to new-second from new-first)))


(def test-data-raw "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2")
(def test-structure-movements (str/split test-data-raw #"\n\n"))
(def original-structure (process-structure-input (first test-structure-movements)))
(def movements (process-movement-input (second test-structure-movements)))

(def test-data-p1 (map #(last %) (vals (reduce (fn [mappi moves] (make-moves mappi moves)) original-structure movements))))

(def input-data (utls/read-file "data/day05input.txt"))
(def p1-structure-raw (first (str/split input-data #"\n\n")))

(def p1-structure (process-structure-input p1-structure-raw))
(def p1-movements (process-movement-input (second (str/split input-data #"\n\n"))))

(def p-1 (map #(last %) (vals (reduce (fn [mappi moves] (make-moves mappi moves)) p1-structure p1-movements))))
(def p-2 (map #(last %) (vals (reduce (fn [mappi moves] (make-moves-p2 mappi moves)) p1-structure p1-movements))))
