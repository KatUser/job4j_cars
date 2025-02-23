package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BodyRepository {

    private final CrudRepository crudRepository;

    public Body create(Body body) {
        crudRepository.run(session -> session.save(body));
        return body;
    }

    public List<Body> findAllBodies() {
        return crudRepository.query(
                "from Body",
                Body.class
        );
    }

    public Optional<Body> findBodyById(int bodyId) {
        return crudRepository.optional(
                "from Body where id = :fId", Body.class,
                Map.of("fId", bodyId)
        );
    }
}
