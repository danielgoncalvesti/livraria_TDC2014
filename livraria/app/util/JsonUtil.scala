package util

import play.api.i18n.Messages
import play.api.data._
import play.api.libs.json._


//TODO VERIFICAR A NECESSIDADE DE DEIXAR ESTES DADOS GEN��RICOS
object JsonUtil {

  case class Message(name: String, value: JsValue)

  def createMsg(_type: String)(m: Message) = Json.obj("type" -> _type, m.name -> m.value)

  def createErrorMessage(error: Form[_]) = createMsg("error") {
    Message("erros",
      value = JsArray(error.errors.map {
        t => Json.obj(t.key -> Messages(t.message, (t.key +: t.args): _*))
      })
    )
  }

  def createErrorMessage(message: String) = createMsg("error") {
    Message("msg", JsString(message))
  }

  def createErrorMessage(ex:Exception) = createMsg("error") {
    Message("msg", JsString(Messages(ex.getMessage)))
  }

  def defaultErrorMessage = createMsg("error") {
    Message("msg", JsString(Messages("fatal.error")))
  }

  def createSuccessMessage(message: String) = createMsg("success") {
    Message("msg", JsString(message))
  }

}

