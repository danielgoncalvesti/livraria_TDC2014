import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import controllers.LivroController
import config.MockServices
import dao.models.Livro
import play.api.test._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import fixtures.models.LivroFake
import play.api.libs.json.Json
import play.api.cache.Cache
import org.specs2.mutable.Specification
import jp.t2v.lab.play2.auth.test.Helpers.AuthFakeRequest

@RunWith(classOf[JUnitRunner])
class LivroControllerSpec extends Specification with MockServices {

	//step(Play.start(new FakeAppContext))

	"LivroController" should {

		"listar os livros com o titulo/autor de nome 'php' e retornar mensagem [Nenhum livro encontrado.]" in new WithApplication {

			object controller extends LivroController with MockLivroService {
				livroService.buscaLivros("php") returns List.empty[Livro]
			}
			val result = controller.pesquisar("php")(FakeRequest(GET, "/pesquisar"))

			contentAsString(result) must contain("Nenhum livro encontrado.")
			status(result) must be equalTo OK
		}

		"listar os livros com o titulo/autor de nome 'scala' e retornar Ok" in new WithApplication {

			object controller extends LivroController with MockLivroService {
				livroService.buscaLivros("scala") returns LivroFake.gimmeListByAlias("scala")
			}
			val result = controller.pesquisar("scala")(FakeRequest(GET, "/pesquisar"))
			status(result) must be equalTo OK
		}

		"buscar no cache os ultimos livros pesquisados que nao existem e retornar json com os dados vazios" in new WithApplication {

			object controller extends LivroController with MockLivroService {
				livroService.buscaLivros("scala") returns List.empty[Livro]
			}
			val result = controller.ultimaPesquisaJson()(FakeRequest(GET, "/ultimaPesquisaJson"))

			contentAsString(result) must be equalTo ("{\"livros\":null}")
		}

		"buscar no cache os ultimos livros pesquisados e retornar json com os livros" in new WithApplication {
			import play.api.Play.current
			object controller extends LivroController with MockLivroService

			Cache.set("ultimos-livros-pesquisados1", LivroFake.toList)
			Cache.getAs[Livro]("ultimos-livros-pesquisados1") must not be None

			val result = controller.ultimaPesquisaJson()(FakeRequest(GET, "/ultimaPesquisaJson"))
			contentAsString(result) must be equalTo ("{\"livros\":bla}")
		}

		"tentar cadastrar um livro com usuario nao logado e retornar SeeOther" in new WithApplication {

			object controller extends LivroController with MockLivroService

			val result = controller.cadastrar()(FakeRequest(POST, "/cadastrar").withFormUrlEncodedBody(
				("nome", "Livro Teste"), ("editora", "Fundamentos"), ("autor", "Blah"), ("preco", "10.00")))

			status(result) must be equalTo SEE_OTHER
		}

		"tentar cadastrar um livro sem enviar as informacoes obrigatorias do form e retornar BadRequest" in new WithApplication {

			object controller extends LivroController with MockLivroService with MockAuthConfigImpl

			val result = controller.cadastrar()(FakeRequest(POST, "/cadastrar").withFormUrlEncodedBody(
				("nome", "Livro Teste"), ("editora", "Fundamentos"), ("autor", "Blah")).withLoggedIn(config)(uuid))

			status(result) must be equalTo BAD_REQUEST
		}

		"cadastrar um livro com usuario autenticado sem enviar as informacoes do form e retornar Ok" in new WithApplication {

			object controller extends LivroController with MockLivroService with MockAuthConfigImpl

			val result = controller.cadastrar()(FakeRequest(POST, "/cadastrar").withFormUrlEncodedBody(
				("nome", "Livro Teste"), ("editora", "Fundamentos"), ("autor", "Blah"), ("preco", "10.00")).withLoggedIn(config)(uuid))

			status(result) must be equalTo OK
		}

	}

	//step(Play.stop)
}
