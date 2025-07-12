package efub.cpbr.crumble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CrumbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrumbleApplication.class, args);
    }

}
