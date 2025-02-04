CREATE TABLE history
(
    id      SERIAL PRIMARY KEY
        REFERENCES owner_history (id),
    start_at TIMESTAMP,
    end_at   TIMESTAMP
);