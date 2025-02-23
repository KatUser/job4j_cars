package ru.job4j.cars.service.drive;

import ru.job4j.cars.model.Drive;

import java.util.List;
import java.util.Optional;

public interface DriveService {
    List<Drive> findAllDrives();

    Optional<Drive> findDriveById(int id);
}
