package io.recheck.external.systems;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Slf4j
@Service
public class RestClientService implements Serializable {

    private RestTemplate rest;
    private HttpHeaders headers;

    public RestClientService() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public ResponseEntity get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        log.debug("Send GET {}", uri);
        ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        log.debug("Send POST {} \n Body: {}", uri, requestEntity);
        ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity put(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        log.debug("Send PUT {} \n Body: {}", uri, requestEntity);
        ResponseEntity<String> responseEntity = rest.exchange( uri, HttpMethod.PUT, requestEntity, (Class<String>) null);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity delete(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        log.debug("Send DELETE {} \n Body: {}", uri, requestEntity);
        ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.DELETE, requestEntity, (Class<String>) null);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

}
