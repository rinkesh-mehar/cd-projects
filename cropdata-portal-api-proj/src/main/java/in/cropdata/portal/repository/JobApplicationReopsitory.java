package in.cropdata.portal.repository;

import in.cropdata.portal.vo.JobApplicationVO;
import in.cropdata.portal.model.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JobApplicationReopsitory extends JpaRepository<JobApplication, Integer> {

	@Query(value = "SELECT ID as JobApplicationId,Name as ApplicantName,Email,Mobile,Address,ResumeUrl  FROM job_applications\n" + 
			"where Name like :searchText OR Email like :searchText OR Mobile like :searchText OR Address like :searchText",
			countQuery = "SELECT ID as JobApplicationId,Name as ApplicantName,Email,Mobile,Address,ResumeUrl  FROM job_applications\n" +
					"where Name like :searchText OR Email like :searchText OR Mobile like :searchText OR Address like :searchText", nativeQuery = true)
	Page<JobApplicationVO> getAllJobApplicationByPagenation(Pageable sortedByIDAsce, String searchText);
	
	@Query(value = "SELECT jobApplication.ID as JobApplicationId, dept.Name AS Department, posi.Name AS Position, edu.Name AS Education, opp.Experience,\n" + 
			"gs.Name AS State, gd.Name AS District, opp.Description, opp.Profile, opp.Remuneration, opp.ApplyTo,jobApplication.Name as ApplicantName,jobApplication.Email,jobApplication.Mobile,jobApplication.Address,jobApplication.ResumeUrl\n" + 
			"FROM cdt_website.opportunities opp INNER JOIN cdt_website.department dept ON (opp.DepartmentID = dept.ID)\n" + 
			"INNER JOIN cdt_website.position posi ON (opp.PositionID = posi.ID)\n" + 
			"INNER JOIN cdt_website.education edu ON (opp.EducationID = edu.ID)\n" + 
			"INNER JOIN cdt_master_data.geo_state gs ON (opp.StateCode = gs.StateCode)\n" + 
			"INNER JOIN cdt_master_data.geo_district gd ON (opp.DistrictCode = gd.DistrictCode)\n" + 
			"INNER JOIN job_applications jobApplication ON (opp.ID = jobApplication.OpportunityID)\n" + 
			"where jobApplication.ID = ?1", nativeQuery = true)
	JobApplicationVO getJobApplicationById(Integer id);

	JobApplication findByOpportunityIDAndEmailAndMobile(Integer opportunityID, String email,String mobile);
	
}
