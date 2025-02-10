ALTER TABLE car
    ADD COLUMN currentowner_id INT
        REFERENCES owner (id);