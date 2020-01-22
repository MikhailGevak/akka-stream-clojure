(ns akka.stream.substreams-test
  (:require [clojure.test :refer :all]
            [akka.stream.test-utils :refer :all]
            [akka.stream.substreams :as substream]
            [akka.stream.sink :as sink]
            [akka.stream.source :as source]
            [akka.stream.clojuredsl :as dsl]
            [scala.internal :refer [$]])
  (:import (scala.collection.immutable List)))

(deftest substreams-test
  (let
    [source (substream/group-by (source/from-seq [1 2 3 4 5 6]) 3 #(mod % 3))]
    (is (= ($ List & 3 5 7 9 11) (run-graph-and-wait ((dsl/-> source inc (sink/seq))))))))