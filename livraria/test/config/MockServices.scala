

package config

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import services._
import util.AuthConfigImpl
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import fixtures.models.UserFake

trait MockServices extends Mockito {

	lazy val userFake = UserFake.one
	lazy val uuid = userFake.uuid

	object config extends AuthConfigImpl

	trait MockAuthConfigImpl extends  Mockito with AuthConfigImpl {
		override def resolveUser(id: Id)(implicit ctx: ExecutionContext): Future[Option[User]] = Future(Some(userFake))
	}

	trait MockLivroService extends LivroService {
		override val livroService = mock[LivroService]
	}

}