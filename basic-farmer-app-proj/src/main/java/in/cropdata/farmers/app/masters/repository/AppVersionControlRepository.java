/**
 * 
 */
package in.cropdata.farmers.app.masters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.masters.model.AppVersionControl;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface AppVersionControlRepository extends JpaRepository<AppVersionControl, Integer> {
	
	@Query(value = "SELECT AppKey FROM cdt_master_data.app_version_control where AppName = 'BasicFarmerApp'", nativeQuery = true)
	String getAppKey(String appKey);
}
