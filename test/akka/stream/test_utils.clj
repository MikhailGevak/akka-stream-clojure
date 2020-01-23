(ns akka.stream.test-utils
  (:require [clojure.test :refer :all]
            [akka.stream.runner :as runner]
            [scala.interop :as scala])
  (:import (akka.stream.scaladsl RunnableGraph)))

(def context (runner/create-context "test-system"))

(defn run [^RunnableGraph graph] (runner/run ^RunnableGraph graph (:mat context)))

(defn run-graph-and-wait
  ([graph timeout]
   (let [start-time (System/nanoTime)]
     (let [result (scala/await (run graph) timeout)]
       {:result result
        :time   (- (System/nanoTime) start-time)})))
  ([graph] (run-graph-and-wait graph 1000)))

(defn pause
  ([millis] (fn [x] (Thread/sleep millis) x))
  ([] (pause 1000)))