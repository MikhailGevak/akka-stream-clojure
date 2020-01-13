(ns akka.stream.source
  (:require [scala.interop :refer [to-scala-seq]])
  (:import (akka.stream.scaladsl Source)))

(defn from-seq [sequence] (Source/apply (to-scala-seq sequence)))

(defn single [element] (Source/single element))
