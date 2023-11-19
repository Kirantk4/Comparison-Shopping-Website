DROP TABLE IF EXISTS current_prices;
CREATE TABLE current_prices(
    product_id int NOT NULL,
    product_price decimal(10,2) NOT NULL,
    super_market varchar(255) NOT NULL,
    PRIMARY KEY (product_id, super_market),
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE);