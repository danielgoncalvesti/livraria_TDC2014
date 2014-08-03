package dao.config
import scala.slick.driver.MySQLDriver.backend.Database
trait DatabaseConnection {
	val db = DatabaseConnectionFactory.connection
	object DatabaseConnectionFactory {
		def connection = Database.forURL(
				url = s"jdbc:h2:mem:cerberus;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS livraria;",
				user = "sa", password = "", driver = "org.h2.Driver")
	}
}
