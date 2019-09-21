package com.techmonad.fs2kafka.service

import com.techmonad.fs2kafka.model.KafkaRequest

trait KafkaService[F[_], D] {

  def consume(request: KafkaRequest[D]): F[Vector[(String, D)]]

  def produce(request: KafkaRequest[D]): F[Vector[(String, D)]]

}
