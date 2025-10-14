package com.justice.dogs.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.justice.dogs.user",
    entityManagerFactoryRef = "usersEntityManagerFactory",
    transactionManagerRef = "usersTransactionManager"
)
public class UserDataAutoConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "users.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    } 

    @Primary
    @Bean(name = "usersEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan("com.justice.dogs.user");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.connection.url", env.getProperty("users.datasource.url"));
        properties.put("hibernate.connection.username", env.getProperty("users.datasource.username"));
        properties.put("hibernate.connection.password", env.getProperty("users.datasource.password"));
        properties.put("hibernate.connection.driver_class", env.getProperty("users.datasource.driver-class-name"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "usersTransactionManager")
    public PlatformTransactionManager usersTransactionManager(@Qualifier("usersEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}