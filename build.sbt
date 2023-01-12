ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Scala_TPNote"
  )

libraryDependencies += "org.openjfx" % "javafx-controls" % "14.0.1"
