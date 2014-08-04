package dao.models


case class Livro(id: Int = 0, nome: String, editora: String, preco: BigDecimal, autor: String)

trait LivroT {
	self: Model =>
	import driver.profile.simple._

	class LivroT(tag: Tag) extends Table[Livro](tag, Some("livraria"), "livro") {
		def * = (id, nome, editora, preco, autor) <> (Livro.tupled, Livro.unapply)
		val id = column[Int]("idlivro", O.PrimaryKey)
		val nome = column[String]("nome")
		val editora = column[String]("editora")
		val preco = column[BigDecimal]("preco")
		val autor = column[String]("autor")
	}

	lazy val LivroT = new TableQuery(tag => new LivroT(tag))
}



