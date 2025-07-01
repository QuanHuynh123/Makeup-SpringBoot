CREATE TABLE feed_back (
    id CHAR(36) NOT NULL PRIMARY KEY,
    rating INT,
    comment VARCHAR(250) NOT NULL,
    user_id CHAR(36) NOT NULL,
    type_makeup_id BIGINT UNSIGNED NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_feedback_service FOREIGN KEY (type_makeup_id) REFERENCES type_makeup(id)
);
