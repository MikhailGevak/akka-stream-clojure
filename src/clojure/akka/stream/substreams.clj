(ns akka.stream.substreams
  (:require [scala.interop :as scala])
  (:import (akka.stream.scaladsl FlowOpsMat Source SubFlow FlowOps)
           (scala Int)))

(defn group-by
  ([^FlowOps stage max-substreams func allow-closed-substream-recreation]
   (let [scala-func (scala/fn [x] (func x))]
     (.groupBy stage max-substreams scala-func allow-closed-substream-recreation)))
  ([^FlowOps stage ^Int max-substreams func] (group-by stage max-substreams func false)))

(defn merge-substreams [^SubFlow subFlow parallelism] (.mergeSubstreamsWithParallelism subFlow parallelism))
