CREATE TABLE IF NOT EXISTS addresses
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  code int(10),
  building int(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS customers
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  secondName VARCHAR(255) NOT NULL,
  fatherName VARCHAR(100),
  addressId int(10) NOT NULL,
  phone VARCHAR(255) NOT NULL UNIQUE,
  balance double NOT NULL,
  discount smallint (3) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  price double NOT NULL,
  openDate date NOT NULL,
  closeDate date,
  isActive bit NOT NULL,
  customerId int(10) NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT fk_customers_addresses FOREIGN KEY (addressId) REFERENCES addresses(id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customers FOREIGN KEY (customerId) REFERENCES customers(id);