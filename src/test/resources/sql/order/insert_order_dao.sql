INSERT INTO ADDRESSES (id, street, city, code, building) VALUES (1, 'street_1', 'city_1', 111, 1);
INSERT INTO ADDRESSES (id, street, city, code, building) VALUES (2, 'street_2', 'city_2', 222, 2);

INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (1, 'name_1', 'secondName_1', 'fatherName_1', 1, 'phone_1', 111.11, 1, 'email_1');
INSERT INTO CUSTOMERS (id, name, second_name, father_name, address_id, phone, balance, discount, email)
VALUES (2, 'name_2', 'secondName_2', 'fatherName_2', 2, 'phone_2', 111.11, 1, 'email_2');

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