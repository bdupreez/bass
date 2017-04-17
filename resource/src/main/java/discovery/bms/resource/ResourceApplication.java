package discovery.bms.resource;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableResourceServer
public class ResourceApplication {

    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(ResourceApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
