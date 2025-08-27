CREATE TABLE account (
    id CHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(250) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_account_role FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO account(id, username, password, role_id, created_at, updated_at)
VALUES (
    "a4be1774-f8d4-4fd2-a9c5-c86ac1af7bdd",
    'admin',
    '$2a$10$EJXYTLeRAsHQQxZv7eHUNuVPHRGGlu1gL6XRPE26l6BFeDvPFNdV.',  -- hashed password for 'admin'
    1,
    NOW(),
    NOW()
);