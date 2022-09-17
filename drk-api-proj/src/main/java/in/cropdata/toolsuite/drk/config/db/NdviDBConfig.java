/**
 * 
 */
package in.cropdata.toolsuite.drk.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         25-Nov-2019
 */
@Configuration
//@EnableTransactionManagement 
@EnableJpaRepositories(basePackages = { "in.cropdata.toolsuite.drk.dao.ndvi" })//entityManagerFactoryRef = "ndviEntityManagerFactory", transactionManagerRef = "ndviTransactionManager",
public class NdviDBConfig {

    @Bean(name = "ndviDataSource")
    @ConfigurationProperties(prefix = "spring.gstm.ndvi.datasource")
    public DataSource ndviDataSource() {
	return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate gstmNdviJdbcTemplate(@Qualifier("ndviDataSource") DataSource ndviDataSource) {
	return new JdbcTemplate(ndviDataSource);
    }

//    @Bean(name = "ndviEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("ndviDataSource") DataSource ndviDataSource) {
//	return builder.dataSource(ndviDataSource).packages("").persistenceUnit("gstm-ndvi").build();
//    }
//
//    @Bean(name = "ndviTransactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("ndviEntityManagerFactory") EntityManagerFactory ndviEntityManagerFactory) {
//	return new JpaTransactionManager(ndviEntityManagerFactory);
//    }

}
