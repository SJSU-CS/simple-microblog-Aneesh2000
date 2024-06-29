package edu.sjsu.cmpe272.simpleblog.client;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {
//    @Autowired
//    private RestTemplate restTemplate;

    //    public ApiService(RestTemplate restTemplate){
//        this.restTemplate=restTemplate;
//    }
    WebClient webclient = WebClient.builder().build();  // using webclient for restapi
//        .baseUrl("http://localhost:8080/messages/create")
//        .build();

    public String sendRawJsonPostRequest(String url, String rawJson) {

        return webclient.post() // Use the appropriate method (get, post, put, delete, etc.)
                .uri(url) // specify the url
                .header("Content-Type", "application/json") // Set the Content-Type header
                .bodyValue(rawJson) // Pass the raw JSON string as the request body
                .retrieve() // Initiate the request
                .bodyToMono(String.class).block();
    }
}

