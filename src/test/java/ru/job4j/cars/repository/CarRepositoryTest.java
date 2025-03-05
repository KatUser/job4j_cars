package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryTest {

    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private CrudRepository crudRepository;

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        crudRepository = new CrudRepository(sf);

        carRepository = new CarRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Car").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveACarThenReceiveIt() {
        var car = new Car();
        assertThat(carRepository.create(car)).isEqualTo(car);
    }

    @Test
    public void whenUpdateACarThenReceiveNewData() {
        var engineRepository = new EngineRepository(crudRepository);
        var engine = new Engine();
        engineRepository.create(engine);
        engine.setId(1);
        var car = new Car();
        carRepository.create(car);
        car.setEngine(engine);
        carRepository.update(car);
        assertThat(car.getEngine().getId()).isEqualTo(1);
    }

    @Test
    public void whenSaveACarAndDeleteThenDoNotReceiveIt() {
        var car = new Car();
        carRepository.create(car);
        car.setId(1);
        carRepository.delete(1);
        assertThat(carRepository.findById(car.getId())).isEmpty();
    }

    @Test
    public void whenSaveCarsAndGetThemOrderedById() {
        var car1 = new Car();
        var car2 = new Car();
        var car3 = new Car();
        carRepository.create(car2);
        carRepository.create(car1);
        carRepository.create(car3);
        car2.setId(2);
        car3.setId(3);
        car1.setId(1);
        var result = carRepository.findAllOrderById();
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(2).getId()).isEqualTo(3);
        assertThat(result.get(1).getId()).isEqualTo(2);
    }

    @Test
    public void whenSaveACarThenCanGetItById() {
        var car = new Car();
        carRepository.create(car);
        car.setId(1);
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
    }

    @Test
    public void whenSaveCarsThenCannotGetThemAfterDeletingAll() {
        var car1 = new Car();
        var car2 = new Car();
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.deleteAllCars();
        assertThat(carRepository.findAllOrderById()).isEmpty();
    }

}