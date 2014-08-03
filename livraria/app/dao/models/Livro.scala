package dao.models

import scala.slick.lifted

case class Livro(id: Int = 0, nome: String, editora: String, preco: BigDecimal, autor: String)

trait Livros {
	self: Model =>
	import driver.profile.simple._

	class Livros(tag: Tag) extends Table[Livro](tag, "livro") {
		def * = (id, nome, editora, preco, autor) <> (Livro.tupled, Livro.unapply)
		val id = column[Int]("idlivro", O.PrimaryKey)
		val nome = column[String]("nome")
		val editora = column[String]("editora")
		val preco = column[BigDecimal]("preco")
		val autor = column[String]("idautor")
	}

	lazy val livros = new TableQuery(tag => new Livros(tag))
}



