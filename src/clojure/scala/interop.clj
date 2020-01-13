(ns scala.interop
  (:require [scala.internal :refer [$]])
  (:import (scala.collection Iterator)
           (scala.concurrent Future Await Awaitable ExecutionContext)
           (java.util.concurrent TimeUnit)
           (scala.concurrent.duration FiniteDuration)
           (scala Unit)))

(defn nil-to-unit [x]
  (if (nil? x) Unit x))

(defn to-scala-seq [seq]
  (let [scala-buf (scala.collection.mutable.ListBuffer.)]
    (doall (map #(.$plus$eq scala-buf %) (flatten [seq])))
    (.toStream scala-buf)))

(defmacro fn [params & body]
  (let [arity (count params)
        class (symbol (str "scala.Function" arity))]
    `(reify ~class
       (~'apply [this# ~@params]
         ~@body))))

(defn await [^Awaitable awaitable timeout]
       (Await/result awaitable (FiniteDuration/apply timeout "ms")))

(defn to-scala-future [^java.util.concurrent.Future future ^ExecutionContext context] (Future/apply (fn [] @future) context))

(def global ($ scala.concurrent.ExecutionContext$Implicits/global))