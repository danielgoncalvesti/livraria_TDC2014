package controllers

import play.api._
import play.api.mvc._
import services.wired.WiredLivroService
import services._
import play.api.mvc.Results.Status
import play.api.cache.Cache
import play.api.Play.current
import dao.models.Livro
import convertes.json.LivroJson
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext
import play.api.data.Form
import play.api.data.Forms._
import controllers.forms.LivroForm
import jp.t2v.lab.play2.auth.AuthElement
import dao.LivroDAO
import util.AuthConfigImpl
import scala.concurrent.Future
import ExecutionContext.Implicits.global

object LivroController extends LivroController with WiredLivroService

trait LivroController extends Controller with AuthElement with AuthConfigImpl {
	this: LivroService =>

	def index = Action {
		Ok(views.html.index("Livraria está no ar!"))
	}
		
	def home(nome: String) = Action {
		Ok(views.html.livro.home(s"Bem Vindo a Livraria, $nome"))
	}
	
	def pesquisar(nome: String) = Action {
		val livros = livroService.buscaLivros(nome)
		if (livros.nonEmpty) {
			Cache.set("ultimos-livros-pesquisados", livros, 60)
			Ok(views.html.livro.pesquisa(s"Resultado da pesquisa para a palavra: $nome", livros))
		} else {
			Ok(views.html.livro.home("Nenhum livro encontrado."))
		}
	}

	def ultimaPesquisaJson = Action { implicit request =>
		implicit val writes = LivroJson.writes
		val livros = {
			Cache.getAs[Livro]("ultimos-livros-pesquisados")
		}
		Ok(Json.obj("livros" -> livros.map(livro => Json.toJson(livro))))
	}

	val livroForm = Form(
		mapping(
			"nome" -> nonEmptyText,
			"editora" -> nonEmptyText,
			"autor" -> nonEmptyText,
			"preco" -> bigDecimal)(LivroForm.apply)(LivroForm.unapply))

	def cadastrar = AsyncStack(AuthorityKey -> NoAuth) {
		implicit request =>
			Future {
				livroForm.bindFromRequest.fold(
					errors => { BadRequest(views.html.index("As informacoes enviadas estão incompletas.")) },
					form => {
						livroService.cadastra(form.asLivro)
						Ok(views.html.index("Livro cadastrado com sucesso."))
					})
			}
	}

}