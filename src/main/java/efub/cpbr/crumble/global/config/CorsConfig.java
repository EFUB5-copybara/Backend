package efub.cpbr.crumble.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
//@RequiredArgsConstructor
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {
    private final CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
        System.out.println("CorsConfig 생성자 실행됨 ");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()  {
        CorsConfiguration configuration = new CorsConfiguration();
        System.out.println("CORS 허용 Origin 목록: " + corsProperties.getAllowOrigins());
        //configuration.setAllowedOrigins(corsProperties.getAllowOrigins());
        configuration.setAllowedOriginPatterns(corsProperties.getAllowOrigins());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
