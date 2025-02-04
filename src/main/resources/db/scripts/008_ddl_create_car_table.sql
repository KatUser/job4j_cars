CREATE TABLE car
(
    id        SERIAL PRIMARY KEY,
    engine_id int
        NOT NULL UNIQUE REFERENCES engine (id)
);