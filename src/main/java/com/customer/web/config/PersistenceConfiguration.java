package com.customer.web.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:instructor-mysql.properties"})
public class PersistenceConfiguration {

    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = Dotenv.load();
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(dotenv.get("MYSQLDB_URL") + dotenv.get("MYSQLDB_HOST") + ":" + dotenv.get("MYSQLDB_LOCAL_PORT") + "/" + dotenv.get("MYSQLDB_DATABASE"));
        builder.username(dotenv.get("MYSQLDB_USER"));
        builder.password(dotenv.get("MYSQLDB_PASSWORD"));
        System.out.println("My custom datasource bean has been initialized and set");
        return builder.build();
    }

}
