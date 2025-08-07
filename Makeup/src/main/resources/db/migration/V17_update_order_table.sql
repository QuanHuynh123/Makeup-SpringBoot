ALTER TABLE orders
ADD COLUMN unique_request_id VARCHAR(36) NOT NULL,
ADD CONSTRAINT uk_orders_unique_request_id UNIQUE (unique_request_id);