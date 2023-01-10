package tsp.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain security(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf().disable()
                .authorizeExchange().anyExchange().authenticated().and()
                .formLogin().and()
                .build();
    }
}
