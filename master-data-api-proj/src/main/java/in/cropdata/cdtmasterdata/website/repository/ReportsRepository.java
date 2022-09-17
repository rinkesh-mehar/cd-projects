package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.ReportsDto;
import in.cropdata.cdtmasterdata.website.model.Reports;

/**
 * Repository class for interaction with database.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer> {

	@Query(value = "SELECT \n" + 
			"    reports.ID,\n" + 
			"    UPPER(platform_master.Name) AS Platform,\n" + 
			"    UPPER(reports.Title) AS Title,\n" + 
			"    reports.FileUrl,\n" + 
			"    UPPER(reports.Authenticate) AS Authenticate,\n" + 
			"    reports.Status\n" + 
			"FROM\n" + 
			"    reports reports\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master platform_master ON (reports.PlatformID = platform_master.ID)\n" + 
			"WHERE\n" + 
			"    platform_master.Name LIKE :searchText\n" + 
			"        OR reports.Title LIKE :searchText\n" + 
			"        OR reports.Authenticate LIKE :searchText\n" + 
			"        OR reports.Status LIKE :searchText", countQuery = "SELECT \n" + 
					"    reports.ID,\n" + 
					"    UPPER(platform_master.Name) AS Platform,\n" + 
					"    UPPER(reports.Title) AS Title,\n" + 
					"    reports.FileUrl,\n" + 
					"    UPPER(reports.Authenticate) AS Authenticate,\n" + 
					"    reports.Status\n" + 
					"FROM\n" + 
					"    reports reports\n" + 
					"        INNER JOIN\n" + 
					"    cdt_master_data.platform_master platform_master ON (reports.PlatformID = platform_master.ID)\n" + 
					"WHERE\n" + 
					"    platform_master.Name LIKE :searchText\n" + 
					"        OR reports.Title LIKE :searchText\n" + 
					"        OR reports.Authenticate LIKE :searchText\n" + 
					"        OR reports.Status LIKE :searchText", nativeQuery = true)
	Page<ReportsDto> findAllWithSearch(Pageable sortedByIdDesc, String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update reports set Status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveReport(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update reports set Status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeReport(Integer id);

}
