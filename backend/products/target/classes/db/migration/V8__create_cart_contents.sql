DROP TABLE IF EXISTS cart_contents;
CREATE TABLE cart_contents(
    product_id int NOT NULL,
    cart_id int NOT NULL,
    quantity int not null,
    PRIMARY KEY (product_id, cart_id),
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id)ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE
);