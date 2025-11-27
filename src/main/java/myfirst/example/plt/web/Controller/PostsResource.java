package myfirst.example.plt.web.Controller;

import myfirst.example.plt.entity.Posts;
import myfirst.example.plt.service.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsResource {

    private final PostsService postsService;

    public PostsResource(PostsService postsService) {
        this.postsService = postsService;
    }
    @GetMapping("/posts")
    public ResponseEntity getAll(){
        List<Posts> result = postsService.findAll();
        return ResponseEntity.ok(result);
    }
}
