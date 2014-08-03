package services

import dao.models._
import dao.LivroDAO
import controllers.forms.LivroForm
import dao.config.DatabaseConnection

trait LivroServiceImpl extends LivroService with Model {
	this: LivroDAO =>

	override val livroService = new LivroService with DatabaseConnection {
		import driver.profile.simple._

		override def buscaLivros(nome: String): List[Livro] = db.withSession {
			implicit s: Session =>
				livroDAO.buscaLivrosPorNome(nome)
		}

		override def cadastra(livro: Livro) = db.withSession {
			implicit s: Session =>
				livroDAO.salva(livro)
		}

	}

}