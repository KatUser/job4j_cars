package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BrandRepository {
    private final CrudRepository crudRepository;

    public Brand create(Brand brand) {
        crudRepository.run(session -> session.save(brand));
        return brand;
    }

    public List<Brand> findAllBrands() {
        return crudRepository.query(
                "from Brand",
                Brand.class
        );
    }

    public Optional<Brand> findBrandById(int brandId) {
        return crudRepository.optional(
                "from Brand where id = :fId", Brand.class,
                Map.of("fId", brandId)
        );
    }
}
