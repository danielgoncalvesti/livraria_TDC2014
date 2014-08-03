package services.wired

import dao._
import services._
import dao.models.MySqlModel

trait WiredLivroService extends LivroServiceImpl with LivroDAOImpl with MySqlModel