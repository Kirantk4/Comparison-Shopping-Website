DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
    category_id int PRIMARY KEY AUTO_INCREMENT,
    category_name varchar(255) NOT NULL,
    parent_category_id int,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id) ON DELETE CASCADE
);