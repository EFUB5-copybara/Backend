package efub.cpbr.crumble.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private List<String> allowOrigins;

    public List<String> getAllowOrigins() {
        return allowOrigins;
    }

    public void setAllowOrigins(List<String> allowOrigins) {
        this.allowOrigins = allowOrigins;
    }

}
