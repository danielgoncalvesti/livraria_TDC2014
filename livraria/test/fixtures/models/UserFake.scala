package fixtures.models

import fixtures.Fixture
import dao.models.User

object UserFake extends Fixture[User] {
	
	override val fixtures = Map("user" -> User("51c99e50-1a6e-11e4-8c21-0800200c9a66", "foto" , "teste@teste.com.br"))

}