import play.ebean.sbt.PlayEbean
import play.sbt.Play.autoImport._
import play.sbt.PlayJava
import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName = "socobo-server"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "javax.inject" % "javax.inject" % "1",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.postgresql" % "postgresql" % "9.4.1211"
  )
  val main = Project(appName, file(".")).enablePlugins(PlayJava, PlayEbean).settings(
    libraryDependencies ++= appDependencies,
    libraryDependencies += evolutions
  )
}
