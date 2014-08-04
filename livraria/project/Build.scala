import sbt._
import Keys._

import sbt.Keys._
import play.Project._
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.SbtNativePackager._
import scala.Some
import scala.sys.process.Process
import java.io.File
import com.typesafe.config._

object ApplicationBuild extends Build {

	val appName = "livraria"
	val appVersion = "1.0-SNAPSHOT"

	val dependencies = Seq(
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

	val main = play.Project(appName, appVersion, dependencies).settings(
		//Filtrando execucao de classes de teste de por nome de classe
		testOptions in Test := Seq(Tests.Filter(_ endsWith "Spec")),
		//Configuraçoes de setup de execucao dos testes
		testOptions in Test += Tests.Setup(() => println("Iniciando os testes do sistema [Livraria]")),
		//testOptions in Test += Tests.Cleanup( () => println("Limpando")),
		//Desabilitando o log na execução dos testes
		logBuffered in Test := false,
		//Desabilitando execucao paralela dos testes
		parallelExecution in Test := false //Adicionando um diretório com recursos adicionais
		//unmanagedResourceDirectories in Test <+= baseDirectory(_ / "conf"),
		)

}
