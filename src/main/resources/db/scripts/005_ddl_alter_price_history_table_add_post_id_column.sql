ALTER TABLE price_history
    ADD COLUMN post_id INT
        REFERENCES post (id);