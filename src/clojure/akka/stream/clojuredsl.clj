(ns akka.stream.clojuredsl
  (:require [scala.interop :as scala]
            [akka.stream.flow :as flow])
  (:import (akka.stream.scaladsl FlowOpsMat RunnableGraph Source Sink Keep Flow FlowOps)
           (akka.stream Materializer Graph ActorMaterializer OverflowStrategy)
           (akka.actor ActorSystem)))

(defn to [^FlowOpsMat stage1 ^Graph sink] (.to stage1 sink))

(defn to-mat [^Source source ^Sink sink] (.toMat source sink (Keep/right)))

(defn via [^FlowOpsMat stage1 stage2]
  (cond
    (instance? FlowOps stage2)  (.via stage1 stage2)
    (instance? Sink stage2) (to-mat stage1 stage2))
  )

(defn map [^FlowOpsMat stage func] (.map stage (scala/fn [x] (scala/nil-to-unit (func x)))))

(defn map-async [^FlowOpsMat stage future-func parallelism executionContext]
  (.mapAsync
    stage
    parallelism
    (scala/fn [x] (scala/to-scala-future (future-func x) executionContext))
    ))

(defn buffer
  ([^FlowOps stage size ^OverflowStrategy strategy] (.buffer stage size strategy))
  ([^FlowOps stage size] (buffer stage size (OverflowStrategy/backpressure)))
  ([size] (buffer (flow/from-function identity) size)))

(defn- to-flow [x]
  (cond
    (instance? FlowOpsMat x) x
    (instance? Sink x) x
    :else (flow/from-function x)))

(defn -> [& stages] (reduce #(via (to-flow %1) (to-flow %2)) stages))
