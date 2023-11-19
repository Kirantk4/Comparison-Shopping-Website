DROP TABLE IF EXISTS notifications_associations;
CREATE TABLE notifications_associations(
    notification_id int,
    customer_id int,
    PRIMARY KEY (notification_id, customer_id),
    FOREIGN KEY (notification_id) REFERENCES notifications(notification_id)ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)ON DELETE CASCADE
);