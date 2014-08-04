package dao

import dao.models.Model
import dao.models.Livro
import dao.models.LivroT

trait LivroDAOImpl extends LivroDAO with LivroT{
	self: Model =>
		
	override val livroDAO = new LivroDAORepository {
		import driver.profile.simple._
		
		def buscaLivrosPorNome(nome: String)(implicit s: Session) = {
			LivroT.filter(_.nome.toLowerCase like s"%$nome%".toLowerCase).list
		}
		
		def salva(livro: Livro)(implicit s: Session) = ???
		
		def buscaLivrosDeValorMenorQue(preco: BigDecimal)(implicit s: Session): List[Livro] = {
			LivroT.filter(_.preco <= preco).list
		}
	}

}