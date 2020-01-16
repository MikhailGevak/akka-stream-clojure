(def akka-version "2.6.0")
(def alpakka-kafka-version "2.0.0")

(defproject akka-stream-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :source-paths ["src/clojure"]
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 [org.scala-lang/scala-library "2.13.0"]
                 [com.typesafe.akka/akka-stream_2.13 ~akka-version]
                 [com.typesafe.akka/akka-actor_2.13 ~akka-version]
                 [com.typesafe.akka/akka-stream-kafka_2.13 ~alpakka-kafka-version]
                 [funcool/cats "1.2.1"
                  :exclusions [com.keminglabs/cljx
                               org.clojure/clojurescript]]]
  :repl-options {:init-ns akka-stream-clojure.core})
