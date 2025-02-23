package ru.job4j.cars.service.body;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyService {

    List<Body> findAllBodies();

    Optional<Body> findBodyById(int id);
}
