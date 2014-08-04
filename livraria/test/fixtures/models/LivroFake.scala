package fixtures.models

import dao.models.Livro
import fixtures.Fixture

object LivroFake extends Fixture[Livro] {
	
	override val fixtures = Map(
			"Scala for the Impatient" -> Livro(1, "Scala for the Impatient", "Addison-Wesley Professional", BigDecimal(30.0), "Cay S. Horstmann"),
			"Programming in Scala" -> Livro(2, "Programming in Scala: A Comprehensive Step-by-Step Guide", "Artima Pres", BigDecimal(40.0), "Martin Odersky, Lex Spoon e Bill Venners"),
			"Scala for Java Developers" -> Livro(3, "Thomas Alexandre", "Packt Publishing", BigDecimal(20.0), "Thomas Alexandre"),
			"Programming Scala" -> Livro(4, "Programming Scala: Scalability = Functional Programming + Objects (Animal Guide)", "O'Reilly Media", BigDecimal(40.0), "Dean Wampler e Alex Payne"),
			"Functional Programming Patterns in Scala and Clojure" -> Livro(5, "Functional Programming Patterns in Scala and Clojure: Write Lean Programs for the JVM ", "Pragmatic Bookshelf", BigDecimal(50.0), "Michael Bevilacqua-Linn"),
			"Scala Cookbook" -> Livro(6, "Scala Cookbook: Recipes for Object-Oriented and Functional Programming", "O'Reilly Media", BigDecimal(30.0), "Alvin Alexander"),
			"Beginning Scala " -> Livro(7, "Beginning Scala (Expert's Voice in Open Source) ", "Apress", BigDecimal(10.0), "David Pollak"))

}