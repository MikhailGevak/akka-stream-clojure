(ns akka.stream.substreams-test
  (:require [clojure.test :refer :all]
            [akka.stream.test-utils :refer :all]
            [akka.stream.substreams :as substream]
            [akka.stream.sink :as sink]
            [akka.stream.flow :as flow]
            [akka.stream.source :as source]
            [akka.stream.clojuredsl :as dsl]
            [scala.internal :refer [$]])
  (:import (scala.collection.immutable List)
           (scala.collection JavaConverters)))

(deftest substreams-test
  (let
    [graph (->
              (source/from-seq [1 2 3 4 5 6])
              (substream/group-by 3 #(mod % 3))
              (dsl/via (flow/from-function (pause)))
              (dsl/async)
              (substream/merge-substreams)
              (dsl/to-mat (sink/seq))
              )]
    (let [{result :result time :time} (run-graph-and-wait graph 3000)]
      (is (>= time 2000000000))
      (is (= (set (JavaConverters/seqAsJavaList result)) (set [1 2 3 4 5 6]))))))