import sbt._

object Dependencies {

  lazy val fs2Kafka = "com.ovoenergy" %% "fs2-kafka" % "0.20.1"
  lazy val fs2KafkaVulcan = "com.ovoenergy" %% "fs2-kafka-vulcan" % "0.20.1"

  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"

  lazy val commonDependencies = Seq(fs2Kafka, fs2KafkaVulcan)

  lazy val testDependencies = Seq(scalaTest).map(_ % Test)

}
