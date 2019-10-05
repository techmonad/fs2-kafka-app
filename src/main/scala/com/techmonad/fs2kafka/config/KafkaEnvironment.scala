package com.techmonad.fs2kafka.config

import cats.effect.Sync
import com.techmonad.fs2kafka.model.KafkaConfig
import fs2.kafka._
import fs2.kafka.vulcan.AvroSettings

trait KafkaEnvironment[F[_], V] extends KafkaProperties {

  def adminClientSettings(config: KafkaConfig): AdminClientSettings[F]
  def producerSettings(config: KafkaConfig): ProducerSettings[F, String, V]
  def consumerSettings(config: KafkaConfig): ConsumerSettings[F, String, V]

}

object KafkaEnvironment {

  implicit def apply[F[_], V](implicit ev: KafkaEnvironment[F, V]): KafkaEnvironment[F, V] = ev

  def impl[F[_]: Sync, V](schemaRegistry: SchemaRegistry[F, V]): KafkaEnvironment[F, V] =
    new KafkaEnvironment[F, V] {

      override def adminClientSettings(config: KafkaConfig): AdminClientSettings[F] =
        AdminClientSettings[F].withProperties(adminClientProperties(config))

      override def producerSettings(config: KafkaConfig): ProducerSettings[F, String, V] = {
        implicit val as: AvroSettings[F] = schemaRegistry.avroSettings(config.schemaRegistry)
        implicit val valueSerializer: Serializer.Record[F, V] = schemaRegistry.serializer
        ProducerSettings[F, String, V].withProperties(producerProperties((config)))
      }

      override def consumerSettings(config: KafkaConfig): ConsumerSettings[F, String, V] = {
        implicit val as: AvroSettings[F] = schemaRegistry.avroSettings(config.schemaRegistry)
        implicit val valueDeserializer: Deserializer.Record[F, V] = schemaRegistry.deserializer
        ConsumerSettings[F, String, V]
          .withProperties(consumerProperties((config)))
          .withRecordMetadata(_.timestamp.toString)
      }

    }

}
