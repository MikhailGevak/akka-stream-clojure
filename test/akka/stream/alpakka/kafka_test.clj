(ns akka.stream.alpakka.kafka-test
  (:require [clojure.test :refer :all]
            [akka.stream.alpakka.kafka :refer :all]
            [akka.stream.runner :refer [create-context]]))

(def akka-context (create-context "test"))

(deftest flow-test
  (let [settings (.getProperties (consumer-settings
                                   (:system akka-context)
                                   {:bootstrapServers "server1,server2"
                                    :group-id         "group-id"
                                    :stop-timeout     1000
                                    :properties       ["property1" "value1" "property2" "value2"]}))]

    (is (= "group-id" (.get settings "group.id")))
    (is (= "server1,server2" (.get settings "bootstrap.servers")))
    (is (= "value1" (.get settings "property1")))
    (is (= "value2" (.get settings "property2")))))