package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.OpportunitiesDto;
import in.cropdata.cdtmasterdata.website.model.Opportunities;

@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunities, Integer> {

	@Query(value = "SELECT \n" + 
			"    opp.ID,\n" + 
			"    platform_master.Name AS Platform,\n" + 
			"    dept.Name AS Department,\n" + 
			"    posi.Name AS Position,\n" + 
			"    UPPER(REPLACE(GROUP_CONCAT(edu.Name),',',', ')) AS Education,\n" + 
			"    opp.Experience,\n" + 
			"    opp.Location,\n" + 
			"    opp.Description,\n" + 
			"    opp.Profile,\n" + 
			"    opp.Remuneration,\n" + 
			"    opp.ApplyTo\n" + 
			"FROM\n" + 
			"    opportunities opp\n" + 
			"        INNER JOIN\n" + 
			"    department dept ON (opp.DepartmentID = dept.ID)\n" + 
			"        INNER JOIN\n" + 
			"    position posi ON (opp.PositionID = posi.ID)\n" + 
			"        INNER JOIN\n" + 
			"    opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
			"        INNER JOIN\n" + 
			"    education edu ON (oppEdu.EducationID = edu.ID)\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID)\n" + 
			"    GROUP BY\n" + 
			"    opp.ID,\n" + 
			"    platform_master.Name,\n" + 
			"    dept.Name,\n" + 
			"    posi.Name,\n" + 
			"    opp.Experience,\n" + 
			"    opp.Location,\n" + 
			"    opp.Description,\n" + 
			"    opp.Profile,\n" + 
			"    opp.Remuneration,\n" + 
			"    opp.ApplyTo", nativeQuery = true)
	List<OpportunitiesDto> getAllOpportunities();

	@Query(value = "SELECT \n" + 
			"    opp.ID,\n" + 
			"    UPPER(platform_master.Name) AS Platform,\n" + 
			"    UPPER(dept.Name) AS Department,\n" + 
			"    UPPER(posi.Name) AS Position,\n" + 
			"    UPPER(REPLACE(GROUP_CONCAT(edu.Name),',',', ')) AS Education,\n" + 
			"    opp.Experience,\n" + 
			"    UPPER(opp.Location) as Location,\n" + 
			" --   UPPER(REPLACE(opp.Description, '<p>', '')) AS Description,\n" + 
			" --   UPPER(REPLACE(opp.Profile, '<p>', '')) AS Profile,\n" + 
			"    UPPER(opp.Remuneration) AS Remuneration,\n" + 
			" --   opp.ApplyTo,\n" + 
			"    opp.Status\n" + 
			"FROM\n" + 
			"    cdt_website.opportunities opp\n" + 
			"        INNER JOIN\n" + 
			"    cdt_website.department dept ON (opp.DepartmentID = dept.ID)\n" + 
			"        INNER JOIN\n" + 
			"    cdt_website.position posi ON (opp.PositionID = posi.ID)\n" + 
			"        INNER JOIN\n" + 
			"    opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
			"        INNER JOIN\n" + 
			"    education edu ON (oppEdu.EducationID = edu.ID)\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID)\n" + 
			"	where platform_master.Name like :searchText OR dept.Name like :searchText OR posi.Name like :searchText OR edu.Name like :searchText OR opp.Experience like :searchText OR\n" + 
			"	opp.Profile like :searchText\n" + 
			"    GROUP BY opp.ID,\n" + 
			"    platform_master.Name,\n" + 
			"    dept.Name,\n" + 
			"    posi.Name,\n" + 
			"    opp.Experience,\n" + 
			"    opp.Location,\n" + 
			"    opp.Description,\n" + 
			"    opp.Profile,\n" + 
			"    opp.Remuneration,\n" + 
			"    opp.ApplyTo,\n" + 
			"    opp.Status", countQuery = "SELECT \n" + 
					"    opp.ID,\n" + 
					"    UPPER(platform_master.Name) AS Platform,\n" + 
					"    UPPER(dept.Name) AS Department,\n" + 
					"    UPPER(posi.Name) AS Position,\n" + 
					"    UPPER(REPLACE(GROUP_CONCAT(edu.Name),',',', ')) AS Education,\n" + 
					"    opp.Experience,\n" + 
					"    UPPER(opp.Location) as Location,\n" + 
					" --   UPPER(REPLACE(opp.Description, '<p>', '')) AS Description,\n" + 
					" --   UPPER(REPLACE(opp.Profile, '<p>', '')) AS Profile,\n" + 
					"    UPPER(opp.Remuneration) AS Remuneration,\n" + 
					" --   opp.ApplyTo,\n" + 
					"    opp.Status\n" + 
					"FROM\n" + 
					"    cdt_website.opportunities opp\n" + 
					"        INNER JOIN\n" + 
					"    cdt_website.department dept ON (opp.DepartmentID = dept.ID)\n" + 
					"        INNER JOIN\n" + 
					"    cdt_website.position posi ON (opp.PositionID = posi.ID)\n" + 
					"        INNER JOIN\n" + 
					"    opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
					"        INNER JOIN\n" + 
					"    education edu ON (oppEdu.EducationID = edu.ID)\n" + 
					"        INNER JOIN\n" + 
					"    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID)\n" + 
					"	where platform_master.Name like :searchText OR dept.Name like :searchText OR posi.Name like :searchText OR edu.Name like :searchText OR opp.Experience like :searchText OR\n" + 
					"	opp.Profile like :searchText\n" + 
					"    GROUP BY opp.ID,\n" + 
					"    platform_master.Name,\n" + 
					"    dept.Name,\n" + 
					"    posi.Name,\n" + 
					"    opp.Experience,\n" + 
					"    opp.Location,\n" + 
					"    opp.Description,\n" + 
					"    opp.Profile,\n" + 
					"    opp.Remuneration,\n" + 
					"    opp.ApplyTo,\n" + 
					"    opp.Status", nativeQuery = true)
	Page<OpportunitiesDto> getAllOpportunitiesByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT\n" + 
			"		    opp.ID,\n" + 
			"		    opp.PlatformID,\n" + 
			"		    opp.DepartmentID,\n" + 
			"		    opp.PositionID,\n" + 
			"		    opp.Experience,\n" + 
			"		    opp.Location,\n" + 
			"		    REPLACE(REPLACE(opp.Description, '<p>', ''),'</p>','') AS Description,\n" + 
			"		    REPLACE(REPLACE(opp.Profile, '<p>', ''),'</p>','') AS Profile, \n" + 
			"		    opp.Remuneration,\n" + 
			"		    opp.ApplyTo\n" + 
			"		FROM\n" + 
			"		    cdt_website.opportunities opp\n" + 
			"		WHERE \n" + 
			"		    opp.ID = ?1", nativeQuery = true)
	OpportunitiesDto getOpportunitiesById(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update opportunities set status = 'Closed'\n" + 
			"where id = ?1",nativeQuery = true)
	int closeOpportunityById(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update opportunities set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeOpportunityById(Integer id);

	@Query(value="SELECT EducationID FROM cdt_website.opportunities_education where OpportunityID = ?1",nativeQuery = true)
	Integer[] getEducationIdsByOpportunityId(Integer opportunityId);

}
