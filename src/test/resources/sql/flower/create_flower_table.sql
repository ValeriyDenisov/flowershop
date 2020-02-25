CREATE TABLE IF NOT EXISTS flowers
(
  id int(10) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price double NOT NULL,
  quantity_in_stock int(10) NOT NULL
);