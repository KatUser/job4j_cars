package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            var userRepository = new UserRepository(sessionFactory);
            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            userRepository.create(session, user);
            userRepository.findAllUsersOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            userRepository.update(session, user);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(session, user.getId());
            userRepository.findAllUsersOrderById()
                    .forEach(System.out::println);
            userRepository.deleteAllUsers(session);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}