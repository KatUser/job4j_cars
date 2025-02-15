package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Picture;
import ru.job4j.cars.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

class PictureRepositoryTest {
    private final SessionFactory sf
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    private CrudRepository crudRepository;

    private PictureRepository pictureRepository;

    @BeforeEach
    void setUp() {
        crudRepository = new CrudRepository(sf);

        pictureRepository = new PictureRepository(crudRepository);
    }

    @AfterEach
    void cleanUp() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Picture").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void whenSaveAPictureThenReturnTheSamePicture() {
        var postRepository = new PostRepository(crudRepository);
        Post post = new Post();
        postRepository.create(post);

        Picture picture = new Picture(1, post, "picture.jpg");
        assertThat(pictureRepository.create(picture)).isEqualTo(picture);
        assertThat(pictureRepository.create(picture)).isInstanceOf(Picture.class);
    }

}