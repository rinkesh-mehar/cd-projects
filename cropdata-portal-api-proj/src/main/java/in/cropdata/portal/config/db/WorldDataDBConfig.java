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
 * @package in.cropdata.portal.config.db
 * @date 13/12/21
 * @time 5:15 PM
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "worldDataModelsEntityManagerFactory", transactionManagerRef = "worldDataTransactionManager",
        basePackages = {
                "in.cropdata.portal.worldData.repository" })
public class WorldDataDBConfig
{
    @Bean(name = "worldDataModelsDataSource")
    @ConfigurationProperties(prefix = "worlddatamodels.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate worldDataModelsDataJDBCTemplate(@Qualifier("worldDataModelsDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "worldDataModelsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                                         @Qualifier("worldDataModelsDataSource") DataSource dataSource){
        return builder.dataSource(dataSource).packages("in.cropdata.portal.worldData.model")
                .persistenceUnit("worldDataModels").build();
    }

    @Bean(name = "worldDataModelsTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("worldDataModelsEntityManagerFactory") EntityManagerFactory entityManagerFactory){
            return new JpaTransactionManager(entityManagerFactory);
    }
}
