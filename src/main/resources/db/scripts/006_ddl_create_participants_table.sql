CREATE TABLE participants
(
    id      SERIAL PRIMARY KEY,
    post_id INT NOT NULL REFERENCES auto_post (id),
    user_id INT NOT NULL REFERENCES auto_user (id),
    UNIQUE (user_id, post_id)
);