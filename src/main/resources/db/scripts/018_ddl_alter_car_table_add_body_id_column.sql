ALTER TABLE car
    ADD COLUMN body_id INT
        REFERENCES body (id);