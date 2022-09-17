/**
 * 
 */
package in.cropdata.toolsuite.drk.config.db;

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
 * @author Vivek Gajbhiye - Cropdata
 *
 *         25-Nov-2019
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmEntityManagerFactory", transactionManagerRef = "gstmTransactionManager", 
basePackages = { "in.cropdata.toolsuite.drk.dao.cases", 
	"in.cropdata.toolsuite.drk.dao.pr", 
	"in.cropdata.toolsuite.drk.dao.gt",
	"in.cropdata.toolsuite.drk.repository.fl"})
public class GSTMDBConfig {

    @Bean(name = "gstmDataSource")
    @ConfigurationProperties(prefix = "gstm.kml.datasource")
    public DataSource gstmDataSource() {
	return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate gstmJdbcTemplate(@Qualifier("gstmDataSource") DataSource gstmDataSource) {
	return new JdbcTemplate(gstmDataSource);
    }

    @Bean(name = "gstmEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("gstmDataSource") DataSource gstmDataSource) {
	return builder.dataSource(gstmDataSource).
		packages("in.cropdata.toolsuite.drk.model.cases", 
			"in.cropdata.toolsuite.drk.model.pr", 
			"in.cropdata.toolsuite.drk.model.gt")
		.persistenceUnit("gstm-kml").build();
    }

    @Bean(name = "gstmTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("gstmEntityManagerFactory") EntityManagerFactory gstmEntityManagerFactory) {
	return new JpaTransactionManager(gstmEntityManagerFactory);
    }
}
