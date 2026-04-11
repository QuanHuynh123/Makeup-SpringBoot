ALTER TABLE appointment
    ADD COLUMN booking_token CHAR(36) NULL UNIQUE AFTER price;