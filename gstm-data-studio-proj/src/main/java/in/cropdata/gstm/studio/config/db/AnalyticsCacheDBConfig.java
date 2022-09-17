package in.cropdata.gstm.studio.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Analytics Cache DB configuration.
 * 
 * @since 1.0
 * @author PranaySK
 * @Date 01-08-2020
 */

@Configuration
public class AnalyticsCacheDBConfig {

	@Bean(name = "analyticsDataSource")
	@ConfigurationProperties(prefix = "analytics.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "analyticsDataJdbcTemplate")
	public JdbcTemplate analyticsDataJdbcTemplate(@Qualifier("analyticsDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
