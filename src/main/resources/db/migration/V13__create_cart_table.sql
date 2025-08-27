CREATE TABLE cart (
    id CHAR(36) NOT NULL PRIMARY KEY,
    total_price DOUBLE NOT NULL,
    total_quantity INT NOT NULL,
    user_id CHAR(36) UNIQUE,  -- one-to-one nên UNIQUE để đảm bảo chỉ một cart cho mỗi user
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id)
);
