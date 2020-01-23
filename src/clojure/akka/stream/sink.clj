(ns akka.stream.sink
  (:require [scala.interop :as scala])
  (:import (akka.stream.scaladsl Sink)))

(defn ignore [] (Sink/ignore))

(defn for-each [func]
  (Sink/foreach (scala/fn [x] (scala/nil-to-unit (func x)))))

(defn seq [] (Sink/seq))

(defn ignore [] (Sink/ignore))
