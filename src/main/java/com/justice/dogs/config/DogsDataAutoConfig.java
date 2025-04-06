package com.justice.dogs.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Configuration
@PropertySource({ "classpath:application.yml" })
@EnableJpaRepositories(
    basePackages = "com.justice.dogs.dogsHolder",
    entityManagerFactoryRef = "dogsEntityManager",
    transactionManagerRef = "dogsTransactionManager"
)
public class DogsDataAutoConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix="dogs.datasource")
    public DataSource dogsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dogsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dogsDataSource());
        em.setPackagesToScan(new String[] { "com.justice.dogs.dogsHolder" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.connection.url", env.getProperty("dogs.datasource.url"));
        properties.put("hibernate.connection.username", env.getProperty("dogs.datasource.username"));
        properties.put("hibernate.connection.password", env.getProperty("dogs.datasource.password"));
        properties.put("hibernate.connection.driver_class", env.getProperty("dogs.datasource.driver-class-name"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager dogsTransactionManager() {
        JpaTransactionManager dogsTransactionManager = new JpaTransactionManager();
        dogsTransactionManager.setEntityManagerFactory(dogsEntityManager().getObject());
        return dogsTransactionManager;
    }
}