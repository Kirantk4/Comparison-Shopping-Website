DROP TABLE IF EXISTS products;
CREATE TABLE products(
    product_id int PRIMARY KEY AUTO_INCREMENT,
    product_name varchar(255) NOT NULL,
    size int not null,
    unit varchar(255) not null,
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)ON DELETE CASCADE);