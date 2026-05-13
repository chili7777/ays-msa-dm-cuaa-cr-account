package com.pichincha.dm.cuaa.account.infrastructure.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username:sa}")
    private String dbUser;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name:org.h2.Driver}")
    private String dbDriver;

    @Bean
    @Primary
    public DataSource dataSource() {
        if (dbUrl != null && dbUrl.startsWith("postgresql://")) {
            try {
                URI dbUri = new URI(dbUrl);
                String userInfo = dbUri.getUserInfo();

                String username = dbUser;
                String password = dbPassword;

                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":");
                    username = parts[0];
                    password = parts[1];
                }

                String host = dbUri.getHost();
                int port = dbUri.getPort();
                String path = dbUri.getPath();
                String query = dbUri.getQuery();

                StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://");
                jdbcUrl.append(host);
                if (port != -1) {
                    jdbcUrl.append(":").append(port);
                }
                jdbcUrl.append(path);
                if (query != null) {
                    jdbcUrl.append("?").append(query);
                }

                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(jdbcUrl.toString());
                config.setUsername(username);
                config.setPassword(password);
                config.setDriverClassName("org.postgresql.Driver");

                return new HikariDataSource(config);
            } catch (URISyntaxException e) {
                throw new RuntimeException("Error parsing PostgreSQL URL: " + dbUrl, e);
            }
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);

        if (dbUrl != null && dbUrl.startsWith("jdbc:postgresql:")) {
            config.setDriverClassName("org.postgresql.Driver");
        } else {
            config.setDriverClassName(dbDriver);
        }

        return new HikariDataSource(config);
    }
}