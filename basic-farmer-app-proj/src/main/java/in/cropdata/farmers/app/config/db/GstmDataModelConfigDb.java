/**
 * 
 */
package in.cropdata.farmers.app.config.db;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmDataModelEntityManagerFactory", transactionManagerRef = "gstmDataModelTransactionManager", basePackages = {
		"in.cropdata.farmers.app.gstm.repository" })
public class GstmDataModelConfigDb {

	@Bean(name = "gstmDataSource")
	@ConfigurationProperties(prefix = "gstm.datasource")
	public DataSource gstmDataSource() {

		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "gstmJdbcTemplate")
	public JdbcTemplate gstmJdbcTemplate(@Qualifier(value = "gstmDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "gstmDataModelEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean gstmDataModelEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(value = "gstmDataSource") DataSource dataSource) {

		return builder.dataSource(dataSource).packages("in.cropdata.farmers.app.gstm.model").persistenceUnit("gstm.datasource")
				.build();
	}

	@Bean(name = "gstmDataModelTransactionManager")
	public JpaTransactionManager gstmDataModelTransactionManager(
			@Qualifier(value = "gstmDataModelEntityManagerFactory") EntityManagerFactory gstmDataModelTransactionManager) {
		return new JpaTransactionManager(gstmDataModelTransactionManager);
	}

}
