create TABLE IF NOT EXISTS users
(
    id int(10) PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL unique,
    password VARCHAR(255) NOT NULL
);