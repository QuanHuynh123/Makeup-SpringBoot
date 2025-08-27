CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO `category` (id, name,created_at,updated_at) VALUES
(1, 'ANIME',NOW(), NOW()),
(2, 'MOVIE',NOW(), NOW()),
(3, 'GAME',NOW(), NOW()),
(4, 'FESTIVAL',NOW(), NOW());