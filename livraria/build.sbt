name := "livraria"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalaBinaryVersion := "2.10"

libraryDependencies ++= Seq(
 		jdbc,
  		cache,
		"com.typesafe.slick" %% "slick" % "2.1.0-RC3",
		"com.h2database" % "h2" % "1.3.175",
		"com.typesafe" % "config" % "1.2.0",
		"com.typesafe.play" %% "play" % "2.2.2" withSources,
		"com.typesafe.play" %% "play-json" % "2.3.2" withSources,
		"jp.t2v" %% "play2-auth" % "0.11.0" withSources,
		"jp.t2v" %% "play2-auth-test" % "0.11.0" % "test" withSources,
		"com.typesafe" %% "scalalogging-slf4j" % "1.0.1" withSources,
		"com.typesafe" % "config" % "1.0.1" withSources,
		"junit" % "junit" % "4.8.1" % "test",
		"org.mockito" % "mockito-core" % "1.9.5" % "test",
		"net.liftweb" %% "lift-json" % "2.5.1",
		"org.specs2" %% "specs2" % "2.3.7" % "test"
)
     
instrumentSettings

play.Project.playScalaSettings

