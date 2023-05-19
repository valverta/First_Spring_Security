package ru.alishev.First_Spring_Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import ru.alishev.First_Spring_Security.security.AuthProviderImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

//    @Bean
//    public SecurityFilterChain filterChain(AuthenticationManagerBuilder auth) throws Exception {
//        return auth.authenticationProvider(authProvider);
//    }

    // Настраивает аутентификацию
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authProvider);
//    }
}
