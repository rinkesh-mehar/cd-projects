package in.cropdata.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.Reports;
import in.cropdata.portal.vo.ReportsVO;

import java.util.List;

/**
 * Repository class for interaction with database.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer> {

	@Query(value = "SELECT ID, Platform, Title, FileUrl FROM reports "
			+ "WHERE Platform like :searchText OR Title like :searchText", countQuery = "SELECT ID, Platform, Title, FileUrl FROM reports "
					+ "WHERE Platform like :searchText OR Title like :searchText", nativeQuery = true)
	Page<ReportsVO> findAllWithSearch(Pageable sortedByIdAsc, String searchText);

	@Query(value = "SELECT \n" + 
			"    n.ID,\n" + 
			"    n.PlatformID,\n" + 
			"    pm.name Platform,\n" + 
			"    n.Title title,\n" + 
			"    n.FileUrl,\n" + 
			"    n.Status,\n" + 
			"    n.Authenticate\n" + 
			"FROM\n" + 
			"    cdt_website.reports n\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.general_platform_master pm ON pm.ID = n.PlatformID\n" + 
			"    where n.Status = 'Active'", nativeQuery = true)
	List<ReportsVO> getReports();

}
