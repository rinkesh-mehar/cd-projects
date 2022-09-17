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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         26-Nov-2019
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager", 
basePackages = { "in.cropdata.toolsuite.drk.dao.masterdata", 
	"in.cropdata.toolsuite.drk.repository.tileassignment" })
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
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
	return builder.dataSource(dataSource)
		.packages("in.cropdata.toolsuite.drk.model.masterdata", 
			"in.cropdata.toolsuite.drk.model.tileassignment")
		.persistenceUnit("master").build();
    }

    @Primary
    @Bean(name = "masterTransactionManager")
    public PlatformTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
	return new JpaTransactionManager(masterEntityManagerFactory);
    }
}
