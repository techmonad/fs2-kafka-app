package com.techmonad.fs2kafka.model

import cats.implicits._
import vulcan.Codec

final case class KafkaConfig(topic: String, bootstrapServers: String, schemaRegistry: String)

final case class KafkaRequest[V](config: KafkaConfig, messages: List[V])

final case class Person(name: String, address: String)

object Person {

  implicit val personCodec: Codec[Person] =
    Codec.record(
      name = "Person",
      namespace = Some("com.techmonad.fs2kafka.model")
    ) { field =>
      (
        field("name", _.name),
        field("age", _.address)
      ).mapN(Person(_, _))
    }

}
