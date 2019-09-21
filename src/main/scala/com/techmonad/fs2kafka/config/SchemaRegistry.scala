package com.techmonad.fs2kafka.config

trait SchemaRegistry[D] {}

object SchemaRegistry {

  implicit def apply[D](implicit ev: SchemaRegistry[D]): SchemaRegistry[D] = ev

}
