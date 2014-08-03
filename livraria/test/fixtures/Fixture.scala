package fixtures

import scala.util.Random

trait Fixture[T] {

	protected val fixtures: Map[String, T]

	def one = fixtures.values.toList(new Random().nextInt(fixtures.values.size))

	def gimme(amount: Int) = fixtures.values.toList.take(amount)

	def gimmeByAlias(alias: String) = fixtures.apply(alias)
	
	def gimmeListByAlias(alias: String) = fixtures.filter(p=> p._1.toLowerCase().contains(alias.toLowerCase())).valuesIterator.toList

	def toList = fixtures.valuesIterator.toList

}