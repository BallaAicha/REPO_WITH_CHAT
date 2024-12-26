package org.etutoria.usersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UsersServiceApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }


}