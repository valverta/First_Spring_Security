package ru.alishev.First_Spring_Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.alishev.First_Spring_Security.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Конфигурируем сам Spring Security
//        // Конфигурируем авторизацию
//
//        http.csrf().disable()  //отключаем защиту от межсайтовой подделки запросов
//                .authorizeRequests()
//                .antMatchers("/auth/login", "/error").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/auth/login")
//                .loginProcessingUrl("process_login")
//                .defaultSuccessUrl("/hello", true)
//                .failureUrl("/auth/login?error");
//        return http.build();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Конфигурируем сам Spring Security
        // Конфигурируем авторизацию

        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
        //.csrf().disable()  //отключаем защиту от межсайтовой подделки запросов, она включена автоматически
    }

    // Настраивает аутентификацию (если использовать свою форму, то без этого не работает)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
