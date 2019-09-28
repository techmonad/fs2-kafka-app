package com.techmonad.fs2kafka.config

import com.techmonad.fs2kafka.model.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig

trait KafkaProperties {

  val config: KafkaConfig

  final def adminClientProperties: Map[String, String] = defaultProperties

  final def consumerProperties: Map[String, String] = defaultProperties ++ Map {
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
    ConsumerConfig.GROUP_ID_CONFIG -> "group"
  }

  final def producerProperties: Map[String, String] = defaultProperties

  final private def defaultProperties: Map[String, String] = Map {
    CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG -> config.bootstrapServers
    CommonClientConfigs.CLIENT_ID_CONFIG -> "techmonad-fs2-kafka"
  }

}

object KafkaProperties {

  def apply(KC: KafkaConfig): KafkaProperties = new KafkaProperties {
    override val config: KafkaConfig = KC
  }

}
