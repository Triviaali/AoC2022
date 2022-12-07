(ns aoc2022.day07
  (:require [aoc2022.utils :as utls]
            [clojure.string :as str]
            [clojure.pprint :as pprint]))


(def test-data-raw "$ cd /\n$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k")

(def test-data (str/split test-data-raw #"$ ls"))
(str/split test-data-raw #"[$ ls]+")

(def mappi {:/ "paska"})

(defn what-command [s]
  (cond
    (str/starts-with? "$ cd" s) :cd
    (str/starts-with? "$ ls" s) :ls
    :else :file))

(defn command-actions [command]
  (let [parse-cd (fn [s] (second (re-find #"cd (.)$" s)))]))

