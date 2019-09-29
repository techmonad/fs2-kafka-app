import sbt._

object Dependencies {

  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"

  lazy val fs2Kafka = "com.ovoenergy" %% "fs2-kafka" % "0.20.1"
  lazy val fs2KafkaVulcan = "com.ovoenergy" %% "fs2-kafka-vulcan" % "0.20.1"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val embeddedKafka =
    ("io.github.embeddedkafka" %% "embedded-kafka-schema-registry" % "5.3.0").exclude("javax.ws.rs", "javax.ws.rs-api")
  lazy val jakartaWs = "jakarta.ws.rs" % "jakarta.ws.rs-api" % "2.1.5"

  lazy val scalacheck = "org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2"
  lazy val discipline = "org.typelevel" %% "discipline-scalatest" % "1.0.0-M1"

  lazy val log4j = "org.slf4j" % "slf4j-log4j12" % "1.7.28"

  lazy val commonDependencies: Seq[ModuleID] = Seq(fs2Kafka, fs2KafkaVulcan)

  lazy val testDependencies: Seq[ModuleID] =
    Seq(scalaTest, scalacheck, discipline, embeddedKafka, jakartaWs, log4j).map(_ % Test)

}
