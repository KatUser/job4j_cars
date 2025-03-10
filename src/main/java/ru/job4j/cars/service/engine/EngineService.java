package ru.job4j.cars.service.engine;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineService {

    List<Engine> findAllEngines();

    Optional<Engine> findById(int id);
}
