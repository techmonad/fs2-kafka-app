package com.techmonad.fs2kafka.model

final case class KafkaMessage()

final case class KafkaConfig()

final case class KafkaRequest(config: KafkaConfig, messages: List[KafkaMessage])
