package com.techmonad.fs2kafka.spec

import scala.collection.immutable.ArraySeq
import scala.jdk.CollectionConverters

object Converters {
  val collection: CollectionConverters.type = scala.jdk.CollectionConverters

  def unsafeWrapArray[A](array: Array[A]): Seq[A] = ArraySeq.unsafeWrapArray(array)
}
