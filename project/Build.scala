import sbt._
import play.Play.autoImport._
import PlayKeys._
import sbt.Keys._

object ApplicationBuild extends Build {

    val appName         = "socobo-server"
    val appVersion      = "1.0-SNAPSHOT"


    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.google.code.gson" % "gson" % "2.2",
      "mysql" % "mysql-connector-java" % "5.+",
      "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
    )
    val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
        playVersion := appVersion,
        libraryDependencies ++= appDependencies
      )
}
