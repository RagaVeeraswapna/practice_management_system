package com.revature.springapigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringApiGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiGwApplication.class, args);
    }
//    @Bean
//    public RouteLocator serviceRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p.path("/health-record/*").uri("http://localhost:8081/health-record"))
//                .route(p -> p.path("/allergies/*").uri("http://localhost:8082/allergies"))
//                .build();
//    }
}


