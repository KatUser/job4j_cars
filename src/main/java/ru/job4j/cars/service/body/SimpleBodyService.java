package ru.job4j.cars.service.body;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.BodyRepository;
import ru.job4j.cars.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyService implements BodyService {

    private final BodyRepository bodyRepository;

    @Override
    public List<Body> findAllBodies() {
        return bodyRepository.findAllBodies();
    }

    @Override
    public Optional<Body> findBodyById(int id) {
        return bodyRepository.findBodyById(id);
    }
}
