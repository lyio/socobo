name := "socobo-server"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

// The Typesafe repository
resolvers ++= Seq ("Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases")

pipelineStages := Seq(gzip)

libraryDependencies ++= Seq(
  cache,
  javaCore,
  javaJpa
)

com.etsy.sbt.Checkstyle.checkstyleSettings
