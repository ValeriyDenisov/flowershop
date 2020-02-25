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
  second_name VARCHAR(255) NOT NULL,
  father_name VARCHAR(100),
  address_id int(10) NOT NULL,
  phone VARCHAR(255) NOT NULL UNIQUE,
  balance double NOT NULL,
  discount smallint (3) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS orders
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  price double NOT NULL,
  open_date date NOT NULL,
  close_date date,
  is_active bit NOT NULL,
  customer_id int(10) NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT fk_customers_addresses FOREIGN KEY (address_id) REFERENCES addresses(id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_customers FOREIGN KEY (customer_id) REFERENCES customers(id);