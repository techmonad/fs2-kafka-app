import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.techmonad"
ThisBuild / organizationName := "techmonad"

lazy val root = (project in file("."))
  .settings(
    name := "fs2-kafka-app",
    libraryDependencies ++= commonDependencies ++ testDependencies,
    resolvers += "confluent" at "https://packages.confluent.io/maven/"
  )

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)

parallelExecution in Test := false

coverageExcludedPackages := Seq("").mkString(";")
