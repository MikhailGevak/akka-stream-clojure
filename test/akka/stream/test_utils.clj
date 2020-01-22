(ns akka.stream.test-utils
  (:require [clojure.test :refer :all]
            [akka.stream.runner :as runner]
            [scala.interop :as scala])
  (:import (akka.stream.scaladsl RunnableGraph)))

(def context (runner/create-context "test-system"))

(defn run-graph-and-wait
  ([graph timeout] (scala/await (runner/run ^RunnableGraph graph (:mat context)) timeout))
  ([graph] (run-graph-and-wait graph 1000)))