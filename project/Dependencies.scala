import sbt._

object Dependencies {

  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"

  lazy val fs2Kafka = "com.ovoenergy" %% "fs2-kafka" % "0.20.2"
  lazy val fs2KafkaVulcan = "com.ovoenergy" %% "fs2-kafka-vulcan" % "0.20.2"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"
  lazy val embeddedKafka = "io.github.embeddedkafka" %% "embedded-kafka-schema-registry" % "6.2.0"

  lazy val scalacheck = "org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2"
  lazy val discipline = "org.typelevel" %% "discipline-scalatest" % "2.1.5"

  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.5"

  lazy val commonDependencies: Seq[ModuleID] = Seq(fs2Kafka, fs2KafkaVulcan)

  lazy val testDependencies: Seq[ModuleID] = {
    Seq(scalaTest, scalacheck, discipline, embeddedKafka, logback) ++
      Seq(
        "io.github.embeddedkafka" %% "embedded-kafka" % "2.8.0",
        "org.apache.kafka" %% "kafka" % "6.2.0-ce"
      )
  }.map(_ % Test)

}
