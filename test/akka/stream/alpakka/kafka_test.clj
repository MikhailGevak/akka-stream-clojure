(ns akka.stream.alpakka.kafka-test
  (:require [clojure.test :refer :all]
            [akka.stream.alpakka.kafka :refer :all]
            [akka.stream.runner :refer [create-context]]))

(def akka-context (create-context "test"))

(deftest flow-test
  (let [settings (consumer-settings
                   (:system akka-context)
                                    {:bootstrapServers "server1,server2"
                                     :group-id "group-id"
                                     :stop-timeout 1000
                                     :properties ["property1" "value1" "property2" "value2"]})]

    (println (.getProperties settings))))