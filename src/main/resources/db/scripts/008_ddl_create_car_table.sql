CREATE TABLE car
(
    id        SERIAL PRIMARY KEY,
    brand_id int
        NOT NULL REFERENCES brand (id),
    body_id int
        NOT NULL REFERENCES body(id),
    drive_id int
        NOT NULL REFERENCES drive(id),
    engine_id int
        NOT NULL REFERENCES engine (id),
    currentowner_id int
        REFERENCES owner(id)

);