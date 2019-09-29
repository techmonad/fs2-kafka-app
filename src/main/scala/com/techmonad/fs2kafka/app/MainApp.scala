package com.techmonad.fs2kafka.app

import cats.effect.IO
import com.techmonad.fs2kafka.config.{KafkaEnvironment, KafkaProperties, SchemaRegistry}
import com.techmonad.fs2kafka.model.{KafkaConfig, KafkaRequest, Person}
import com.techmonad.fs2kafka.service.interpreter.KafkaService
import org.slf4j.{Logger, LoggerFactory}

object MainApp extends App {

  val log: Logger = LoggerFactory.getLogger("MainApp")

  val request = KafkaRequest(
    config = KafkaConfig("topic", "localhost:9092", "http://localhost:9092"),
    messages = List(Person("Lukas", "NL"), Person("Charlie", "US"))
  )

  val properties = KafkaProperties(request.config)
  val schema = SchemaRegistry.impl[IO, Person](request.config.schemaRegistry)
  val kafkaEnv = KafkaEnvironment.impl[IO, Person](properties, schema)

  val service = KafkaService(kafkaEnv)
  val produced = service.produce(request).unsafeRunSync()
  val consumed = service.consume(request).unsafeRunSync()

  log.info(s"Produced: $produced")
  log.info(s"Consumed: $consumed")
}
