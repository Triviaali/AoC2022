(ns aoc2022.utils
  (:require [clojure.string :as str]))


(defn read-file [path]
  (slurp path))