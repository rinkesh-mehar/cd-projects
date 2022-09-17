package in.cropdata.portal.config.db;

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
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.config.db
 * @date 30/07/20
 * @time 12:24 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmDataModelsEntityManagerFactory", transactionManagerRef = "gstmDataModelsTransactionManager", basePackages = {
       "in.cropdata.portal.gstmDataModels.repository" })
public class GstmDataModelsConfig
{

    @Bean(name = "gstmDataModelsDataSource")
    @ConfigurationProperties(prefix = "gstmdatamodels.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate gstmDataModelsDataJdbcTemplate(@Qualifier("gstmDataModelsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "gstmDataModelsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean magstmDataModelsEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                             @Qualifier("gstmDataModelsDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("in.cropdata.portal.gstmDataModels.model")
                .persistenceUnit("gstmDataModels").build();
    }


    @Bean(name = "gstmDataModelsTransactionManager")
    public PlatformTransactionManager gstmDataModelsTransactionManager(
            @Qualifier("gstmDataModelsEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
        return new JpaTransactionManager(masterEntityManagerFactory);
    }
}
