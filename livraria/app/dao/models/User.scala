package dao.models

import org.joda.time.{DateTime, LocalDate}

import scala.Some
import play.api.libs.json.Json
import scala.Some

case class User(val uuid: String, val avatar: String, val email: String, val lastLogin: Option[String] = None)

object User {
	def fromJson(json: String) = ???
}

case class Permission(cliente: String, perfis: List[Profile])

case class Access(modulo: String, eventos: List[String])

case class Profile(id: Int, personagem: String, acessos: List[Access])