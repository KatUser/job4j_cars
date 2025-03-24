package ru.job4j.cars.service.picture;

import ru.job4j.cars.model.Picture;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface PictureService {
    Optional<Picture> findByPost(int postId);

    Picture create(Picture picture);

    Optional<Picture> findById(int id) throws Exception;

    void update(Picture picture);

    void delete(Picture picture);
}
