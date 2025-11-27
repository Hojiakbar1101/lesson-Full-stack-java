package myfirst.example.plt.service;

import myfirst.example.plt.entity.Posts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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
}
