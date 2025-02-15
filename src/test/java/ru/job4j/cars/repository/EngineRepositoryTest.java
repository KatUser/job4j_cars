package ru.job4j.cars.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EngineRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private EngineRepository engineRepository;

    @BeforeEach
    void setUp() {
        CrudRepository crudRepository = new CrudRepository(sf);

        engineRepository = new EngineRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Engine ").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveEngineThenReturnSavedEngine() {
        var engine = new Engine();
        assertThat(engineRepository.create(engine)).isEqualTo(engine);
    }

    @Test
    public void whenUpdateAnEngineThenReceiveNewData() {
        var engine = new Engine();
        engine.setId(1);
        engineRepository.update(engine);
        assertThat(engineRepository.findById(1).get()).isEqualTo(engine);
    }

    @Test
    public void whenSaveAnEngineAndDeleteItThenDoNotReceiveIt() {
        var engine = new Engine();
        engineRepository.create(engine);
        engine.setId(1);
        engineRepository.delete(1);
        Assertions.assertThat(engineRepository.findById(engine.getId())).isEmpty();
    }

    @Test
    public void whenSaveEnginesAndGetThemOrderById() {
        var engine1 = new Engine();
        var engine2 = new Engine();
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engine1.setId(1);
        engine2.setId(2);
        var result = engineRepository.findAllOrderById();
        Assertions.assertThat(result.getFirst().getId()).isEqualTo(1);
        Assertions.assertThat(result.getLast().getId()).isEqualTo(2);
    }

    @Test
    public void whenSaveAnEngineThenCanGetItById() {
        var engine = new Engine();
        engineRepository.create(engine);
        engine.setId(1);
        Assertions.assertThat(engineRepository.findById(engine.getId()).get()).isEqualTo(engine);
    }

    @Test
    public void whenSaveEnginesThenCannotGetThemAfterDeleting() {
        var engine1 = new Engine();
        var engine2 = new Engine();
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.deleteAll();
        Assertions.assertThat(engineRepository.findAllOrderById()).isEmpty();
    }

}