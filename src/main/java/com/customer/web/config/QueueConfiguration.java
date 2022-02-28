package com.customer.web.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.sql.common.DefaultSqlStorageProvider;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.jobrunr.storage.sql.mariadb.MariaDbStorageProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


@Configuration
@PropertySource({"classpath:queue.properties"})
public class QueueConfiguration {

    @Bean
    public DataSource queueDataSource() {
        Dotenv dotenv = Dotenv.load();
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(dotenv.get("MYSQLDB_URL") + dotenv.get("MYSQLDB_HOST") + ":" + dotenv.get("MYSQLDB_LOCAL_PORT") + "/" + dotenv.get("MYSQLDB_DATABASE"));
        builder.username(dotenv.get("MYSQLDB_USER"));
        builder.password(dotenv.get("MYSQLDB_PASSWORD"));
        return builder.build();
    }

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {

        MariaDbStorageProvider storageProvider = new MariaDbStorageProvider(queueDataSource());
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }
}
