package com.techmonad.fs2kafka
package service
package interpreter

import cats.effect.IO
import com.techmonad.fs2kafka.model.KafkaRequest

class KafkaServiceInterpreter[V] extends KafkaService[IO, V] {

  override def consume(request: KafkaRequest[V]): IO[Vector[(String, V)]] = ???

  override def produce(request: KafkaRequest[V]): IO[Vector[(String, V)]] = ???

}

object KafkaService extends KafkaServiceInterpreter
