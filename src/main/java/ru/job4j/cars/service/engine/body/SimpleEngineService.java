package ru.job4j.cars.service.engine.body;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.BodyRepository;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {

    private final EngineRepository engineRepository;

    @Override
    public List<Engine> findAll() {
        return engineRepository.findAllOrderById();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }
}
