package myfirst.example.plt.web.Controller;

import myfirst.example.plt.entity.PostData;
import myfirst.example.plt.entity.Posts;
import myfirst.example.plt.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/posts")
    public ResponseEntity create(@RequestBody  Posts posts){
        Posts res = postsService.save(posts);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/posts/paging")
    public ResponseEntity findAllPaging(Pageable pageable){
        Page<PostData> res = postsService.findAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts/params")
    public ResponseEntity getAllByParam(@RequestParam Long postid){
        List<Posts> result = postsService.findAllByQueryParam(postid);
        return ResponseEntity.ok(result);
    }
}
