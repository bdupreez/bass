package net.briandupreez.bass;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationUI extends SpringApplication {

    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(ApplicationUI.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
