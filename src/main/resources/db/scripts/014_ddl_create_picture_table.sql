CREATE TABLE picture
(
    id SERIAL PRIMARY KEY,
    post_id INT REFERENCES post (id),
    path TEXT
);