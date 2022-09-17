/**
 * 
 */
package in.cropdata.cdtmasterdata.config.db;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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

/**
 * @author pallavi-waghmare
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "drkrishiEntityManagerFactory", transactionManagerRef = "drkrishiTransactionManager", basePackages = {
		"in.cropdata.cdtmasterdata.drkrishi.repository" })
public class DrkrishiDBConfig {
	
	@Bean(name = "drkrishiDataSource")
	@ConfigurationProperties(prefix = "drkrishi.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate drkrishiDataJdbcTemplate(@Qualifier("drkrishiDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "drkrishiEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean drkrishiEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("drkrishiDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("in.cropdata.cdtmasterdata.drkrishi.model")
				.persistenceUnit("drkrishi").build();
	}

	@Bean(name = "drkrishiTransactionManager")
	public PlatformTransactionManager drkrishiTransactionManager(
			@Qualifier("drkrishiEntityManagerFactory") EntityManagerFactory drkrishiEntityManagerFactory) {
		return new JpaTransactionManager(drkrishiEntityManagerFactory);
	}
	
}
