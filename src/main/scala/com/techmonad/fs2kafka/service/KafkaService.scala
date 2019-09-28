package com.techmonad.fs2kafka.service

import com.techmonad.fs2kafka.model.KafkaRequest

trait KafkaService[F[_], V] {

  def consume(request: KafkaRequest[V]): F[Vector[(String, V)]]

  def produce(request: KafkaRequest[V]): F[Vector[(String, V)]]

}
