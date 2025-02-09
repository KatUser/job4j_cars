package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    /**
     * Показать объявления за последний день;
     */
    public List<Post> findAllPostsForLatestDay() {
        return crudRepository.query(
                "from Post where created = current_date()", Post.class);
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
    public List<Post> findAllPostsByBrand(int brandId) {
        return crudRepository.query(
                "from Post where brand_id = :fId",
                Post.class,
                Map.of("fId", brandId)
        );
    }
}
