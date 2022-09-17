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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "masterManagerFactory", 
                       transactionManagerRef = "masterTransactionManager", 
                       basePackages = {"in.cropdata.farmers.app.masters.repository"})
public class MasterAppConfigDb {

	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "cdt-master.datasource")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	
	@Bean(name="masterDataJdbcTemplate")
	public JdbcTemplate masterJdbcTemplate
	                             (@Qualifier(value = "masterDataSource") 
	                               DataSource dataSource) {
		return new JdbcTemplate(dataSource);                          
	}
	
//	@Bean(name="masterDataSimpleJdbcCallTemplate")
//	public SimpleJdbcCall masterSimpleJdbcCallTemplate
//	                             (@Qualifier(value = "masterDataSource") 
//	                               DataSource dataSource) {
//		return new SimpleJdbcCall(dataSource);                          
//	}
	
	@Bean(name = "masterManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			                                                 @Qualifier(value = "masterDataSource") DataSource dataSource) {
		
		return builder.dataSource(dataSource)
				       .packages("in.cropdata.farmers.app.masters.model")
				       .persistenceUnit("masters")
				       .build();
	}
	
	
	
	@Bean(name = "masterTransactionManager")
	public JpaTransactionManager masterTransactionManager(@Qualifier(value = "masterManagerFactory") 
	                                                      EntityManagerFactory masterTransactionManager) {	
		return new JpaTransactionManager(masterTransactionManager);
	}

}
