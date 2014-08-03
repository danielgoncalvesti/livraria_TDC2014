package dao.models

trait Model {
	import scala.slick.driver.JdbcDriver
	val driver: JdbcDriver
}

trait MySqlModel extends Model {
	import scala.slick.driver.MySQLDriver
	override val driver = MySQLDriver
}