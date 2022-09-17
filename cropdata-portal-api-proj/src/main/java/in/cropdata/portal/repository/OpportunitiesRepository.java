package in.cropdata.portal.repository;


import in.cropdata.portal.vo.OpportunitiesVO;
import in.cropdata.portal.model.Opportunities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunities, Integer> {

	@Query(value = "SELECT \n" + 
			"    opp.ID,\n" + 
			"    general_platform_master.Name AS Platform,\n" + 
			"    dept.Name AS Department,\n" + 
			"    posi.Name AS Position,\n" + 
			"    GROUP_CONCAT(edu.Name) AS Education,\n" + 
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
			"       INNER JOIN\n" + 
			"    opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
			"        INNER JOIN\n" + 
			"    education edu ON (oppEdu.EducationID = edu.ID)\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.general_platform_master general_platform_master ON (opp.PlatformID = general_platform_master.ID)\n" + 
			"    where opp.Status = 'Active' GROUP BY\n" + 
			"    opp.ID,\n" + 
			"    general_platform_master.Name,\n" + 
			"    dept.Name,\n" + 
			"    posi.Name,\n" + 
			"    opp.Experience,\n" + 
			"    opp.Location,\n" + 
			"    opp.Description,\n" + 
			"    opp.Profile,\n" + 
			"    opp.Remuneration,\n" + 
			"    opp.ApplyTo", nativeQuery = true)
	List<OpportunitiesVO> getAllOpportunities();

	@Query(value = "SELECT \n" + 
			"    opp.ID,\n" + 
			"    general_platform_master.Name AS Platform,\n" + 
			"    dept.Name AS Department,\n" + 
			"    posi.Name AS Position,\n" + 
			"    GROUP_CONCAT(edu.Name) AS Education,\n" + 
			"    opp.Experience,\n" + 
			"    opp.Location,\n" + 
			"    REPLACE(opp.Description,'\n','') AS Description,\n" + 
			"    REPLACE(opp.Profile,'\n','') AS Profile,\n" + 
			"    opp.Remuneration,\n" + 
			"    opp.ApplyTo\n" + 
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
			"    cdt_master_data.general_platform_master general_platform_master ON (opp.PlatformID = general_platform_master.ID)\n" + 
			"WHERE\n" + 
			"    opp.Status = 'Active'\n" + 
			"        AND (dept.Name LIKE :searchText\n" + 
			"        OR posi.Name LIKE :searchText\n" + 
			"        OR edu.Name LIKE :searchText\n" + 
			"        OR opp.Experience LIKE :searchText\n" + 
			"        OR opp.Profile LIKE :searchText\n" + 
			"        OR opp.Remuneration LIKE :searchText\n" + 
			"        OR opp.ApplyTo LIKE :searchText)\n" + 
			"GROUP BY opp.ID , general_platform_master.Name , dept.Name , posi.Name , opp.Experience , opp.Location , opp.Description , opp.Profile , opp.Remuneration , opp.ApplyTo", countQuery = "SELECT \n" + 
					"    opp.ID,\n" + 
					"    general_platform_master.Name AS Platform,\n" + 
					"    dept.Name AS Department,\n" + 
					"    posi.Name AS Position,\n" + 
					"    GROUP_CONCAT(edu.Name) AS Education,\n" + 
					"    opp.Experience,\n" + 
					"    opp.Location,\n" + 
					"    REPLACE(opp.Description,'\n','') AS Description,\n" + 
					"    REPLACE(opp.Profile,'\n','') AS Profile,\n" + 
					"    opp.Remuneration,\n" + 
					"    opp.ApplyTo\n" + 
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
					"    cdt_master_data.general_platform_master general_platform_master ON (opp.PlatformID = general_platform_master.ID)\n" + 
					"WHERE\n" + 
					"    opp.Status = 'Active'\n" + 
					"        AND (dept.Name LIKE :searchText\n" + 
					"        OR posi.Name LIKE :searchText\n" + 
					"        OR edu.Name LIKE :searchText\n" + 
					"        OR opp.Experience LIKE :searchText\n" + 
					"        OR opp.Profile LIKE :searchText\n" + 
					"        OR opp.Remuneration LIKE :searchText\n" + 
					"        OR opp.ApplyTo LIKE :searchText)\n" + 
					"GROUP BY opp.ID , general_platform_master.Name , dept.Name , posi.Name , opp.Experience , opp.Location , opp.Description , opp.Profile , opp.Remuneration , opp.ApplyTo", nativeQuery = true)
	Page<OpportunitiesVO> getAllOpportunitiesByPagenation(Pageable sortedByIdAsc, String searchText);
	
	@Query(value = "SELECT \n" + 
			"		    opp.ID,\n" + 
			"		    general_platform_master.Name AS Platform,\n" + 
			"		    dept.Name AS Department,\n" + 
			"		    posi.Name AS Position,\n" + 
			"		    GROUP_CONCAT(edu.Name) AS Education, \n" + 
			"		    opp.Experience, \n" + 
			"		    opp.Location, \n" + 
			"		    REPLACE(opp.Description,'\\n','') as Description, \n" + 
			"		    REPLACE(opp.Profile,'\\n','') as Profile,\n" + 
			"		    opp.Remuneration,\n" + 
			"		    opp.ApplyTo,\n" + 
			"            DATE_FORMAT(opp.UpdatedAt, '%d-%b-%Y') AS postedOn\n" + 
			"		FROM\n" + 
			"		    cdt_website.opportunities opp\n" + 
			"		        INNER JOIN \n" + 
			"		    cdt_website.department dept ON (opp.DepartmentID = dept.ID)\n" + 
			"		        INNER JOIN \n" + 
			"		    cdt_website.position posi ON (opp.PositionID = posi.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
			"		        INNER JOIN\n" + 
			"		    education edu ON (oppEdu.EducationID = edu.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.general_platform_master general_platform_master ON (opp.PlatformID = general_platform_master.ID)\n" + 
			"		WHERE\n" + 
			"		    opp.Status = 'Active'  AND opp.ID = ?1\n" + 
			"		GROUP BY opp.ID , general_platform_master.Name , dept.Name , posi.Name , opp.Experience , opp.Location , opp.Description , opp.Profile , opp.Remuneration , opp.ApplyTo, opp.UpdatedAt", nativeQuery = true)
	OpportunitiesVO getOpportunitiesById(Integer id);

}
