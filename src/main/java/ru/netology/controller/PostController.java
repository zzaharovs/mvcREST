package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Post save(@RequestBody Post post) throws IOException {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public String removeById(@PathVariable Long id) {

        service.removeById(id);
            return String.format("Delete post %d success", id);
    }

}
