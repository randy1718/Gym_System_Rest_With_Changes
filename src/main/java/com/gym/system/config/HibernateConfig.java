package com.gym.system.config;

import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.gym.system")
@EnableTransactionManagement
public class HibernateConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/gym_system_rest");
        ds.setUsername("postgres");
        ds.setPassword("Randymiller123");
        return (DataSource) ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.gym.system.model");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        emf.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);

        Properties props = new Properties();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update");

        emf.setJpaProperties(props);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    /*
     * @Bean
     * public LocalSessionFactoryBean sessionFactory() {
     * LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
     * factory.setDataSource(dataSource());
     * factory.setPackagesToScan("com.gym.system.model");
     * 
     * Properties props = new Properties();
     * props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
     * props.put("hibernate.show_sql", "true");
     * props.put("hibernate.hbm2ddl.auto", "update");
     * 
     * factory.setHibernateProperties(props);
     * return factory;
     * }
     * 
     * @Bean
     * public HibernateTransactionManager transactionManager(
     * SessionFactory sessionFactory) {
     * return new HibernateTransactionManager(sessionFactory);
     * }
     */
}
