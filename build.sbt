name := "socobo-server"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

pipelineStages := Seq(gzip)

libraryDependencies ++= Seq(
  cache,
  javaCore,
  javaJpa
)
