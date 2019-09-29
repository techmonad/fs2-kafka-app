package com.techmonad.fs2kafka
package service
package interpreter

import cats.effect.IO
import com.techmonad.fs2kafka.config.{KafkaEnvironment, KafkaProperties, SchemaRegistry}
import com.techmonad.fs2kafka.model.{KafkaRequest, Person}
import com.techmonad.fs2kafka.spec.BaseKafkaSpec
import io.confluent.kafka.schemaregistry.avro.AvroCompatibilityLevel
import net.manub.embeddedkafka.schemaregistry.EmbeddedKafkaConfig

class KafkaServiceSpec extends BaseKafkaSpec {

  it("should be able to produce records with single") {

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
  }

}
