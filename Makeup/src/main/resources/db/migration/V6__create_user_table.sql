CREATE TABLE users (
    id CHAR(36) NOT NULL PRIMARY KEY,
    full_name VARCHAR(40),
    email VARCHAR(40),
    address VARCHAR(50),
    phone VARCHAR(20) NOT NULL,
    account_id CHAR(36),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    is_guest BOOLEAN DEFAULT FALSE NOT NULL,
    guest_token VARCHAR(36),
    CONSTRAINT fk_users_account FOREIGN KEY (account_id) REFERENCES account(id)
);