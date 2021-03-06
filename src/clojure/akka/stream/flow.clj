(ns akka.stream.flow
  (:require [scala.interop :as scala])
  (:import (akka.stream.scaladsl Flow FlowOps)))

(defn from-function [func]
  (Flow/fromFunction (scala/fn [x] (scala/nil-to-unit (func x)))))
