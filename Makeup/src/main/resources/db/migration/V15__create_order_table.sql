CREATE TABLE orders (
    id CHAR(36) NOT NULL PRIMARY KEY,
    total_price DOUBLE NOT NULL,
    total_quantity INT NOT NULL,
    order_date TIMESTAMP NOT NULL,
    pickup_date TIMESTAMP,
    return_date TIMESTAMP,
    status VARCHAR(50),

    receiver_name VARCHAR(40),
    receiver_email VARCHAR(100),
    receiver_phone VARCHAR(20),
    receiver_message VARCHAR(200),
    receiver_address VARCHAR(200),
    type_shipping VARCHAR(50),

    user_id CHAR(36) NOT NULL,
    payment_id BIGINT UNSIGNED  NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_payment FOREIGN KEY (payment_id) REFERENCES payment(id)
);
