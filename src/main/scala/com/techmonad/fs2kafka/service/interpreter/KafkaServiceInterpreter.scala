package com.techmonad.fs2kafka
package service
package interpreter

import cats.effect.IO
import com.techmonad.fs2kafka.model.KafkaRequest

class KafkaServiceInterpreter[D] extends KafkaService[IO, D] {

  override def consume(request: KafkaRequest[D]): IO[Vector[(String, D)]] = ???

  override def produce(request: KafkaRequest[D]): IO[Vector[(String, D)]] = ???

}

object KafkaService extends KafkaServiceInterpreter
