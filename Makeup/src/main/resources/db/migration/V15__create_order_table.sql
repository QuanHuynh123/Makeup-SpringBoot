CREATE TABLE orders (
    id CHAR(36) NOT NULL PRIMARY KEY,
    totalPrice DOUBLE NOT NULL,
    totalQuantity INT NOT NULL,
    orderDate TIMESTAMP NOT NULL,
    pickup_date TIMESTAMP,
    return_date TIMESTAMP,
    status VARCHAR(50),
    user_id CHAR(36) NOT NULL,
    payment_id BIGINT UNSIGNED  NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_payment FOREIGN KEY (payment_id) REFERENCES payment(id)
);
