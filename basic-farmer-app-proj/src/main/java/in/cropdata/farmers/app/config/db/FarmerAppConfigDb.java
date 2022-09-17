package in.cropdata.farmers.app.config.db;



/**
 * @author cropdata-Aniket Naik
 *
 */

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "farmersAppManagerFactory", 
//                       transactionManagerRef = "farmersAppTransactionManager", 
//                       basePackages = {"in.cropdata.farmers.app.repository" })
public class FarmerAppConfigDb {

//	
//	@Bean(name = "farmersAppDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//
//		return DataSourceBuilder.create().build();
//	}
//
//	@Bean(name = "farmersAppJdbcTemplate")
//	public JdbcTemplate farmersAppJdbcTemplate(@Qualifier("farmersAppDataSource") DataSource dataSource) {
//
//		return new JdbcTemplate(dataSource);
//	}
//
//	
//	@Bean(name = "farmersAppManagerFactory")
//	public LocalContainerEntityManagerFactoryBean farmersAppManagerFactory(EntityManagerFactoryBuilder builder,
//			@Qualifier("farmersAppDataSource") DataSource dataSource) {
//
//		return builder.dataSource(dataSource)
//				      .packages("in.cropdata.farmers.app.model")
//				      .persistenceUnit("farmersApp")
//				      .build();
//	}
//
//	
//	@Bean(name = "farmersAppTransactionManager")
//	public PlatformTransactionManager farmersAppTransactionManager(
//			@Qualifier("farmersAppManagerFactory") EntityManagerFactory farmersEntityManagerFactory) {
//
//		return new JpaTransactionManager(farmersEntityManagerFactory);
//	}

}
