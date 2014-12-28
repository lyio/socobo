import play.PlayImport.PlayKeys._
import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName = "socobo-server"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "mysql" % "mysql-connector-java" % "5.+",
    "org.springframework" % "spring-context" % "3.2.2.RELEASE",
    "javax.inject" % "javax.inject" % "1",
    "org.springframework.data" % "spring-data-jpa" % "1.3.2.RELEASE",
    "org.springframework" % "spring-expression" % "3.2.2.RELEASE",
    "org.hibernate" % "hibernate-entitymanager" % "3.6.10.Final",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.webjars" % "bootstrap" % "3.0.0",
    "org.webjars" % "angularjs" % "1.3.8"
  )
  val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
    playVersion := appVersion,
    libraryDependencies ++= appDependencies,
    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8")
  )
}
