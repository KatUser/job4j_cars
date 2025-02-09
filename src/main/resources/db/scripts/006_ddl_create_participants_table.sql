CREATE TABLE participants
(
    id      SERIAL PRIMARY KEY,
    post_id INT NOT NULL REFERENCES post (id),
    user_id INT NOT NULL REFERENCES auto_user (id),
    UNIQUE (user_id, post_id)
);