package com.customer.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({ "classpath:instructor-mysql.properties" })
public class PersistenceConfiguration {

    @Value("${spring.datasource.url}")
    private String dbHost;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(this.dbHost);
        builder.username(this.dbUsername);
        builder.password(this.dbPassword);
        System.out.println("My custom datasource bean has been initialized and set");
        return builder.build();
    }

}
