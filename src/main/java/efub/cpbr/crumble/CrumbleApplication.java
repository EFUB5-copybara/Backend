package efub.cpbr.crumble;

import efub.cpbr.crumble.global.config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableConfigurationProperties(CorsProperties.class)
public class CrumbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrumbleApplication.class, args);
    }

}
