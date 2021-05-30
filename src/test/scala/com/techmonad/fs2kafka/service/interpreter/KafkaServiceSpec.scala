package com.techmonad.fs2kafka
package service
package interpreter

import com.techmonad.fs2kafka.model.KafkaConfig
import io.github.embeddedkafka.EmbeddedKafka.withRunningKafka
import io.github.embeddedkafka.EmbeddedKafkaConfig
import org.scalatest.Assertions
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class KafkaServiceSpec extends AnyWordSpec with Assertions with Matchers {

  "runs with embedded kafka and Schema Registry" should {

    "work" in {
      implicit val config = EmbeddedKafkaConfig(
        customBrokerProperties = Map(
          "transaction.state.log.replication.factor" -> "1",
          "transaction.abort.timed.out.transaction.cleanup.interval.ms" -> "1000"
        )
      )

      withRunningKafka {
        /*createCustomTopic("topic", partitions = 3)

        val request = KafkaRequest[Person](
          config = toKafkaConfig("topic", config),
          messages = List(Person("Lukas", "NL"), Person("Charlie", "US"))
        )

        val schema = SchemaRegistry.impl[IO, Person]
        val kafkaEnv = KafkaEnvironment.impl[IO, Person](schema)

        val service = KafkaService[Person](kafkaEnv)
        val produced = service.produce(request).unsafeRunSync()
        val consumed = service.consume(request).unsafeRunSync()

        consumed should contain theSameElementsAs produced*/
      }
    }

    def toKafkaConfig(topic: String, config: EmbeddedKafkaConfig): KafkaConfig =
      KafkaConfig(topic, s"localhost:${config.kafkaPort}", s"http://localhost:${config.zooKeeperPort}")

  }

  /*it("should be able to produce records with single") {

    withKafka { (config, topic) =>
      createCustomTopic(topic, partitions = 3)

      val request = KafkaRequest[Person](
        config = toKafkaConfig(topic, config),
        messages = List(Person("Lukas", "NL"), Person("Charlie", "US"))
      )

      val properties = KafkaProperties(request.config)
      val schema = SchemaRegistry.impl[IO, Person](request.config.schemaRegistry)
      val kafkaEnv = KafkaEnvironment.impl[IO, Person](properties, schema)

      val service = KafkaService[Person](kafkaEnv)
      val produced = service.produce(request).unsafeRunSync()
      val consumed = service.consume(request).unsafeRunSync()

      consumed should contain theSameElementsAs produced
    }
  }*/

}
