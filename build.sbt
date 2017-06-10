
name := "elasticsearchExplain"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.elasticsearch.client" % "rest" % "5.0.1",
  "com.typesafe.play" %% "play-json" % "2.5.10",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.mockito" % "mockito-core" % "2.7.22" % Test
)

enablePlugins(JavaAppPackaging)

enablePlugins(DockerPlugin)
