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

	"LivroIntegration" should {

		"responder a página principal através de um browser e retornar o conteúdo 'Livraria está no ar!' " in {
			running(TestServer(8080), HTMLUNIT) { browser =>
				browser.goTo("http://localhost:8080/")
				browser.pageSource must contain("Livraria está no ar!")
			}
		}

	}

	"LivroTemplate" should {

		"renderizar o template 'pesquisa' com os parametros desejados e retornar o conteúdo correspondente" in {
			val html = views.html.livro.pesquisa("Resultado da Pesquisa", LivroFake.toList)

			contentType(html) must equalTo("text/html")
			contentAsString(html) must contain("Resultado da Pesquisa")
			contentAsString(html) must contain("Scala for the Impatient")
		}

	}

	//step(Play.start(new FakeAppContext))

	"LivroRoutes" should {

		"responder para o template 'index' a chamada da rota principal" in new WithApplication {
			val result = route(FakeRequest(GET, "/")).get

			status(result) must equalTo(OK)
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Livraria está no ar!")
		}

		"ao acessar rota inexistente e retornar None(BadRequest)" in new WithApplication {
			route(FakeRequest(GET, "/games")) must beNone
		}

	}

	"LivroController" should {

		"retornar no template 'home' mensagem 'Bem Vindo a Livraria, TDC 2014'" in {
			val result = controllers.LivroController.home("TDC 2014")(FakeRequest())

			status(result) must equalTo(OK)
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Bem Vindo a Livraria, TDC 2014")
		}

		"listar os livros com o titulo/autor de nome 'php' e retornar mensagem [Nenhum livro encontrado.]" in new WithApplication {

			object controller extends LivroController with MockLivroService {
				livroService.buscaLivros("php") returns List.empty[Livro]
			}
			val result = controller.pesquisar("php")(FakeRequest())

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
			val result = controller.ultimaPesquisaJson()(FakeRequest())

			contentAsString(result) must be equalTo ("""{"livros":null}""")
		}

		"buscar no cache os ultimos livros pesquisados e retornar json com os livros" in new WithApplication {
			import play.api.Play.current
			object controller extends LivroController with MockLivroService

			val livro = List(Livro(1, "Scala for the Impatient", "Addison-Wesley Professional", BigDecimal(30.0), "Cay S. Horstmann"))
			Cache.set("ultimos-livros-pesquisados1", livro)

			val result = controller.ultimaPesquisaJson()(FakeRequest())
			contentAsString(result) must be equalTo ("""{"livros":[{"id":1,"nome":"Scala for the Impatient","editora":"Addison-Wesley Professional","preco":30.0,"autor":"Cay S. Horstmann"}]}""")
		}

		"tentar cadastrar um livro com usuario nao logado e retornar SeeOther" in new WithApplication {

			object controller extends LivroController with MockLivroService

			val result = controller.cadastrar()(FakeRequest(POST, "/cadastrar").withFormUrlEncodedBody(
				("nome", "Livro Teste"), ("editora", "Fundamentos"), ("autor", "Blah"), ("preco", "10.00")))

			status(result) must be equalTo SEE_OTHER
		}

		"tentar cadastrar um livro sem enviar as informacoes obrigatorias do form e retornar BadRequest" in new WithApplication {

			object controller extends LivroController with MockLivroService with MockAuthConfigImpl

			val result = controller.cadastrar()(FakeRequest().withFormUrlEncodedBody(
				("nome", "Livro Teste"), ("editora", "Fundamentos"), ("autor", "Blah")).withLoggedIn(config)(uuid))

			status(result) must be equalTo BAD_REQUEST
		}

		"cadastrar um livro com usuario autenticado sem enviar as informacoes do form e retornar Ok" in
			new WithApplication(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

				object controller extends LivroController with MockLivroService with MockAuthConfigImpl

				val result = controller.cadastrar()(FakeRequest().withFormUrlEncodedBody(
					("nome", "Livro de Scala"), ("editora", "Fundamentos"), ("autor", "Blah"), ("preco", "10.00")).withLoggedIn(config)(uuid))

				status(result) must be equalTo OK
			}

	}

	//step(Play.stop)
}
