package config

import scala.slick.driver.H2Driver.profile.simple._
import scala.slick.driver.H2Driver.simple.Database
import scala.io.Source
import scala.slick.driver.H2Driver
import dao.models.Livro
import dao.Tabelas
import dao.models.Model

trait DBIntegration extends TableList {

	val dbname = "livraria"
	lazy val db = DatabaseConnectionFactory.connection

	val tables = {

		db.withSession {
			implicit session =>
				val result = session.conn.prepareStatement(s"show tables from $dbname").executeQuery()
				if (!result.next) {
					try {
						tableList.foreach {
							table =>
								println(table.ddl.createStatements.mkString("\n"))
								table.ddl.create
						}
					} catch {
						case e: Exception =>
							e.printStackTrace()
							throw e
					}
					import scala.slick.jdbc.{StaticQuery => Q }
					Source.fromFile(s"sql/livros.sql").getLines().foreach {
						line => 
							if (line.startsWith("insert")) (Q.u + line).execute
							println(line)
					}
				}
		}
	}

	object DatabaseConnectionFactory {
Class.forName("org.h2.Driver")
		val connection = Database.forURL(
				url = s"jdbc:h2:mem:$dbname;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS $dbname;",
				user = "sa", password = "", driver = "org.h2.Driver")
	}
}


trait H2Model extends Model {
	import scala.slick.driver.H2Driver
	override val driver = H2Driver
}

trait TableList extends Tabelas with H2Model {
	
	import dao.models.Livro
	import scala.slick.lifted.TableQuery
	val tableList: Seq[TableQuery[_ <: Table[_]]] = Seq(LivroT)
}
