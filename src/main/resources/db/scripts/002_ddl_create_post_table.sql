CREATE TABLE post
(
    id SERIAL PRIMARY KEY,
    description VARCHAR NOT NULL,
    created TIMESTAMP,
    user_id INT REFERENCES auto_user(id),
    sold boolean
);