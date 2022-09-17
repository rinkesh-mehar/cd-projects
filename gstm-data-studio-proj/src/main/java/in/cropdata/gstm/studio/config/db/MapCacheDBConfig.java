package in.cropdata.gstm.studio.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Map Cache DB configuration.
 * 
 * @since 1.0
 * @author PranaySK
 * @Date 01-08-2020
 */

@Configuration
public class MapCacheDBConfig {

	@Primary
	@Bean(name = "mapDataSource")
	@ConfigurationProperties(prefix = "map.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "mapDataJdbcTemplate")
	public JdbcTemplate mapDataJdbcTemplate(@Qualifier("mapDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Primary
	@Bean(name = "mapNamedJdbcTemplate")
	public NamedParameterJdbcTemplate mapNamedJdbcTemplate(@Qualifier("mapDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
