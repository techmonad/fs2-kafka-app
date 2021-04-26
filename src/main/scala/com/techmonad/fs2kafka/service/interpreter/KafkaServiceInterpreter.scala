package com.techmonad.fs2kafka
package service
package interpreter

import cats.effect.{IO, _}
import cats.implicits._
import com.techmonad.fs2kafka.config.KafkaEnvironment
import com.techmonad.fs2kafka.model.KafkaRequest
import fs2.kafka.{ProducerRecord, ProducerRecords, _}
import fs2.{Chunk, Stream}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class KafkaServiceInterpreter[V](kafkaEnvironment: KafkaEnvironment[IO, V]) extends KafkaService[IO, V] {

  implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

  override def consume(request: KafkaRequest[V]): IO[Vector[(String, V)]] =
    consumerStream[IO]
      .using(kafkaEnvironment.consumerSettings(request.config))
      .evalTap(_.subscribeTo(request.config.topic))
      .evalMap(IO.sleep(3.seconds).as) // sleep a bit to trigger potential race condition with _.stream
      .flatMap(_.stream)
      .map(committable => committable.record.key -> committable.record.value)
      .interruptAfter(10.seconds) // wait some time to catch potentially duplicated records
      .compile
      .toVector

  override def produce(request: KafkaRequest[V]): IO[Vector[(String, V)]] = {
    val toProduce = request.messages.zipWithIndex.map { case (value, index) => s"key-$index" -> value }
    (for {
      settings <- Stream(kafkaEnvironment.producerSettings(request.config))
      producer <- producerStream[IO].using(settings)
      records <- Stream.chunk(Chunk.seq(toProduce).map { case passthrough @ (key, value) =>
        ProducerRecords.one(ProducerRecord(request.config.topic, key, value), passthrough)
      })
      batched <- Stream
        .eval(producer.produce(records))
        .map(_.map(_.passthrough))
        .buffer(toProduce.size)
      passthrough <- Stream.eval(batched)
    } yield passthrough).compile.toVector
  }

}

object KafkaService {

  def apply[V](kafkaEnvironment: KafkaEnvironment[IO, V]): KafkaService[IO, V] =
    new KafkaServiceInterpreter[V](kafkaEnvironment)

}
