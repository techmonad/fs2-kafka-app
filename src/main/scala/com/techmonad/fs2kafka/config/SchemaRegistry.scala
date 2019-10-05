package com.techmonad.fs2kafka.config

import cats.effect.Sync
import fs2.kafka.vulcan.{avroDeserializer, avroSerializer, AvroSettings, SchemaRegistryClientSettings}
import fs2.kafka.{Deserializer, Serializer}
import vulcan.Codec

trait SchemaRegistry[F[_], V] {

  def avroSettings(schemaRegistryUrl: String): AvroSettings[F]
  def serializer(implicit AS: AvroSettings[F]): Serializer.Record[F, V]
  def deserializer(implicit AS: AvroSettings[F]): Deserializer.Record[F, V]

}

object SchemaRegistry {

  implicit def apply[F[_], V](implicit ev: SchemaRegistry[F, V]): SchemaRegistry[F, V] = ev

  def impl[F[_]: Sync, V](implicit codec: Codec[V]): SchemaRegistry[F, V] =
    new SchemaRegistry[F, V] {

      override def avroSettings(schemaRegistryUrl: String): AvroSettings[F] =
        AvroSettings {
          SchemaRegistryClientSettings[F](schemaRegistryUrl)
        }.withAutoRegisterSchemas(true)

      override def serializer(implicit AS: AvroSettings[F]): Serializer.Record[F, V] = avroSerializer[V].using(AS)

      override def deserializer(implicit AS: AvroSettings[F]): Deserializer.Record[F, V] = avroDeserializer[V].using(AS)

    }

}
