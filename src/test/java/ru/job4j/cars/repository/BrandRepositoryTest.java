package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;

import static org.assertj.core.api.Assertions.assertThat;

class BrandRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        CrudRepository crudRepository = new CrudRepository(sf);

        brandRepository = new BrandRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Brand").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveABrandThenReturnTheSameBrand() {
        var brand = new Brand();
        assertThat(brandRepository.create(brand)).isEqualTo(brand);
        assertThat(brandRepository.create(brand)).isInstanceOf(Brand.class);
    }

}