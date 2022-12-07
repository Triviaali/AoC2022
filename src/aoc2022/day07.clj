(ns aoc2022.day07
  (:require [aoc2022.utils :as utls]
            [clojure.string :as str]
            [clojure.pprint :as pprint]
            [clojure.walk :as cw]))


(def test-data-raw "$ cd /\n$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k")

(def test-data (str/split-lines test-data-raw))

(defn what-command [s]
  (cond
    (str/includes? s "$ cd ..") :cdb
    (str/includes? s "$ cd") :cd
    (str/starts-with? s "$ ls") :ls
    :else :file))

(defn command-actions [command]
  (let [parse-cd (fn [s] (second (re-find #"cd (.)$" s)))]
    (parse-cd command)))

(defn tw [xs]
  (let [fxs (take-while #(= (what-command %) :file) xs)
        rxs (drop-while #(= (what-command %) :file) xs)]
    [fxs rxs]))
(defn iter-commands [cmds cur-loc m]
  (if (empty? cmds)
    m
    (let [cmd (first cmds)
          c-type (what-command cmd)]
        (case c-type
          :cd (let [kw-dir (keyword (command-actions cmd))
                    new-loc (conj cur-loc kw-dir)]
                (iter-commands (rest cmds) new-loc (assoc-in m new-loc {})))

          :ls (let [[filut restit] (tw (rest cmds))]
                (iter-commands restit cur-loc (assoc-in m (conj cur-loc :files) filut)))

          :cdb (iter-commands (rest cmds) (vec (drop-last cur-loc)) m)))))

(defn numberi [s]
  (->> s
       (re-find #"^[0-9]+")
       (filter #(not (nil? %)))
       (apply str)
       (parse-long)))



(defn find-size [m]
  (if (and (map? m) (contains? m :files))
    (assoc m :filesizes (reduce + (filter number? (map numberi (get m :files)))))
    (identity m)))

(defn find-paths [m cur-path]
  (let [[k v] (seq m)]))

(map what-command test-data)
(cw/postwalk find-size (iter-commands test-data [] {}))
(seq (cw/postwalk find-size (iter-commands test-data [] {})))

(def p "dir a\n14848514 b.txt\n8504156 c.dat\ndir dd\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k\n")
(def pp (str/split-lines p))


(tw pp)