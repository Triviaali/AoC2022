(ns aoc2022.day03
  (:require [aoc2022.utils]
            [clojure.string :as str]
            [clojure.set :as setti]))


(def test-d "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")
(def kasitelty (str/split-lines test-d))



(defn kahtia [s]
  (split-at (/ (count s) 2) s))

(defn inttersektion [[eka toka]]
  (setti/intersection (set eka) (set toka)))

(defn uusi-parempi-intersektion [s]
  (let [setteina (map set s)]
    (apply setti/intersection setteina)))

(def lista (map #(str/split % #"") kasitelty))
(def kahtiajako (map kahtia lista))
(def intrs (map inttersektion kahtiajako))

(def kirjaimet (concat (map #(str (char %)) (range 97 123))
                       (map #(str (char %)) (range 65 91))))

(def kirjaimet2 (concat (map char (range 97 123))
                       (map char (range 65 91))))

(def numerot (range 1 53))
(def valmap (zipmap kirjaimet2 numerot))

(map valmap (flatten intrs))
(def kirjain-lista (flatten (map first intrs)))
(reduce + (map valmap kirjain-lista))

(defn prosessoi-input [path]
  (let [s (aoc2022.utils/read-file path)]
    (->> s
         (str/split-lines)
         (map kahtia)
         (map inttersektion)
         (map first)
         (map valmap))))

(defn prosessoi-input-part2 [path]
  (let [s (aoc2022.utils/read-file path)]
    (->> s
         (str/split-lines)
         (partition 3)
         (map uusi-parempi-intersektion)
         (map first)
         (map valmap))))

(reduce + (prosessoi-input-part2 "data/day03input.txt"))

(reduce + (prosessoi-input "data/day03input.txt"))
