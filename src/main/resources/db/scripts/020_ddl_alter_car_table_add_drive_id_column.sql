ALTER TABLE car
    ADD COLUMN drive_id INT
        REFERENCES drive (id);