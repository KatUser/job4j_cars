package ru.job4j.cars.service.picture;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.job4j.cars.model.Picture;
import ru.job4j.cars.repository.PictureRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePictureService implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    public Picture create(Picture picture) {
        return pictureRepository.create(picture);
    }

    @Override
    public Optional<Picture> findByPost(int postId) {
        return pictureRepository.findByPost(postId);
    }

    @Override
    public Optional<Picture> findById(int id) {
        try {
            pictureRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pictureRepository.findById(id);
    }

    @Override
    public void update(Picture picture) {
        pictureRepository.update(picture);
    }

    @Override
    public void delete(Picture picture) {
        pictureRepository.delete(picture);
    }
}
