ALTER TABLE post
    ADD COLUMN car_id INT
        REFERENCES car (id);