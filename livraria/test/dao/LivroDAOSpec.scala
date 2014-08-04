package dao

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import play.api.mvc.Result
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import _root_.config.H2Model
import _root_.config.DBIntegration

@RunWith(classOf[JUnitRunner])
class LivroDAOSpec extends Specification with DBIntegration  {

	"LivroDAO" should {

		val dao = new LivroDAOImpl with H2Model

		"trazer os livros que possuem o nome informado" in {
			db.withSession {
				implicit session =>
					val livros = dao.livroDAO.buscaLivrosPorNome("scala")
					livros.size must be equalTo 6
			}
		}
		
		"retornar os livros de scala com preco menor que 30 reais" in {
			db.withSession {
				implicit session =>
					val livros = dao.livroDAO.buscaLivrosDeValorMenorQue(BigDecimal("30.0"))
					livros.size must be equalTo 4
			}
		}
	}

}