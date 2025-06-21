CREATE TABLE account (
    id CHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(250) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_account_role FOREIGN KEY (role_id) REFERENCES role(id)
);