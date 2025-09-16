package ait.shop.security.security_config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
1. Получение всех продуктов - всем пользователям, включая анонимных (не прошли аутентификацию)
2. Получение продукта по id - аутентифицированным пользователям с любой ролью (залогинен)
3. Сохранение продукта в БД - только администраторам (пользователя с ролью ADMIN).
 */

@EnableWebSecurity // все входящие http запросы будут проверяться Security
@EnableMethodSecurity // Позволяет защитить отдельные методы
@Configuration
public class SecurityConfig {

    @Bean
    // Метод для шифрования паролей
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")

                );
        return http.build();
    }

}
