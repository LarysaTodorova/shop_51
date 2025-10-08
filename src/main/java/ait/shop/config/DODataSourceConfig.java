package ait.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("fake-profile")
public class DODataSourceConfig {

    @Value("${DB_HOST}")
    private String host;

    @Value("${DB_PORT}")
    private String port;

    @Value("${DB_NAME}")
    private String name;

    @Value("${DB_USERNAME}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    // jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driver)
                .url(String.format("jdbc:postgresql://%s:%s/%s", host, port, name))
                .username(username)
                .password(password)
                .build();
    }
}
