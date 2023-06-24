package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:h2.properties"})
@EnableJpaRepositories(
        basePackages = "com.example.demo.repo.h2",
        entityManagerFactoryRef = "h2EntityManager",
        transactionManagerRef = "h2TransactionManager")
@EntityScan("com.example.demo.entity.h2")
public class PersistenceH2AutoConfiguration {

    @Autowired
    private Environment env;


   @Bean
    @ConfigurationProperties(prefix="spring.h2-datasource")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }

   /* @Bean
    public DataSource h2DataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("spring.h2-datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.h2-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.h2-datasource.username"));
        dataSource.setPassword(env.getProperty("spring.h2-datasource.password"));

        return dataSource;
    }*/

    // h2EntityManager model
    @Bean
    public LocalContainerEntityManagerFactoryBean h2EntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(h2DataSource());
        em.setPackagesToScan(
                new String[] { "com.example.demo.entity.h2" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("spring.jpa.properties.h2.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    // h2TransactionManager model

    @Bean
    public PlatformTransactionManager h2TransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                h2EntityManager().getObject());
        return transactionManager;
    }
}