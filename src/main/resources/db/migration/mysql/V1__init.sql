CREATE TABLE `conta` (
  `conta_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `perfil` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`conta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

CREATE TABLE `loja` (
  `loja_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cep` varchar(255) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  PRIMARY KEY (`loja_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

CREATE TABLE `produto` (
  `produto_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  PRIMARY KEY (`produto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `produto_lojas` (
  `produto_produto_id` bigint(20) NOT NULL,
  `lojas_loja_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ga3o6uuxfixp2w2p01wxbolae` (`lojas_loja_id`),
  KEY `FK8npgx9wq4w845fdjw4x3fo98w` (`produto_produto_id`),
  CONSTRAINT `FK8npgx9wq4w845fdjw4x3fo98w` FOREIGN KEY (`produto_produto_id`) REFERENCES `produto` (`produto_id`),
  CONSTRAINT `FKmdog3gny0voolhvbt2qah8sn0` FOREIGN KEY (`lojas_loja_id`) REFERENCES `loja` (`loja_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


