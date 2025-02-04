CREATE TABLE owner_history --таблица для связей @ManyToMany
(
    id       SERIAL PRIMARY KEY,
    owner_id int REFERENCES owner (id),
    car_id   int REFERENCES car (id)
);