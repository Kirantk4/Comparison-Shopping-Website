DELETE FROM notifications_associations;
DELETE FROM notifications;
DELETE FROM cart_contents;
DELETE FROM carts;
DELETE FROM delivery_contents;
DELETE FROM deliveries;
DELETE FROM customers;
DELETE FROM price_histories;
DELETE FROM current_prices;
DELETE FROM products;
DELETE FROM categories;
-- --Delete Tables
DROP TABLE notifications_associations;
DROP TABLE cart_contents;
DROP TABLE delivery_contents;
DROP TABLE price_histories;
DROP TABLE current_prices;
DROP TABLE notifications;
DROP TABLE products;
DROP TABLE categories;
-- -- Remake Tables
CREATE TABLE categories(
    category_id int PRIMARY KEY AUTO_INCREMENT,
    category_name varchar(255) NOT NULL,
    parent_category_id int,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id) ON DELETE CASCADE
);
CREATE TABLE products(
    product_id int PRIMARY KEY AUTO_INCREMENT,
    product_name varchar(255) NOT NULL,
    size int not null,
    unit varchar(255) not null,
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)ON DELETE CASCADE
);
CREATE TABLE current_prices(
    product_id int NOT NULL,
    product_price decimal(10,2) NOT NULL,
    super_market varchar(255) NOT NULL,
    PRIMARY KEY (product_id, super_market),
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE
);
CREATE TABLE cart_contents(
    product_id int NOT NULL,
    cart_id int NOT NULL,
    quantity int not null,
    PRIMARY KEY (product_id, cart_id),
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id)ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE
);
CREATE TABLE delivery_contents(
    delivery_id int NOT NULL,
    product_id int NOT NULL,
    quantity int NOT NULL,
    price decimal(10,2) NOT NULL,
    PRIMARY KEY (delivery_id, product_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE,
    FOREIGN KEY (delivery_id) REFERENCES deliveries(order_id)ON DELETE CASCADE
);
CREATE TABLE notifications(
    notification_id int PRIMARY KEY AUTO_INCREMENT,
    product_id int NOT NULL,
    notification_description varchar(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE
);
CREATE TABLE notifications_associations(
    notification_id int,
    customer_id int,
    PRIMARY KEY (notification_id, customer_id),
    FOREIGN KEY (notification_id) REFERENCES notifications(notification_id)ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)ON DELETE CASCADE
);
CREATE TABLE price_histories (
    product_id int NOT NULL,
    super_market varchar(255) NOT NULL,
    price_date date NOT NULL,
    product_price decimal(10,2) NOT NULL,
    PRIMARY KEY (product_id, price_date, super_market),
    FOREIGN KEY (product_id, super_market) REFERENCES current_prices(product_id, super_market) ON DELETE CASCADE
);

