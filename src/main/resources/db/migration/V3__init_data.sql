INSERT INTO ADDRESSES (id, street, city, code, building) VALUES (1, 'street_1', 'city_1', 111, 1);
INSERT INTO ADDRESSES (id, street, city, code, building) VALUES (2, 'street_2', 'city_2', 222, 2);

INSERT INTO FLOWERS (id, name, price, quantity_in_stock)
VALUES (1, 'name_1', 100, 1000);
INSERT INTO FLOWERS (id, name, price, quantity_in_stock)
VALUES (2, 'name_2', 50, 2000);
INSERT INTO FLOWERS (id, name, price, quantity_in_stock)
VALUES (3, 'name_3', 20, 3000);
INSERT INTO FLOWERS (id, name, price, quantity_in_stock)
VALUES (4, 'name_4', 5, 4000);


INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (1, 'name_1', 'secondName_1', 'fatherName_1', 1, 'phone_1', 1000000, 1, 'email_1');
INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (2, 'name_2', 'secondName_2', 'fatherName_2', 2, 'phone_2', 500000, 1, 'email_2');

INSERT INTO users (id, login, password, role) VALUES (1, 'email_1', '$2a$10$NHeO7TL3WgpQJ32tFDJZbekcxhhy8ALlDT/4Gu2f5dlxZ.prZSnd.', 'USER');
INSERT INTO users (id, login, password, role) VALUES (2, 'email_2', '$2a$10$XTNYzSInE08TqRM8alrkGeUfHzJtap0TGTUiUJqOOhhNgz80Gmpk2', 'USER');
INSERT INTO users(id, login, password, role) VALUES (3, 'admin', '$2a$10$5aWSSmWrpR1FeYpZRm/Q9uM/y92UrviB69XouLQy6/7.eA32MWTzm', 'ADMIN');