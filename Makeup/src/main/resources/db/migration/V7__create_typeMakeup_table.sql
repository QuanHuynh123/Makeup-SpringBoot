CREATE TABLE type_makeup (
    id SERIAL PRIMARY KEY,
    name_makeup VARCHAR(255) NOT NULL,
    description VARCHAR(250),
    price DOUBLE PRECISION NOT NULL,
    time_makeup INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);


INSERT INTO type_makeup ( price,time_makeup, description, name_makeup, created_at, updated_at) VALUES
( 500, 2, 's', 'Makeup Đám Cưới',NOW(), NOW()),
(500, 2, 's', 'Makeup Đi Chơi/Event',NOW(), NOW()),
( 500, 2, 's', 'Makeup Mẫu Ảnh',NOW(), NOW()),
( 500, 2, 's', 'Makeup Model',NOW(), NOW()),
( 500, 2, 's', 'Makeup Công Sở',NOW(), NOW()),
(500, 2, 's', 'Makeup Sexy',NOW(), NOW());
