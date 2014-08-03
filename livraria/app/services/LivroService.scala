package services

import dao.models.Livro
import controllers.forms.LivroForm

trait LivroService {

  val livroService: LivroService

  trait LivroService {

    def buscaLivros(nome: String): List[Livro]
    
    def cadastra(livro: Livro)

  }

}