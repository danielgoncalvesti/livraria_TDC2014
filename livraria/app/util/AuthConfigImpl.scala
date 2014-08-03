package util

import jp.t2v.lab.play2.auth.AuthConfig
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect._
import play.api.libs.ws.WS
import scala.util.control.NonFatal
import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import scala.Some
import play.api.mvc.SimpleResult
import dao.models.{User => U}
import org.joda.time.DateTime


/**
 * Created by gustavo on 3/3/14.
 */
trait AuthConfigImpl extends AuthConfig {


  /**
   * A type that is used to identify a user.
   */
  type Id = String

  /**
   * A type that represents a user in your application.
   */
  type User = U

  /**
   * A type that is defined by every action for authorization.
   */
  //type Authority = models.auth.Permission

  type Authority = User => Future[Boolean]

  /**
   * A `ClassManifest` is used to retrieve an id from the Cache API.
   */
  val idTag: ClassTag[Id] = classTag[Id]

  /**
   * The session timeout in seconds
   */
  val sessionTimeoutInSeconds: Int = 0


  protected def NoAuth = (user: User) => Future.successful(true)

  /**
   * A function that returns a `User` object from an `Id`.
   */
  def resolveUser(id: Id)(implicit ctx: ExecutionContext): Future[Option[User]] = {
    WS.url(s"http://www.livraria.com/usuario/v1/sessao/$id").get.map {
      response =>
        if (response.status == 200) {
          try {
            val user = U.fromJson(response.body)
            Some(user)
          } catch {
            case NonFatal(e) =>
              None
          }
        } else {
          None
        }
    }
  }

  /**
   * Where to redirect the user after a successful login.
   */
  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[SimpleResult] = {
    val uri = request.getQueryString("forward").getOrElse("/")
    Future.successful(Redirect(uri).withNewSession)
  }

  /**
   * Where to redirect the user after logging out
   */
  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext): Future[SimpleResult] = Future.successful(Redirect(s"http://www.livraria.com/logout"))

  /**
   * If the user is not logged in and tries to access a protected resource then redirect them as follows:
   */
  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext): Future[SimpleResult] = {

    val url = s"http://www.livraria.com/login"

    val forward = request.uri match {
      case "/login" => "/"
      case _ => request.uri
    }

    val uuid = request.cookies.get("") match {
      case Some(c) => c
      case None => ""
    }

    Future.successful(Redirect(s"http://www.livraria.com/l/home"))
  }

  /**
   * If authorization failed (usually incorrect password) redirect the user as follows:
   */
  def authorizationFailed(request: RequestHeader)(implicit ctx: ExecutionContext): Future[SimpleResult] = Future.successful(Forbidden("no permission"))

  /**
   * A function that determines what `Authority` a user has.
   */
  def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext): Future[Boolean] = authority(user)

  /**
   * Whether use the secure option or not use it in the cookie.
   * However default is false, I strongly recommend using true in a production.
   */
  //  override lazy val cookieSecureOption: Boolean = current.configuration.getBoolean("auth.cookie.secure") getOrElse (true)

  override lazy val cookieName: String = "Livraria"

  def validatePath(user: User)(implicit global: ExecutionContext) = {
    Future.successful(true)
  }
}
