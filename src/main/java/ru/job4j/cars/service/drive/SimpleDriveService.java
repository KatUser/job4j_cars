package ru.job4j.cars.service.drive;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Drive;
import ru.job4j.cars.repository.DriveRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleDriveService implements DriveService {

    private final DriveRepository driveRepository;

    @Override
    public List<Drive> findAllDrives() {
        return driveRepository.findAllDrives();
    }

    @Override
    public Optional<Drive> findDriveById(int id) {
        return driveRepository.findDriveById(id);
    }
}
