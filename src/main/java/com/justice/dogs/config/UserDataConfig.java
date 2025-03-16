package com.justice.dogs.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
    basePackages = "com.justice.dogs.users",
    entityManagerFactoryRef = "userEntityManager",
    transactionManagerRef = "userTransactionManager"
)
public class UserDataConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan(new String[] { "com.justice.dogs.users" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource userDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.user.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.user.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.user.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.user.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager dogsTransactionManager() {
 
        JpaTransactionManager dogsTransactionManager = new JpaTransactionManager();
        dogsTransactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return dogsTransactionManager;
    }
}