ALTER TABLE car
    ADD COLUMN brand_id INT
        REFERENCES brand (id);