CREATE TABLE appointment (
    id CHAR(36) NOT NULL PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    makeup_date DATE  NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    user_id CHAR(36) NOT NULL ,
    type_makeup_id BIGINT UNSIGNED NOT NULL,
    staff_id CHAR(36),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_appointment_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_appointment_service FOREIGN KEY (type_makeup_id) REFERENCES type_makeup(id),
    CONSTRAINT fk_appointment_staff FOREIGN KEY (staff_id) REFERENCES staff(id)
);
