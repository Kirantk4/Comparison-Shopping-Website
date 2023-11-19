DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
    customer_id int PRIMARY KEY AUTO_INCREMENT,
    customer_name varchar(255) NOT NULL,
    customer_email varchar(255) NOT NULL UNIQUE,
    customer_address varchar(255) NOT NULL,
    customer_password varchar(255) NOT NULL
    );