import play.Play.autoImport._
import play.PlayImport.PlayKeys._
import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName = "socobo-server"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "mysql" % "mysql-connector-java" % "5.1.34",
    "org.springframework" % "spring-context" % "4.1.1.RELEASE",
    "javax.inject" % "javax.inject" % "1",
    "org.springframework.data" % "spring-data-jpa" % "1.3.2.RELEASE",
    "org.springframework" % "spring-expression" % "3.2.2.RELEASE",
    "org.hibernate" % "hibernate-entitymanager" % "3.6.10.Final",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.webjars" % "bootstrap" % "3.0.0",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "org.easytesting" % "fest-assert" % "1.4"

  )
  val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
    version := appVersion,
    libraryDependencies ++= appDependencies,
    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8")
  )
}
