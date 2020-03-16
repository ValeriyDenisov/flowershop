INSERT INTO ADDRESSES (street, city, code, building) VALUES ('street_1', 'city_1', 111, 1);
INSERT INTO ADDRESSES (street, city, code, building) VALUES ('street_2', 'city_2', 222, 2);

INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (1, 'name_1', 'secondName_1', 'fatherName_1', 1, '1112223344', 111.11, 1, 'login@111');
INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (2, 'name_2', 'secondName_2', 'fatherName_2', 2, '4441112233', 111.11, 1, 'login@222');

INSERT INTO USERS (id, login, password, role)
VALUES (1, 'login@111', '123', 'USER');
INSERT INTO USERS (id, login, password, role)
VALUES (2, 'login@222', '123', 'USER');

INSERT INTO ORDERS (id, price, open_date, close_date, is_active, customer_id)
VALUES (1, 111.11, '2020-01-21', null , 1, 1);
INSERT INTO ORDERS (id, price, open_date, close_date, is_active, customer_id)
VALUES (2, 222.22, '2020-01-22', '2020-01-02' , 0, 2);
INSERT INTO ORDERS (id, price, open_date, close_date, is_active, customer_id)
VALUES (3, 333.33, '2020-01-22', '2020-01-02' , 0, 2);
INSERT INTO ORDERS (id, price, open_date, close_date, is_active, customer_id)
VALUES (4, 444.44, '2020-01-22', '2020-01-02' , 0, 2);
INSERT INTO ORDERS (id, price, open_date, close_date, is_active, customer_id)
VALUES (5, 555.55, '2020-01-22', '2020-01-02' , 0, 1);