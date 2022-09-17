package com.drk.tools.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * DrKrishi DB configuration.
 *
 * @author Rinkesh Mehar
 * @Date 08-09-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "drkrishiEntityManagerFactory", transactionManagerRef = "drkTransactionManager", basePackages = {
		"com.drk.tools.drkrishi.repository"})
public class DrKrishiDBConfig {

	@Bean(name = "DrkDataSource")
	@ConfigurationProperties(prefix = "drkrishi.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate drkrishiJdbcTemplate(@Qualifier("DrkDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "drkrishiEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean drkrishiEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("DrkDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
				.packages("com.drk.tools.drkrishi.model")
				.persistenceUnit("drk").build();
	}

	@Bean(name = "drkTransactionManager")
	public PlatformTransactionManager drkTransactionManager(
			@Qualifier("drkrishiEntityManagerFactory") EntityManagerFactory drkrishiEntityManagerFactory) {
		return new JpaTransactionManager(drkrishiEntityManagerFactory);
	}
}
