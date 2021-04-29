package io.recheck;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
        @Server(url = "https://uoi.recheck.io/", description = "ReCheck UOI"),
        @Server(url = "https://uoi-exp.recheck.io/", description = "ReCheck UOI Experimental"),
        @Server(url = "http://localhost:8080/", description = "localhost")
})
@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
        System.out.println("The system is on!");
    }

}