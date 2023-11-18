DROP TABLE IF EXISTS delivery_contents;
CREATE TABLE delivery_contents(
    delivery_id int NOT NULL,
    product_id int NOT NULL,
    quantity int NOT NULL,
    price decimal(10,2) NOT NULL,
    PRIMARY KEY (delivery_id, product_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE,
    FOREIGN KEY (delivery_id) REFERENCES deliveries(order_id)ON DELETE CASCADE
);