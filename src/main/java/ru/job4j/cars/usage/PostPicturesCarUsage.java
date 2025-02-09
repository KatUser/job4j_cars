package ru.job4j.cars.usage;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.PostRepository;

public class PostPicturesCarUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
//            var userRepository = new UserRepository(new CrudRepository(sessionFactory));
//            var user = new User();
            var postRep = new PostRepository(new CrudRepository(sessionFactory));
//            postRep.findAllPostsByBrand(1);
//            postRep.findAllPostsForLatestDay();
            postRep.findAllPostsWithPics();

//            user.setLogin("admin");
//            user.setPassword("admin");
//            userRepository.create(user);
//            userRepository.findAllOrderById()
//                    .forEach(System.out::println);
//            userRepository.findByLikeLogin("a")
//                    .forEach(System.out::println);
//            userRepository.findById(user.getId())
//                    .ifPresent(System.out::println);
//            userRepository.findByLogin("admin")
//                    .ifPresent(System.out::println);
//            user.setPassword("password");
//            userRepository.update(user);
//            userRepository.findById(user.getId())
//                    .ifPresent(System.out::println);
//
//            userRepository.delete(user.getId());
//            userRepository.findAllOrderById()
//                    .forEach(System.out::println);
//            userRepository.deleteAll();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
