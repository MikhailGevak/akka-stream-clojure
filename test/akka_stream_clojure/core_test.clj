(ns akka-stream-clojure.core-test
  (:require [clojure.test :refer :all]
            [scala.internal :refer [$]]
            [akka.stream.clojuredsl :as dsl]
            [akka.stream.runner :as runner]
            [akka.stream.source :as source]
            [akka.stream.sink :as sink]
            [akka.stream.flow :as flow]
            [scala.interop :as scala])
  (:import (scala Unit)
           (scala.collection.immutable List)
           (akka.stream.scaladsl RunnableGraph)))

(def context (runner/create-context "test-system"))

(defn run-graph-and-wait
  ([graph timeout] (scala/await (runner/run ^RunnableGraph graph (:mat context)) timeout))
  ([graph] (run-graph-and-wait graph 1000)))

(deftest flow-test
  (let
    [graph (dsl/->
             (source/from-seq [1 2 3 4 5])
             #(* 2 %)
             inc
             (sink/seq))]
    (is (= ($ List & 3 5 7 9 11) (run-graph-and-wait graph)))))

(deftest buffer-test
  (let
    [graph (dsl/->
             (source/from-seq [1 2 3 4 5])
             (dsl/buffer 10)
             (sink/seq))]

    (is (= ($ List & 1 2 3 4 5) (run-graph-and-wait graph))))
  )

(deftest map-async-test
  (let [graph (dsl/->
                (dsl/map-async (source/from-seq [1 2 3 4 5])
                               #(future (do (Thread/sleep 1000) %)) 5 (:execution-context context))
                (sink/seq))]
    (is (= ($ List & 1 2 3 4 5) (run-graph-and-wait graph 2000)))))


(deftest scala-future-test
  (let [future (future 10)]
    (is (= 10 (scala/await (scala/to-scala-future future scala/global) 1000)))))