(ns akka.stream.flow
  (:require [scala.interop :as scala])
  (:import (akka.stream.scaladsl Flow FlowOps)))

(defn from-function [func]
  (Flow/fromFunction (scala/fn [x] (scala/nil-to-unit (func x)))))

(defn map-async [^FlowOps flow func parallelizm]
  (let [future-func (fn [x] (scala/to-scala-future (future (func x))))]
    (.mapAsync flow parallelizm future-func)))