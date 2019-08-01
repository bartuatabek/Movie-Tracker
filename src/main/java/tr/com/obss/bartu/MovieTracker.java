package tr.com.obss.bartu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MovieTracker {

    public static void main(String[] args) {
        SpringApplication.run(MovieTracker.class, args);
    }
}
