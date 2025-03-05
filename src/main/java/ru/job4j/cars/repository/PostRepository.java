package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    /**
     * Показать объявления за последний день;
     */
    public List<Post> findAllPostsForToday() {
        var yesterday = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "from Post where created > :fYesterday",
                Post.class,
                Map.of("fYesterday", yesterday)
        );
    }

    /**
     * Показать объявления с фото
     */
    public List<Post> findAllPostsWithPics() {
        return crudRepository.query("""
                        select distinct p
                        from Post p
                        join p.picture
                        where size(p.picture) > 0
                        """,
                Post.class);
    }

    /**
     * Показать объявления определенной марки
     */
    public List<Post> findAllPostsByBrand(String brand) {
        return crudRepository.query("""
                        from Post
                        where car.brand in
                        (select id
                        from Brand
                        where brand = :fBrand)
                        """,
                Post.class,
                Map.of("fBrand", brand)
        );
    }

    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    public List<Post> findAllPosts() {
        return crudRepository.query(
                "from Post",
                Post.class
        );
    }

    public Optional<Post> findPostById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    public void delete(Post post) {
       crudRepository.run(session -> session.delete(post));
    }

    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    public void setAsSold(int id) {
        crudRepository.run(
                "UPDATE Post SET sold = true WHERE id = :fId",
                Map.of("fId", id));
    }
}
