CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    type_payment VARCHAR(250) NOT NULL,
    status BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);


INSERT INTO payment (id, status, type_payment, created_at, updated_at) VALUES
(1, b'1', 'Cash Payment',NOW(), NOW()),
(2, b'1', 'Online Payment',NOW(),NOW());