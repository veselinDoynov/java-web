package web.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:instructor-mysql.properties"})
@EnableJpaRepositories(
        basePackages = "web.repositories.version",
        entityManagerFactoryRef = "versionEntityManager",
        transactionManagerRef = "versionTransactionManager"
)
public class VersionPersistenceConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean versionEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(versionDataSource());
        em.setPackagesToScan(
                new String[] { "web.entity.version" });

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
    public DataSource versionDataSource() {
        Dotenv dotenv = Dotenv.load();
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(dotenv.get("MYSQLDB_URL") + dotenv.get("MYSQLDB_HOST") + ":" + dotenv.get("MYSQLDB_LOCAL_PORT") + "/" + dotenv.get("MYSQLDB_DATABASE_VERSION"));
        builder.username(dotenv.get("MYSQLDB_ROOT_USER"));
        builder.password(dotenv.get("MYSQLDB_PASSWORD"));
        System.out.println("Version database connection started...");
        return builder.build();
    }

    @Bean
    public PlatformTransactionManager versionTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                versionEntityManager().getObject());
        return transactionManager;
    }
}
