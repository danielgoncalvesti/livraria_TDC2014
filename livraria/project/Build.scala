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

	val main = play.Project(appName, appVersion).settings(
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
