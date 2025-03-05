package ru.job4j.cars.service.post;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAllPosts();

    Optional<Post> findPostById(int id);

    Post create(Post post);

    void delete(Post post);

    void update(Post post);

    void setAsSold(int id);

}
