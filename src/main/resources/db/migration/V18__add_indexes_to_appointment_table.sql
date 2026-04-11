CREATE INDEX idx_appointment_staff_date_status
    ON appointment (staff_id, makeup_date, status);

CREATE INDEX idx_appointment_user_date
    ON appointment (user_id, makeup_date);

CREATE INDEX idx_appointment_makeup_date
    ON appointment (makeup_date);