package in.cropdata.toolsuite.drk.config.db;

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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "lhEntityManagerFactory", transactionManagerRef = "lhTransactionManager",
        basePackages = { "in.cropdata.toolsuite.drk.dao.lh" })

public class LHDBConfig {
    @Bean(name = "lhDataSource")
    @ConfigurationProperties(prefix = "spring.lh.datasource")
    public DataSource lhDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate lhJdbcTemplate(@Qualifier("lhDataSource") DataSource ndviDataSource) {
        return new JdbcTemplate(ndviDataSource);
    }

    @Bean(name = "lhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("lhDataSource") DataSource lhDataSource) {
        return builder.dataSource(lhDataSource).
                packages("in.cropdata.toolsuite.drk.model.lh",
                        "in.cropdata.toolsuite.drk.model.pr",
                        "in.cropdata.toolsuite.drk.model.gt")
                .persistenceUnit("lh-kml").build();
    }

    @Bean(name = "lhTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("lhEntityManagerFactory") EntityManagerFactory lhEntityManagerFactory) {
        return new JpaTransactionManager(lhEntityManagerFactory);
    }
}
