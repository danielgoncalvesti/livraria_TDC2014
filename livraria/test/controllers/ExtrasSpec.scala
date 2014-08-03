import scala.concurrent.ExecutionContext.Implicits.global
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import classycle.dependency.Result
import config.MockServices
import dao.LivroDAO
import jp.t2v.lab.play2.auth.AuthElement
import jp.t2v.lab.play2.auth.test.Helpers.AuthFakeRequest
import play.api._
import play.api.GlobalSettings
import play.api.Play.current
import play.api.data.Forms._
import play.api.mvc.Results.Status
import play.api.test._
import play.api.test.Helpers._
import services.wired.WiredLivroService
import util.AuthConfigImpl
import play.api.mvc.Action

@RunWith(classOf[JUnitRunner])
class ExtrasSpec extends Specification with MockServices {

	"WebBrowser" should {
		
		"rodar em um browser" in {
			running(TestServer(3333), HTMLUNIT) { browser =>

				browser.goTo("http://localhost:3333")
				browser.$("#title").getTexts().get(0) must equalTo("Hello Guest")

				browser.$("a").click()

				browser.url must equalTo("http://localhost:3333/Coco")
				browser.$("#title").getTexts().get(0) must equalTo("Hello Coco")

			}
		}
	}
	
	"FakeApplication" should {

		val fakeApplication = FakeApplication(
			additionalConfiguration = inMemoryDatabase("test"),
			withGlobal = Some(new GlobalSettings() {
				override def onStart(app: Application) { println("Winter is coming!") }
			}))

		"" in new WithApplication(fakeApplication) {
			true must beTrue
		}
	}

	"WithDbData" should {

		abstract class WithDbData extends WithApplication {
			//override def around[T](t: => T)(implicit evidence: (T) => Result) = ??? //TODO FAZER ALGO
		}

		"" in new WithDbData {
			true must beTrue
		}
	}

}
