package com.ssc.demo;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.google.common.base.Predicates;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ComponentScan(basePackages= {"com.ssc.controller","com.ssc.service", "com.ssc.facade", "com.ssc.demo.error.handler", "com.ssc.model"})
@EntityScan("com.ssc.model")
@EnableJpaRepositories(value= {"com.ssc.jpa.repository", "com.ssc.model"})
@PropertySource("classpath:application.properties")
public class ContactPersistenceContext {

	 @Autowired
	 private Environment env;

	 @Bean(destroyMethod = "close")
	 DataSource dataSource(Environment env) {
	     HikariConfig dataSourceConfig = new HikariConfig();
	     dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
	     dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
	     dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
	     dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));
	
	     return new HikariDataSource(dataSourceConfig);
	 }

     @Bean
     LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, 
                                                                 Environment env) {
         LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
         entityManagerFactoryBean.setDataSource(dataSource);
         entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
         entityManagerFactoryBean.setPackagesToScan("com.ssc.model");
  
         Properties jpaProperties = new Properties();
      
         jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
         jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));	
         jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));
         jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));	        
         jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
  
         entityManagerFactoryBean.setJpaProperties(jpaProperties);
  
         return entityManagerFactoryBean;
     }

     
     @Bean
     JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
         JpaTransactionManager transactionManager = new JpaTransactionManager();
         transactionManager.setEntityManagerFactory(entityManagerFactory);
         return transactionManager;
     }
     
     @Bean
     public Docket api() { 
         return new Docket(DocumentationType.SWAGGER_2)  
           .select()                                  
           .apis(RequestHandlerSelectors.any())              
           .paths(Predicates.not(PathSelectors.regex("/error.*")))
           .build();                                           
     }
     

 	@Bean
 	public ModelMapper modelMapper() {
 	    return new ModelMapper();
 	}
}
