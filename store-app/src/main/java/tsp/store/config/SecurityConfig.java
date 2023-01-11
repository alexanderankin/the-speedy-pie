package tsp.store.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfig {
    @SneakyThrows
    @Bean
    DefaultSecurityFilterChain security(HttpSecurity httpSecurity) {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests().anyRequest().authenticated().and()
                .formLogin().and()
                .build();
    }
}
