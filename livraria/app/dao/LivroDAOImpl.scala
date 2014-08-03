package dao

import dao.models.Model
import dao.models.Livros
import dao.models.Livro

trait LivroDAOImpl extends LivroDAO {
	self: Model =>
		
	override val livroDAO = new LivroDAORepository {
		import driver.profile.simple._
		
		def buscaLivrosPorNome(nome: String)(implicit s: Session) = {
			???
		}
		
		def salva(livro: Livro)(implicit s: Session) = ???
	}

}