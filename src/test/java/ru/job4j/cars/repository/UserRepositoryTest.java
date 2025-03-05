package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        CrudRepository crudRepository = new CrudRepository(sf);

        userRepository = new UserRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveAUserThenReceiveIt() {
        var user = new User();
        assertThat(userRepository.create(user)).isEqualTo(user);
    }

    @Test
    public void whenUpdateAUserThenReceiveNewData() {
        var user = new User();
        user.setId(1);
        userRepository.update(user);
        assertThat(userRepository.findById(1).get()).isEqualTo(user);
    }

    @Test
    public void whenSaveAUserAndDeleteItThenDoNotReceiveIt() {
        var user = new User();
        userRepository.create(user);
        user.setId(1);
        userRepository.delete(1);
        assertThat(userRepository.findById(1)).isEmpty();
    }

    @Test
    public void whenSaveUsersAndGetThemOrderById() {
        var user1 = new User();
        var user2 = new User();
        var user3 = new User();
        userRepository.create(user3);
        userRepository.create(user2);
        userRepository.create(user1);
        user3.setId(3);
        user2.setId(2);
        user1.setId(1);
        var result = userRepository.findAllOrderById();
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(1).getId()).isEqualTo(2);
        assertThat(result.get(2).getId()).isEqualTo(3);

    }

    @Test
    public void whenSaveAUserThenCanGetItById() {
        var user = new User();
        user.setId(1);
        userRepository.create(user);
        assertThat(userRepository.findById(1).get()).isEqualTo(user);
    }

    @Test
    public void whenSaveCarsThenCannotGetThemAfterDeleting() {
        var user1 = new User();
        var user2 = new User();
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.deleteAll();
        assertThat(userRepository.findAllOrderById()).isEmpty();
    }

}