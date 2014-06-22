import sbt._
import play.Play.autoImport._
import PlayKeys._
import sbt.Keys._

object ApplicationBuild extends Build {

    val appName         = "socobo-server"
    val appVersion      = "1.0-SNAPSHOT"


    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.google.code.gson" % "gson" % "2.2"
    )
    val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
        playVersion := appVersion,
        libraryDependencies ++= appDependencies
      )
}
