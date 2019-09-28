package com.techmonad.fs2kafka.model

import cats.implicits._
import vulcan.Codec

final case class KafkaConfig(topic: String, bootstrapServers: String, schemaRegistry: String)

trait KafkaRequest[D] {

  val messages: List[D]

}

final case class AvroKafkaRequest(config: KafkaConfig, messages: List[Person]) extends KafkaRequest[Person]

final case class StringKafkaRequest(config: KafkaConfig, messages: List[String]) extends KafkaRequest[String]

final case class Person(name: String, age: Int)

object Person {

  implicit val personCodec: Codec[Person] =
    Codec.record(
      name = "Person",
      namespace = Some("com.techmonad.fs2kafka.model")
    ) { field =>
      (
        field("name", _.name),
        field("age", _.age)
      ).mapN(Person(_, _))
    }

}
