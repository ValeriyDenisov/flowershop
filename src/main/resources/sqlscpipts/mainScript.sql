CREATE DATABASE flowershop;

CREATE TABLE customers
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  secondName VARCHAR(100) NOT NULL,
  fatherName VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  telephone VARCHAR(100) NOT NULL,
  balance double NOT NULL,
  discount int(3) NOT NULL
)
COLLATE='utf8_general_ci';
CREATE UNIQUE INDEX films_title_uindex ON user (title);

INSERT INTO customers (name, secondName, fatherName, address, telephone, balance, discount)
VALUES
  ("Bob", "Dilan", "John", "NEW YORK",  79993432, 32525.35, 5),
  ("Garry", "Eddison", "John", "MOSCOW",  799323432, 124214.35, 10);
