package com.techmonad.fs2kafka.model

final case class KafkaConfig(topic: String, bootstrapServers: String, schemaRegistry: String)

final case class KafkaRequest[D](config: KafkaConfig, messages: List[D])
