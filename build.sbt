
name := "elasticsearchExplain"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.10" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4",
  "com.typesafe.play" %% "play-json" % "2.5.10",
  "org.elasticsearch.client" % "rest" % "5.0.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.3"
)
