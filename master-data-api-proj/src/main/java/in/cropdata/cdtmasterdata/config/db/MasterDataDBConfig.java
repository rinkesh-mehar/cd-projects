package in.cropdata.cdtmasterdata.config.db;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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

/**
 * Master Data DB configuration.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager", basePackages = {
		"in.cropdata.cdtmasterdata.acl.repository", "in.cropdata.cdtmasterdata.datavalidation.dao",
		"in.cropdata.cdtmasterdata.region.dao", "in.cropdata.cdtmasterdata.repository" })
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
		return builder.dataSource(dataSource)
				.packages("in.cropdata.cdtmasterdata.acl.model", "in.cropdata.cdtmasterdata.acl.model",
						"in.cropdata.cdtmasterdata.model", "in.cropdata.cdtmasterdata.region.model")
				.persistenceUnit("master").build();
	}

	@Primary
	@Bean(name = "masterTransactionManager")
	public PlatformTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
		return new JpaTransactionManager(masterEntityManagerFactory);
	}
}
