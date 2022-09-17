package in.cropdata.farmers.app.drk.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.drk.model.FarmerDeviceToken;

@Repository
public interface FarmerDeviceTokenRepository extends JpaRepository<FarmerDeviceToken, Integer> {

	@Query(value = "SELECT * FROM drkrishi_ts.farmer_device_tokens FK where FK.FarmerID = ?1 and FK.DeviceToken =?2", nativeQuery = true)
	Optional<FarmerDeviceToken> checkExistingDeviceToken(String farmerId, String deviceToken);
	
	Optional<List<FarmerDeviceToken>> findByFarmerID(String farmerID);

	Optional<FarmerDeviceToken> findByDeviceToken(String deviceToken);

	@Modifying
	@Transactional
	Integer deleteByFarmerID(String farmerID);

	@Query(value = "SELECT fdt.DeviceToken from farmer_device_tokens fdt where fdt.FarmerID = ?1", nativeQuery = true)
	List<String> findFarmerDeviceTokenByFarmerID(String farmerId);
	
	@Modifying
    @Transactional
	@Query(value="update drkrishi_ts.farmer_device_tokens set status = 'Tracking' where DeviceToken = ?1",nativeQuery = true)
	int updateStatusAsTracking(String deviceToken);
	

	@Query(value = "select count(rights.right_id) from logistic_hub.rights rights where rights.right_id = ?1 and right_status = ?1", nativeQuery = true)
	Integer isRightUntracable(String rightId, Integer statusId);
	
	@Modifying
    @Transactional
	@Query(value="update logistic_hub.rights r set r.right_status = 26 where r.right_id = ?1",nativeQuery = true)
	int updateRightAsUntracable(String rightId);

}
