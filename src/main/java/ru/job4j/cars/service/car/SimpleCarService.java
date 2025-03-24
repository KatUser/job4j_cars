package ru.job4j.cars.service.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAllOrderById();
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }
}
