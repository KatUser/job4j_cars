package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Picture;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private CrudRepository crudRepository;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        crudRepository = new CrudRepository(sf);

        postRepository = new PostRepository(crudRepository);
    }

    @Test
    public void whenSaveAPostThenReturnSavedPost() {
        Post post = new Post();
        assertThat(postRepository.create(post)).isEqualTo(post);
    }

    @Test
    public void whenSaveAPostOfTodayThenReturnSavedPost() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        postRepository.create(post);
        assertThat(postRepository.findAllPostsForToday()).contains(post);
    }

    @Test
    public void whenSaveAPostWithAPicThenReturnSavedPost() {
        Post post = new Post();
        postRepository.create(post);

        PictureRepository pictureRepository = new PictureRepository(crudRepository);
        Picture picture = new Picture(1, post, "picture.jpg");
        pictureRepository.create(picture);

        assertThat(postRepository.findAllPostsWithPics()).contains(post);
    }

    @Test
    public void whenSaveAPostWithABrandThenReturnSavedPost() {
        BrandRepository brandRepository = new BrandRepository(crudRepository);
        var testBrand = "testBrand";
        var brand = new Brand(1, testBrand);
        brandRepository.create(brand);

        CarRepository carRepository = new CarRepository(crudRepository);
        Car car = new Car();
        carRepository.create(car);
        car.setBrand(brand);
        Post post = new Post();
        post.setCar(car);
        postRepository.create(post);
        assertThat(postRepository.findAllPostsByBrand(testBrand)).contains(post);
    }

    @Test
    public void whenSaveAPostOfYesterdayThenReturnNoSavedPost() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now().minusDays(1));
        postRepository.create(post);
        assertThat(postRepository.findAllPostsForToday()).isEmpty();
    }

}