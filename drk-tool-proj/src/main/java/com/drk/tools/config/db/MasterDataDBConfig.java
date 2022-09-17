package com.drk.tools.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Master Data DB configuration.
 *
 * @author PranaySK
 * @Date 03-09-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager", basePackages = {
		"com.drk.tools.repository" })
public class MasterDataDBConfig {

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public JdbcTemplate masterDataJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Primary
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.drk.tools.model").persistenceUnit("master").build();
	}

	@Primary
	@Bean(name = "masterTransactionManager")
	public PlatformTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
		return new JpaTransactionManager(masterEntityManagerFactory);
	}
}
