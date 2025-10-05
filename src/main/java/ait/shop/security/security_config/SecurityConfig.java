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
                // Этот метод отключает защиту CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Этот метод служит для настройки сессий
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Включаем базовую авторизацию (при помощи логина и пароля)
                .httpBasic(Customizer.withDefaults())
                // При помощи этого метода мы конфигурируем доступ к разному функционалу
                // приложения для разных ролей пользователей
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customers/{customerId}/add-product/{productId}")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customers/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/customers/{customerId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/customers/name").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customers/restore/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/customers/active-count").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/customers/total-bucket-price/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/customers/average-bucket-price/{id}").hasAnyRole("ADMIN", "USER")

                );
        // Собирает цепочку фильтров, которые мы прописали выше
        return http.build();
    }

}
