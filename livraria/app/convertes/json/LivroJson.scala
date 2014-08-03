package convertes.json

import play.api.libs.json.Json
import dao.models.Livro

object LivroJson extends LivroJson

trait LivroJson {
  implicit val reads = Json.reads[Livro]
  implicit val writes = Json.writes[Livro]
  implicit val format = Json.format[Livro]
}