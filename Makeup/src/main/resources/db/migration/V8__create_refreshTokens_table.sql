CREATE TABLE refresh_tokens (
    id CHAR(36) NOT NULL PRIMARY KEY,
    token VARCHAR(512) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    account_id CHAR(36)  NOT NULL,
    CONSTRAINT fk_account
        FOREIGN KEY(account_id)
        REFERENCES account(id)
        ON DELETE CASCADE
);
