package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Brand;

@AllArgsConstructor
public class BrandRepository {
    private final CrudRepository crudRepository;

    public Brand create(Brand brand) {
        crudRepository.run(session -> session.save(brand));
        return brand;
    }
}
