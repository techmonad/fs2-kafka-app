package com.techmonad.fs2kafka.config

import cats.effect.Sync
import fs2.kafka._

trait KafkaEnvironment[F[_], V] {

  def adminClientSettings: AdminClientSettings[F]
  def producerSettings: ProducerSettings[F, String, V]
  def consumerSettings: ConsumerSettings[F, String, V]

}

object KafkaEnvironment {

  implicit def apply[F[_], V](implicit ev: KafkaEnvironment[F, V]): KafkaEnvironment[F, V] = ev

  def impl[F[_]: Sync, V](properties: KafkaProperties, schemaRegistry: SchemaRegistry[F, V]): KafkaEnvironment[F, V] =
    new KafkaEnvironment[F, V] {

      override def adminClientSettings: AdminClientSettings[F] =
        AdminClientSettings[F].withProperties(properties.adminClientProperties)

      override def producerSettings: ProducerSettings[F, String, V] = {
        implicit val valueSerializer: Serializer.Record[F, V] = schemaRegistry.serializer
        ProducerSettings[F, String, V].withProperties(properties.producerProperties)
      }

      override def consumerSettings: ConsumerSettings[F, String, V] = {
        implicit val valueDeserializer: Deserializer.Record[F, V] = schemaRegistry.deserializer
        ConsumerSettings[F, String, V]
          .withProperties(properties.consumerProperties)
          .withRecordMetadata(_.timestamp.toString)
      }

    }

}
