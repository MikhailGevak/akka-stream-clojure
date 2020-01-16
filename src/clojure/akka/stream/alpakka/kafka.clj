(ns akka.stream.alpakka.kafka
  (:require [scala.internal :refer [$]])
  (:import (akka.kafka ConsumerSettings)
           (akka.actor ActorSystem)
           (org.apache.kafka.common.serialization Deserializer StringDeserializer)
           (java.time Duration)))

(defn consumer-settings
  ([^ActorSystem actorSystem
    ^Deserializer keyDeserializer
    ^Deserializer valueDeserializer
    {bootstrapServers :bootstrapServers
     group-id         :group-id
     stop-timeout     :stop-timeout
     properties       :properties}]
   (let [settings (-> (ConsumerSettings/apply actorSystem keyDeserializer valueDeserializer)
                     (.withBootstrapServers bootstrapServers)
                     (.withGroupId group-id)
                     (.withStopTimeout (Duration/ofMillis stop-timeout))
                     )]

     (reduce #(apply .withProperty (conj %1 %2)) settings properties)))
  ([^ActorSystem actorSystem config-map]
   (consumer-settings actorSystem (.new StringDeserializer) (.new StringDeserializer) config-map)))
