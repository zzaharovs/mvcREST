package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private final ConcurrentMap<Long, Post> postMap;
    private final AtomicLong postMapCounter = new AtomicLong();

    public PostRepository() {
        this.postMap = new ConcurrentHashMap<>();
        this.postMapCounter.set(0);
    }

    public List<Post> all() {

        List<Post> result = new ArrayList<>(postMap.values()).stream()
                .filter(x -> !x.isRemoved())
                .collect(Collectors.toList());

        if (!result.isEmpty()) return result;

        throw new NotFoundException("List posts is empty");

    }

    public Optional<Post> getById(long id) {
        if (!postMap.isEmpty() && !postMap.get(id).isRemoved()) {
            return Optional.ofNullable(postMap.get(id));
        }
        throw new NotFoundException(String.format("Post with specified id %d not found", id));

    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postMapCounter.incrementAndGet());
            postMap.put(post.getId(), post);
            return post;
        }
        if (postMap.containsKey(post.getId()) && !postMap.get(post.getId()).isRemoved()) {
            postMap.put(post.getId(), post);
            return post;
        }
        throw new NotFoundException(String.format("Post with specified id %d not found", post.getId()));
    }

    public void removeById(long id) {

        if (postMap.containsKey(id) && !postMap.get(id).isRemoved()) {
            postMap.get(id).removePost();
            return;
        }
        throw new NotFoundException(String.format("Post with specified id %d not found", id));
    }

}
