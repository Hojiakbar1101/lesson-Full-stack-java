package myfirst.example.plt.service;


import myfirst.example.plt.entity.PostData;
import myfirst.example.plt.entity.Posts;
import myfirst.example.plt.repository.PostDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostDataService {

    private final PostDataRepository postDataRepository;

    public PostDataService(PostDataRepository postDataRepository) {
        this.postDataRepository = postDataRepository;
    }

    public PostData save(PostData postData){
        return postDataRepository.save(postData);
    }

    public List<PostData> saveAll(Posts[] posts){
        List<PostData> postDataList = new ArrayList<>();
        for(Posts post:posts){
            PostData postData = new PostData();
            postData.setPostId(post.getId());
            postData.setUserId(post.getUserId());
            postData.setTitle(post.getTitle());
            postData.setBody(post.getBody());
            postDataList.add(postData);
        }
        return postDataRepository.saveAll(postDataList);

    }
}
