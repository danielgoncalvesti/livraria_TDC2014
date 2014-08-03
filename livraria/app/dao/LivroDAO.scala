package dao

import dao.models.Model
import dao.config.DatabaseConnection
import dao.models.Livro

trait LivroDAO extends DatabaseConnection {
	this: Model =>

	val livroDAO: LivroDAORepository

	trait LivroDAORepository {
		import driver.profile.simple._

		def buscaLivrosPorNome(nome: String)(implicit s: Session): List[Livro]
		def salva(livro: Livro)(implicit s: Session)

	}
}