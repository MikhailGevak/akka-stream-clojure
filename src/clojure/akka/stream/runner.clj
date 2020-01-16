(ns akka.stream.runner
  (:import (akka.stream Materializer ActorMaterializer)
           (akka.stream.scaladsl RunnableGraph Source Sink)
           (akka.actor ActorSystem)))

(defn create-context [actor-system-name]
  (let [system (ActorSystem/apply actor-system-name)] {
                                                       :system system
                                                       :mat (ActorMaterializer/apply system)
                                                       :execution-context (.dispatcher system)}))

(defn run ([^RunnableGraph graph ^Materializer mat] (.run graph mat)))

(defn run-with [^Source source ^Sink sink ^Materializer mat] (.runWith source sink mat))
