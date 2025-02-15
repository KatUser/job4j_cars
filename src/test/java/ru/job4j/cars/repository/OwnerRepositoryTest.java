package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.Owner;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        CrudRepository crudRepository = new CrudRepository(sf);

        ownerRepository = new OwnerRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Owner").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveAnOwnerThenReceiveIt() {
        var owner = new Owner();
        assertThat(ownerRepository.create(owner)).isEqualTo(owner);
    }

    @Test
    public void whenUpdateAaOwnerThenReceiveNewData() {
        var owner = new Owner();
        ownerRepository.create(owner);
        owner.setId(1);
        ownerRepository.update(owner);
        assertThat(ownerRepository.findById(1).get()).isEqualTo(owner);
    }

    @Test
    public void whenSaveAnOwnerAndDeleteItThenDoNotReceiveIt() {
        var owner = new Owner();
        ownerRepository.create(owner);
        owner.setId(1);
        ownerRepository.delete(1);
        assertThat(ownerRepository.findById(owner.getId())).isEmpty();
    }

    @Test
    public void whenSaveOwnersAndGetThemOrderById() {
        var owner1 = new Owner();
        var owner2 = new Owner();
        var owner3 = new Owner();
        ownerRepository.create(owner1);
        ownerRepository.create(owner2);
        ownerRepository.create(owner3);
        owner3.setId(3);
        owner2.setId(2);
        owner1.setId(1);
        var result = ownerRepository.findAllOrderById();
        assertThat(result.getFirst().getId()).isEqualTo(1);
        assertThat(result.get(1).getId()).isEqualTo(2);
        assertThat(result.getLast().getId()).isEqualTo(3);
    }

    @Test
    public void whenSaveAnOwnerThenCanGetItById() {
        var owner = new Owner();
        ownerRepository.create(owner);
        owner.setId(1);
        assertThat(ownerRepository.findById(1).get()).isEqualTo(owner);
    }

    @Test
    public void whenSaveCarsThenCannotGetThemAfterDeleting() {
        var owner1 = new Owner();
        var owner2 = new Owner();
        ownerRepository.create(owner1);
        ownerRepository.create(owner2);
        ownerRepository.deleteAll();
        assertThat(ownerRepository.findAllOrderById()).isEmpty();
    }

}