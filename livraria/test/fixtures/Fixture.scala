package fixtures

import scala.util.Random
import scala.util.Try

trait Fixture[T] {

	protected val fixtures: Map[String, T]

	def one = fixtures.values.toList(new Random().nextInt(fixtures.values.size))

	def gimme(amount: Int) = if (amount < fixtures.size ) fixtures.values.toList.take(amount) else toList

	def gimmeByAlias(alias: String) = Try(fixtures.apply(alias))
		.getOrElse(throw new FixtureException("Não existe fixture cadastrada para o alias informado."))
	
	def gimmeListByAlias(alias: String) = Try {
		fixtures.filter(p=> p._1.toLowerCase().contains(alias.toLowerCase())).valuesIterator.toList
	}.getOrElse(throw new FixtureException("Não existem fixtures cadastradas para o alias informado"))

	def toList = fixtures.valuesIterator.toList

}

class FixtureException(msg: String) extends Exception(msg)