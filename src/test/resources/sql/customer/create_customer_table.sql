CREATE TABLE IF NOT EXISTS addresses
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  code int(10),
  building VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS customers
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  secondName VARCHAR(255) NOT NULL,
  fatherName VARCHAR(255),
  addressId int(10) NOT NULL,
  phone VARCHAR(255) NOT NULL UNIQUE,
  balance double NOT NULL,
  discount smallint (3) NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT fk_customers_addresses FOREIGN KEY (addressId) REFERENCES addresses(id)