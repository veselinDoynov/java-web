package com.customer.web.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@PropertySource({"classpath:instructor-mysql.properties"})
@Configuration
@EnableJpaRepositories(basePackages = {
        "org.baeldung.repository",
        "org.baeldung.boot.repository"
})
@EnableTransactionManagement
public class TestPersistenceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean testEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(testDataSource());
        em.setPackagesToScan(
                new String[] { "com.customer.web.entity.web" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        Dotenv dotenv = Dotenv.load();
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(dotenv.get("MYSQLDB_URL") + dotenv.get("MYSQLDB_HOST") + ":" + dotenv.get("MYSQLDB_LOCAL_PORT") + "/" + dotenv.get("MYSQLDB_DATABASE_TEST"));
        builder.username(dotenv.get("MYSQLDB_USER"));
        builder.password(dotenv.get("MYSQLDB_PASSWORD"));
        System.out.println("My custom datasource bean has been initialized and set");
        return builder.build();
    }

    @Bean
    public PlatformTransactionManager testTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                testEntityManager().getObject());
        return transactionManager;
    }
}
