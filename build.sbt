import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.techmonad"
ThisBuild / organizationName := "techmonad"

lazy val root = (project in file("."))
  .settings(
    name := "fs2-kafka-app",
    parallelExecution in Test := false,
    logBuffered in Test := false,
    fork in Test := true,
    scalacOptions += "-deprecation",
    scalafmtOnCompile := true,
    coverageExcludedPackages := Seq("").mkString(";"),
    libraryDependencies ++= commonDependencies ++ testDependencies
  )
  .settings(
    resolvers ++= Seq(
      "jitpack".at("https://jitpack.io"),
      "confluent".at("https://packages.confluent.io/maven/"),
      Resolver.sonatypeRepo("snapshots")
    )
  )

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)
