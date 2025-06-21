CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name_role VARCHAR(40) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO role (id,name_role,created_at,updated_at) VALUES
(1, 'ADMIN',NOW(),NOW()),
(2, 'USER',NOW(),NOW()),
(3, 'STAFF',NOW(),NOW());