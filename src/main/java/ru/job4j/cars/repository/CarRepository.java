package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    public void delete(int userId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", userId)
        );
    }

    public List<Car> findAllOrderById() {
        return crudRepository.query("from Car order by id asc",
                Car.class);
    }

    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "from Car where id = :fId",
                Car.class,
                Map.of("fId", carId)
        );
    }

    public void deleteAllCars() {
        crudRepository.run("delete from Car");
    }

}
