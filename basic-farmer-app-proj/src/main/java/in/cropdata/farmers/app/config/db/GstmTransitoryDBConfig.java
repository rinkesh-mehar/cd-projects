package in.cropdata.farmers.app.config.db;

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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Gstm-Transitory DB configuration.
 *
 * @author Rinkesh Mehar
 * @Date 22-07-2020
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "gstmTransitoryManagerFactory", transactionManagerRef = "gstmTransitoryTransactionManager", basePackages = {
        "in.cropdata.farmers.app.gstmTransitory.repository" })
public class GstmTransitoryDBConfig {
    @Bean(name = "gstmTransitoryDataSource")
    @ConfigurationProperties(prefix = "gstmtransitory.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

	@Bean(name = "gstmTransitoryJdbcTemplate")
	public JdbcTemplate gstmTransitoryJdbcTemplate(@Qualifier("gstmTransitoryDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

    @Bean(name = "gstmTransitoryManagerFactory")
    public LocalContainerEntityManagerFactoryBean gstmTransitoryManagerFactory(EntityManagerFactoryBuilder builder,
                                                                               @Qualifier("gstmTransitoryDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("in.cropdata.farmers.app.gstmTransitory.entity").persistenceUnit("gstmTransitory")
                .build();
    }

    @Bean(name = "gstmTransitoryTransactionManager")
    public PlatformTransactionManager gstmTransitoryTransactionManager(
            @Qualifier("gstmTransitoryManagerFactory") EntityManagerFactory gstmTransitoryManagerFactory) {
        return new JpaTransactionManager(gstmTransitoryManagerFactory);
    }
}
