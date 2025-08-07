package com.example.data.config;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

/**
 * JPA Configuration class that provides database connectivity beans and related configurations.
 * This class is responsible for setting up the database connection and related JPA components for
 * the application.
 *
 * @author Garik Arutyunyan
 * @version 1.0
 * @since 1.0
 */
@Configuration
@PropertySource("classpath:db-${env:local}.properties")
@EnableJpaRepositories(basePackages = "com.example.data.repository")
public class JPAConfig {
  /** Default constructor for JdbcConfig. Initializes a new instance of the JPA configuration. */
  JPAConfig() {}

  /**
   * The name of the database driver class to be used for establishing a database connection.
   * Populated from the external property "db.driver" defined in the application properties file.
   * This property is mandatory for setting up the JPA DataSource.
   */
  @Value("${db.driver}")
  private String driver;

  /**
   * The URL of the database to be used for establishing a connection. Retrieved from the external
   * property "db.url" defined in the application properties file. This property is essential for
   * configuring the DataSource to identify the database location.
   */
  @Value("${db.url}")
  private String url;

  /**
   * The username used to authenticate the connection to the database. This value is injected from
   * the external property "db.username" defined in the application properties file. It is a
   * required property for establishing database connections.
   */
  @Value("${db.username}")
  private String username;

  /**
   * The password used to authenticate the connection to the database. This value is injected from
   * the external property "db.password" defined in the application properties file. It is a
   * required property for establishing database connections.
   */
  @Value("${db.password}")
  private String password;

  /**
   * Configures and provides a DataSource bean for database connectivity. This method uses the
   * DriverManagerDataSource implementation and sets the driver class name, database URL, username,
   * and password from external properties such as db.driver, db.url, db.username, and db.password.
   *
   * @return a configured DataSource instance for database connectivity
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }

  /**
   * Creates and configures the EntityManagerFactory for JPA.
   *
   * @param dataSource the DataSource to be used
   * @return configured LocalContainerEntityManagerFactoryBean
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Flyway flyway) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.example.data.entity");

    Properties properties = new Properties();
    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.format_sql", "true");
    properties.put("hibernate.connection.autocommit", "false");
    properties.put("hibernate.connection.release_mode", "after_transaction");
    properties.put("hibernate.connection.driver_class", "org.h2.Driver");
    properties.put("hibernate.connection.url", "jdbc:h2:mem:testdb");
    properties.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform");


    em.setJpaProperties(properties);
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    flyway.migrate();
    return em;
  }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .validateOnMigrate(true)
                .schemas("company")
                .load();
    }

  /**
   * Configures and provides a JpaTransactionManager bean to enable declarative transaction
   * management in the application. The transaction manager is responsible for coordinating
   * transactions with the underlying database through JPA.
   *
   * @param emf the EntityManagerFactory to be managed for transactional support
   * @return a configured instance of JpaTransactionManager
   */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
