DROP TABLE IF EXISTS notifications;
CREATE TABLE notifications(
    notification_id int PRIMARY KEY AUTO_INCREMENT,
    product_id int NOT NULL,
    notification_description varchar(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id)ON DELETE CASCADE);