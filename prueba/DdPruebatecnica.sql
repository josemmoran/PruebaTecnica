CREATE DATABASE pruebatecnica
use pruebatecnica;
-- pruebatecnica.tbl_account definition

CREATE TABLE `tbl_account` (
  `number_account` varchar(255) NOT NULL,
  `account_balance` decimal(38,2) NOT NULL,
  PRIMARY KEY (`number_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- pruebatecnica.tbl_transaction definition

CREATE TABLE `tbl_transaction` (
  `id_transaction` varchar(255) NOT NULL,
  `amount_transaction` decimal(38,2) NOT NULL,
  `date_transaction` datetime(6) NOT NULL,
  `new_available_account` decimal(38,2) NOT NULL,
  `number_account` varchar(255) NOT NULL,
  `type_transaction` varchar(255) NOT NULL,
  PRIMARY KEY (`id_transaction`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- pruebatecnica.tbl_user_app definition

CREATE TABLE `tbl_user_app` (
  `user_id` binary(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK5k1crsft45ujh493m0n4ayugo` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- pruebatecnica.tbl_user_account definition

CREATE TABLE `tbl_user_account` (
  `user_id` binary(16) NOT NULL,
  `number_account` varchar(255) NOT NULL,
  KEY `FKh6jaqrdfrxdwiajaokknrcoe3` (`number_account`),
  KEY `FK433ao462e8lhv91uqyvcww952` (`user_id`),
  CONSTRAINT `FK433ao462e8lhv91uqyvcww952` FOREIGN KEY (`user_id`) REFERENCES `tbl_user_app` (`user_id`),
  CONSTRAINT `FKh6jaqrdfrxdwiajaokknrcoe3` FOREIGN KEY (`number_account`) REFERENCES `tbl_account` (`number_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;