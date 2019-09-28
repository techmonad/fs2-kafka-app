package com.techmonad.fs2kafka.config

import cats.effect.Sync
import fs2.kafka.vulcan.{avroDeserializer, avroSerializer, AvroSettings, SchemaRegistryClientSettings}
import fs2.kafka.{Deserializer, Serializer}
import vulcan.Codec

trait SchemaRegistry[F[_], V] {

  def serializer: Serializer.Record[F, V]
  def deserializer: Deserializer.Record[F, V]

}

object SchemaRegistry {

  implicit def apply[F[_], V](implicit ev: SchemaRegistry[F, V]): SchemaRegistry[F, V] = ev

  def impl[F[_]: Sync, V](schemaRegistryUrl: String)(implicit codec: Codec[V]): SchemaRegistry[F, V] =
    new SchemaRegistry[F, V] {

      val avroSettings: AvroSettings[F] = AvroSettings {
        SchemaRegistryClientSettings[F](schemaRegistryUrl)
      }

      override def serializer: Serializer.Record[F, V] = avroSerializer[V].using(avroSettings)

      override def deserializer: Deserializer.Record[F, V] = avroDeserializer[V].using(avroSettings)

    }

}
