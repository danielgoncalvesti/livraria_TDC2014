package controllers.forms

import dao.models.Livro

case class LivroForm(nome: String, editora:String, autor:String, preco: BigDecimal) {
	
	val asLivro = Livro(nome = nome, editora = editora, preco = preco, autor = autor)
}