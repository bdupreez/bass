package net.briandupreez.bass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableResourceServer
public class AuthServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
