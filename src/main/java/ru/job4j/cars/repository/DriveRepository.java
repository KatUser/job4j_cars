package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Drive;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DriveRepository {

    private final CrudRepository crudRepository;

    public Drive create(Drive drive) {
        crudRepository.run(session -> session.save(drive));
        return drive;
    }

    public List<Drive> findAllDrives() {
        return crudRepository.query(
                "from Drive",
                Drive.class
        );
    }

    public Optional<Drive> findDriveById(int driveId) {
        return crudRepository.optional(
                "from Drive where id = :fId", Drive.class,
                Map.of("fId", driveId)
        );
    }
}
