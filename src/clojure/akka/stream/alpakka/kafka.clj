(ns akka.stream.alpakka.kafka
  (:require [scala.internal :refer [$]])
  (:import (akka.kafka ConsumerSettings Subscription Subscriptions)
           (akka.actor ActorSystem)
           (org.apache.kafka.common.serialization Deserializer StringDeserializer)
           (java.time Duration)
           (akka.kafka.scaladsl Consumer)
           (scala.collection Set)))

(defn consumer-settings
  ([^ActorSystem actor-system
    ^Deserializer key-deserializer
    ^Deserializer value-deserializer
    {bootstrapServers :bootstrapServers
     group-id         :group-id
     stop-timeout     :stop-timeout
     properties       :properties}]
   (let [settings (-> (ConsumerSettings/apply actor-system key-deserializer value-deserializer)
                      (.withBootstrapServers bootstrapServers)
                      (.withGroupId group-id)
                      (.withStopTimeout (Duration/ofMillis stop-timeout))
                      )]

     (reduce #(.withProperty %1 (first %2) (second %2)) settings (partition 2 properties))))
  ([^ActorSystem actorSystem config-map]
   (consumer-settings actorSystem (new StringDeserializer) (new StringDeserializer) config-map)))

(defn- create-subscription [topic]
  (println topic)
  ($ Subscriptions/topics & topic))

(defn source-with-offset-context [^ConsumerSettings kafkaConsumerSettings, topic]
  (Consumer/sourceWithOffsetContext kafkaConsumerSettings (create-subscription topic)))

(defn source-plain [^ConsumerSettings kafkaConsumerSettings, topic]
  (Consumer/plainSource  kafkaConsumerSettings (create-subscription topic)))

(defn source-committable  [^ConsumerSettings kafkaConsumerSettings, topic]
  (Consumer/plainSource  kafkaConsumerSettings (create-subscription topic)))