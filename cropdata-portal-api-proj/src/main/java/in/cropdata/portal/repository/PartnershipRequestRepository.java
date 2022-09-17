package in.cropdata.portal.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.portal.model.PartnershipRequest;

/**
 * @author cropdata-kunal
 *
 */
public interface PartnershipRequestRepository extends JpaRepository<PartnershipRequest, Integer> {

	@Query(value = "SELECT ID,Name FROM cdt_master_data.agm_industries order by ID", nativeQuery = true)
	CopyOnWriteArrayList<Map<String, Object>> getAllIndustries();

	@Query(value = "SELECT ID,Name FROM cdt_master_data.agm_partner_programs  order by ID", nativeQuery = true)
	List<Map<String, Object>> getPartnershipProgram();

}
