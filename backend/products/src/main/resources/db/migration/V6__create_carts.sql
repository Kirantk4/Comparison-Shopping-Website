DROP TABLE IF EXISTS carts;
CREATE TABLE carts(
    cart_id int PRIMARY KEY AUTO_INCREMENT,
    customer_id int NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)ON DELETE CASCADE);