package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Picture;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PictureRepository {

    private final CrudRepository crudRepository;

    public Picture create(Picture picture) {
        crudRepository.run(session -> session.save(picture));
        return picture;
    }

    public Optional<Picture> findByPost(int postId) {
        return crudRepository.optional("from Picture where post_id = :fPostId",
                Picture.class,
                Map.of("fPostId", postId));
    }

    public Optional<Picture> findById(int id) {
        return crudRepository.optional("from Picture where id = :fId",
                Picture.class,
                Map.of("fId", id));
    }

    public void update(Picture picture) {
        crudRepository.run(session -> session.merge(picture));
    }

    public void delete(Picture picture) {
        crudRepository.run(session -> session.delete(picture));
    }

}
