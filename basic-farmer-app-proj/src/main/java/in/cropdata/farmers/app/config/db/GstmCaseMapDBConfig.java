package in.cropdata.farmers.app.config.db;

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
 * Gstm-Case-Map DB configuration.
 *
 * @author Rinkesh Mehar
 * @Date 23-03-2021
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmCaseManagerFactory", transactionManagerRef = "gstmCaseTransactionManager", basePackages = {
        "in.cropdata.farmers.app.gstmCaseMap.repository"})
public class GstmCaseMapDBConfig {

    @Bean(name = "gstmCaseDataSource")
    @ConfigurationProperties(prefix = "gstmcase.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
//
//    @Bean(name = "gstmCaseJdbcTemplate")
//    public JdbcTemplate gstmCaseJdbcTemplate(@Qualifier(value = "gstmCaseDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

    @Bean(name = "gstmCaseManagerFactory")
    public LocalContainerEntityManagerFactoryBean gstmTransitoryManagerFactory(EntityManagerFactoryBuilder builder,
                                                                               @Qualifier("gstmCaseDataSource") DataSource dataSource)
    {
        return builder.dataSource(dataSource).packages("in.cropdata.farmers.app.gstmCaseMap.entity").persistenceUnit("gstmCase")
                .build();
    }

    @Bean(name = "gstmCaseTransactionManager")
    public PlatformTransactionManager gstmCaseTransactionManager(
            @Qualifier("gstmCaseManagerFactory") EntityManagerFactory gstmTransitoryManagerFactory)
    {
        return new JpaTransactionManager(gstmTransitoryManagerFactory);
    }
}
