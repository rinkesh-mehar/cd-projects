package in.cropdata.gstm.studio.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * GSTM Data Studio DB configuration.
 * 
 * @since 1.0
 * @author PranaySK
 * @Date 01-08-2020
 */

@Configuration
public class GstmDataDBConfig {

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "gstmDataJdbcTemplate")
	public JdbcTemplate gstmDataJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "gstmNamedJdbcTemplate")
	public NamedParameterJdbcTemplate gstmNamedJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
