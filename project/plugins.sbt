// Comment to get more information during initialization
logLevel := Level.Warn

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.0-M2")

// Use the Typesafe Gzip plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

// Etsy stylecheck
addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "0.4.1")
