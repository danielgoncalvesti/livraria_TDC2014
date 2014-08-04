package dao

import dao.models.LivroT
import dao.models.Model

trait Tabelas extends LivroT {
	self: Model =>
}