package in.cropdata.portal.config.db;

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
 * Web-site DB configuration.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "websiteEntityManagerFactory", transactionManagerRef = "websiteTransactionManager", basePackages = {
		"in.cropdata.portal.repository" })
public class WebsiteDBConfig
{

	@Primary
	@Bean(name = "websiteDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public JdbcTemplate websiteDataJdbcTemplate(@Qualifier("websiteDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Primary
	@Bean(name = "websiteEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean websiteEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("websiteDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("in.cropdata.portal.model")
				.persistenceUnit("website").build();
	}

	@Primary
	@Bean(name = "websiteTransactionManager")
	public PlatformTransactionManager websiteTransactionManager(
			@Qualifier("websiteEntityManagerFactory") EntityManagerFactory websiteEntityManagerFactory) {
		return new JpaTransactionManager(websiteEntityManagerFactory);
	}
}
