package com.justice.dogs.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
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
    public LocalContainerEntityManagerFactoryBean dogsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dogsDataSource());
        em.setPackagesToScan(new String[] {"com.justice.dogs.holder"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    // required to put down information for the dogsDb database,
    // especially when Docker Compose is disabled
    @Bean
    @Primary
    public DataSource dogsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("dogs.jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager dogsTransactionManager() {
 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dogsEntityManager().getObject());
        return transactionManager;
    }
}
