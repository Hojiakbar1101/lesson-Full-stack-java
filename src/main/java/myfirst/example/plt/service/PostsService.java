package myfirst.example.plt.service;

import myfirst.example.plt.entity.PostData;
import myfirst.example.plt.entity.Posts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class PostsService {

    private final RestTemplate restTemplate;

    private final PostDataService postDataService;

    @Value("${api.jsonplaceholder}")
    private String api;

    public PostsService(RestTemplate restTemplate, PostDataService PostDataService) {
        this.restTemplate = restTemplate;
        this.postDataService =  PostDataService;
    }

    public Posts save(Posts posts){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Posts> entity = new HttpEntity<>(posts, headers);
        Posts result = restTemplate.patchForObject(api + " /posts", entity, Posts.class);
        return result;

    }

    public List<Posts> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Posts[]> entity = new HttpEntity<>(headers);
        Posts[] result = restTemplate.exchange(this.api + "/posts",
                HttpMethod.GET,
                entity, Posts[].class).getBody();
        postDataService.saveAll(result);
        return result != null ? Arrays.asList(result) : List.of();
    }

    public List<Posts> findAllByQueryParam(Long postId){
        HttpEntity<List<Posts>>  entity = new HttpEntity<>(getHeaders());
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.api+"/comments")
                .queryParam("postId", "{postId}")
                .encode()
                .toUriString();
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        List<Posts> result = restTemplate.exchange(urlTemplate, HttpMethod.GET,
                entity, List.class, params).getBody();
        return result;

    }
//    public List<Posts> update(Long postId, List<Posts> posts){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<List<Posts>> entity = new HttpEntity<>(posts, headers);
//
//    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    public Page<PostData> findAll(Pageable pageable){
        return  postDataService.findAll(pageable);
    }
}
