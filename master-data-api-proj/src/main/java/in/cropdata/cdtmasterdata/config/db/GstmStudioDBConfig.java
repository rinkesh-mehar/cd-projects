package in.cropdata.cdtmasterdata.config.db;

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
 * GSTM Studio DB configuration.
 * 
 * @author PranaySK
 * @Date 28-07-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmStudioEntityManagerFactory", transactionManagerRef = "gstmStudioTransactionManager", basePackages = {
		"in.cropdata.cdtmasterdata.gstmstudio.repository" })
public class GstmStudioDBConfig {

	@Bean(name = "gstmStudioDataSource")
	@ConfigurationProperties(prefix = "gstmstudio.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate gstmStudioDataJdbcTemplate(@Qualifier("gstmStudioDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "gstmStudioEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean gstmStudioEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("gstmStudioDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("in.cropdata.cdtmasterdata.gstmstudio.model")
				.persistenceUnit("gstmstudio").build();
	}

	@Bean(name = "gstmStudioTransactionManager")
	public PlatformTransactionManager gstmStudioTransactionManager(
			@Qualifier("gstmStudioEntityManagerFactory") EntityManagerFactory gstmStudioEntityManagerFactory) {
		return new JpaTransactionManager(gstmStudioEntityManagerFactory);
	}
}
