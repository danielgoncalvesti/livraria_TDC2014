name := "livraria"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalaBinaryVersion := "2.10"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings

instrumentSettings
