package com.techmonad.fs2kafka.config

import com.techmonad.fs2kafka.model.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig

trait KafkaProperties {

  final def adminClientProperties(config: KafkaConfig): Map[String, String] = defaultProperties(config)

  final def consumerProperties(config: KafkaConfig): Map[String, String] = defaultProperties(config) ++ Map {
    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
    ConsumerConfig.GROUP_ID_CONFIG -> "group"
  }

  final def producerProperties(config: KafkaConfig): Map[String, String] = defaultProperties(config)

  final private def defaultProperties(config: KafkaConfig): Map[String, String] = Map {
    CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG -> config.bootstrapServers
    CommonClientConfigs.CLIENT_ID_CONFIG -> "techmonad"
  }

}

object KafkaProperties extends KafkaProperties
