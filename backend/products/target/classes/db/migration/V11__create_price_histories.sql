DROP TABLE IF EXISTS price_histories;
CREATE TABLE price_histories (
    product_id int NOT NULL,
    super_market varchar(255) NOT NULL,
    price_date date NOT NULL,
    product_price decimal(10,2) NOT NULL,
    PRIMARY KEY (product_id, price_date, super_market),
    FOREIGN KEY (product_id, super_market) REFERENCES current_prices(product_id, super_market) ON DELETE CASCADE
);