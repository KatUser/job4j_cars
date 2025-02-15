package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Picture;

@AllArgsConstructor
public class PictureRepository {

    private final CrudRepository crudRepository;

    public Picture create(Picture picture) {
        crudRepository.run(session -> session.save(picture));
        return picture;
    }
}
