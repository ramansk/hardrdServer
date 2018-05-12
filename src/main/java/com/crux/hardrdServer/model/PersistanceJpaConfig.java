package com.crux.hardrdServer.model;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class PersistanceJpaConfig {

	@Bean
	public LocalSessionFactoryBean hibernateSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(new String[] { "com.crux.hardrdServer.model" });
		sessionFactory.setHibernateProperties(additionalProperties());

		return sessionFactory;
	}

	@Bean
	HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);

		return transactionManager;
	}

	@Bean
	public DataSource dataSource() {		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/userdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("admin");

		return dataSource;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "false");
		properties.setProperty("hibernate.use_sql_comments", "false");
		
	//	properties.setProperty("hibernate.cache.use_second_level_cache", "false");
	//	properties.setProperty("hibernate.cache.use_query_cache", "false");
		
	//	properties.setProperty("hibernate.cache.use_query_cache", "false");
	//	properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
	//	properties.setProperty("net.sf.ehcache.configurationResourceName", "/ehCache.xml");
		//<prop key="hibernate.cache.use_second_level_cache">true</prop>
		//<prop key="hibernate.cache.use_query_cache">true</prop>
		//<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
		//<prop key="net.sf.ehcache.configurationResourceName">/ehCache.xml</prop>


		return properties;
	}

}