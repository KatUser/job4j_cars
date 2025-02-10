ALTER TABLE post
    ADD COLUMN brand_id INT
        REFERENCES brand (id);