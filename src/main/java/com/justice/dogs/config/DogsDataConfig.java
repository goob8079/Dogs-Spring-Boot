package com.justice.dogs.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.justice.dogs.holder",
    entityManagerFactoryRef = "dogsEntityManager",
    transactionManagerRef = "dogsTransactionManager"
)
public class DogsDataConfig {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.dog")
    public DataSource dogsDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean dogsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dogsDataSource());
        em.setPackagesToScan(new String[] { "com.justice.dogs.holder" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("jakarta.persistence.jdbc.url", env.getProperty("spring.datasource.dog.url"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    // this sets the naming conventions for the application.properties settings.
    // so, in this case, the settings are kept as their default names to make it easier.
    // @Bean
    // @Primary
    // public DataSource dogsDataSource() {
    //     DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //     dataSource.setDriverClassName(env.getProperty("spring.datasource.dog.driver-class-name"));
    //     dataSource.setUrl(env.getProperty("spring.datasource.dog.url"));
    //     dataSource.setUsername(env.getProperty("spring.datasource.dog.username"));
    //     dataSource.setPassword(env.getProperty("spring.datasource.dog.password"));

    //     return dataSource;
    // }

    @Bean
    @Primary
    public PlatformTransactionManager dogsTransactionManager() {
 
        JpaTransactionManager dogsTransactionManager = new JpaTransactionManager();
        dogsTransactionManager.setEntityManagerFactory(dogsEntityManager().getObject());
        return dogsTransactionManager;
    }
}