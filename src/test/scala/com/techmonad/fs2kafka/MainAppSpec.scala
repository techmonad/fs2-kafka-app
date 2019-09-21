package com.techmonad.fs2kafka

import com.techmonad.fs2kafka.app.Hello
import org.scalatest._

class MainAppSpec extends FlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    Hello.greeting shouldEqual "hello"
  }
}
