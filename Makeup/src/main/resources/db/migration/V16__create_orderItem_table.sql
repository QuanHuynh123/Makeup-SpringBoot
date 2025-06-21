CREATE TABLE order_item (
    id CHAR(36) NOT NULL PRIMARY KEY,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    rental_date TIMESTAMP NOT NULL,
    status VARCHAR(50),
    order_id CHAR(36) NOT NULL,
    product_id CHAR(36) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES product(id)
);
