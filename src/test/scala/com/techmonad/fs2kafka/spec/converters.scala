package com.techmonad.fs2kafka.spec

object converters {
  val collection = scala.collection.JavaConverters

  def unsafeWrapArray[A](array: Array[A]): Seq[A] = array
}
