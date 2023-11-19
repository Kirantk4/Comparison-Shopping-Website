DROP TABLE IF EXISTS deliveries;
CREATE TABLE deliveries(
    order_id int PRIMARY KEY AUTO_INCREMENT,
    customer_id int NOT NULL,
    ordered_date date NOT NULL,
    delivery_date date NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)ON DELETE CASCADE);