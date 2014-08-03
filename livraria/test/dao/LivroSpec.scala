package dao

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import play.api.mvc.Result

class LivroSpec extends Specification {

	"LivroDAO" should {
		
		"retornar os livros de scala com preco menor que 30 reais" in {
			true must beFalse
		}
	}

}