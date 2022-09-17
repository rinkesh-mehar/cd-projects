package in.cropdata.farmers.app.config.db;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "drkManagerFactory",
		transactionManagerRef = "drkTransactionManager",
		basePackages = {"in.cropdata.farmers.app.drk.repository"})
public class DrKrishiConfigDb {
	
	@Primary
	@Bean(name = "drkDataSource")
	@ConfigurationProperties(prefix = "drkrishi.datasource")
	public DataSource drkDataSource() {
		return DataSourceBuilder.create().build();
	}
	

	@Primary
	@Bean(name="drkrishiDataJdbcTemplate")
	public JdbcTemplate drkJdbcTemplate
	                             (@Qualifier(value = "drkDataSource") 
	                               DataSource dataSource) {
		return new JdbcTemplate(dataSource);                          
	}
	
	@Primary
	@Bean(name = "drkManagerFactory")
	public LocalContainerEntityManagerFactoryBean drkEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			                                                 @Qualifier(value = "drkDataSource") DataSource dataSource) {
		Map<String,Object> properties = new HashMap<>();
		properties.put("javax.persistence.query.timeout", 15000); // update image took 10 sec to execute and its transactional
		properties.put("javax.persistence.lock.timeout", 7000);  
		return builder.dataSource(dataSource)
				       .packages("in.cropdata.farmers.app.drk.model")
				       .persistenceUnit("dr-krishi")
				       .properties(properties)
				       .build();
	}
	
	
	@Primary
	@Bean(name = "drkTransactionManager")
	public JpaTransactionManager drkTransactionManager(@Qualifier(value = "drkManagerFactory") 
	                                                      EntityManagerFactory drkTransactionManager) {	
		return new JpaTransactionManager(drkTransactionManager);
	}
	

}
