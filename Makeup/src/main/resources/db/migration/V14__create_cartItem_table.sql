CREATE TABLE cart_item (
    id CHAR(36) NOT NULL PRIMARY KEY,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    rental_date TIMESTAMP NOT NULL,
    cart_id CHAR(36) NOT NULL,
    product_id CHAR(36) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(id),
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES product(id)
);
