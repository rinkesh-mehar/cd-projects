package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.JobApplicationDto;
import in.cropdata.cdtmasterdata.website.model.JobApplication;

@Repository
public interface JobApplicationReopsitory extends JpaRepository<JobApplication, Integer> {

	@Query(value = "SELECT \n" + 
			"				    job_applications.ID AS JobApplicationId,\n" + 
			"				    UPPER(posi.Name) AS Position,\n" + 
			"				    UPPER(job_applications.Name) AS ApplicantName,\n" + 
			"				    job_applications.Mobile,\n" + 
			"				    DATE_FORMAT(job_applications.CreatedAt, '%d-%m-%Y') AS CreatedAt,\n" + 
			"				    UPPER(opp.Location) AS Location,\n" + 
			"				    job_applications.ResumeUrl, \n" + 
			"				    job_applications.Status,\n" + 
			"		            DATE_FORMAT(job_applications.InterviewScheduledDate, '%d-%m-%Y %H:%i') AS InterviewScheduledDate\n" + 
			"				FROM\n" + 
			"				    job_applications job_applications\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.opportunities opp ON (job_applications.OpportunityID = opp.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.department dept ON (opp.DepartmentID = dept.ID) \n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.position posi ON (opp.PositionID = posi.ID) \n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
			"				        INNER JOIN \n" + 
			"				    cdt_website.education edu ON (oppEdu.EducationID = edu.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID)\n" + 
			"				WHERE\n" + 
			"				    job_applications.ID LIKE :searchText\n" + 
			"				        OR posi.Name LIKE :searchText\n" + 
			"				        OR edu.Name LIKE :searchText \n" + 
			"				        OR job_applications.Name LIKE :searchText\n" + 
			"				        OR job_applications.Mobile LIKE :searchText\n" + 
			"				        OR job_applications.CreatedAt LIKE :searchText\n" + 
			"				        OR opp.Location LIKE :searchText \n" + 
			"				        OR job_applications.Status LIKE :searchText \n" + 
			"				GROUP BY job_applications.ID , posi.Name , job_applications.Name ,job_applications.Mobile , job_applications.CreatedAt , opp.Location, job_applications.Status, job_applications.InterviewScheduledDate", countQuery = "SELECT \n" + 
					"				    job_applications.ID AS JobApplicationId,\n" + 
					"				    UPPER(posi.Name) AS Position,\n" + 
					"				    UPPER(job_applications.Name) AS ApplicantName,\n" + 
					"				    job_applications.Mobile,\n" + 
					"				    DATE_FORMAT(job_applications.CreatedAt, '%d-%m-%Y') AS CreatedAt,\n" + 
					"				    UPPER(opp.Location) AS Location,\n" + 
					"				    job_applications.ResumeUrl, \n" + 
					"				    job_applications.Status,\n" + 
					"		            DATE_FORMAT(job_applications.InterviewScheduledDate, '%d-%m-%Y %H:%i') AS InterviewScheduledDate\n" + 
					"				FROM\n" + 
					"				    job_applications job_applications\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.opportunities opp ON (job_applications.OpportunityID = opp.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.department dept ON (opp.DepartmentID = dept.ID) \n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.position posi ON (opp.PositionID = posi.ID) \n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.opportunities_education oppEdu ON (opp.ID = oppEdu.OpportunityID)\n" + 
					"				        INNER JOIN \n" + 
					"				    cdt_website.education edu ON (oppEdu.EducationID = edu.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID)\n" + 
					"				WHERE\n" + 
					"				    job_applications.ID LIKE :searchText\n" + 
					"				        OR posi.Name LIKE :searchText\n" + 
					"				        OR edu.Name LIKE :searchText \n" + 
					"				        OR job_applications.Name LIKE :searchText\n" + 
					"				        OR job_applications.Mobile LIKE :searchText\n" + 
					"				        OR job_applications.CreatedAt LIKE :searchText\n" + 
					"				        OR opp.Location LIKE :searchText \n" + 
					"				        OR job_applications.Status LIKE :searchText \n" + 
					"				GROUP BY job_applications.ID , posi.Name , job_applications.Name ,job_applications.Mobile , job_applications.CreatedAt , opp.Location, job_applications.Status, job_applications.InterviewScheduledDate", nativeQuery = true)
	Page<JobApplicationDto> getAllJobApplicationByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT \n" + 
			"						    jobApplication.ID AS JobApplicationId,\n" + 
			"						    jobApplication.Name AS ApplicantName, \n" + 
			"						    jobApplication.Email, \n" + 
			"						    jobApplication.Mobile, \n" + 
			"						    jobApplication.Address,\n" + 
			"						    jobApplication.ResumeUrl,\n" + 
			"						    platform_master.Name as Platform,\n" + 
			"						    dept.Name AS Department, \n" + 
			"						    posi.Name AS Position, \n" + 
			"						    UPPER(REPLACE(GROUP_CONCAT(edu.Name),',',', ')) AS Education,\n" + 
			"						    opp.Experience,\n" + 
			"						    opp.Location, \n" + 
			"						    REPLACE(REPLACE(opp.Description, '<p>', ''),'</p>','') AS Description,\n" + 
			"						    REPLACE(REPLACE(opp.Profile, '<p>', ''),'</p>','') AS Profile,\n" + 
			"						    opp.Remuneration, \n" + 
			"						    opp.ApplyTo , \n" + 
			"				            DATE_FORMAT(jobApplication.CreatedAt, '%d-%m-%Y') AS CreatedAt, \n" + 
			"		                    DATE_FORMAT(jobApplication.InterviewScheduledDate, '%Y-%m-%d %H:%i') AS InterviewScheduledDate,\n" + 
			"                            jobApplication.AppliedCount\n" + 
			"						FROM \n" + 
			"						    cdt_website.opportunities opp \n" + 
			"						        INNER JOIN\n" + 
			"						    cdt_website.department dept ON (opp.DepartmentID = dept.ID) \n" + 
			"						        INNER JOIN\n" + 
			"						    cdt_website.position posi ON (opp.PositionID = posi.ID) \n" + 
			"						        INNER JOIN \n" + 
			"						     cdt_website.opportunities_education oppEdu on (opp.ID = oppEdu.OpportunityID)\n" + 
			"						     INNER JOIN \n" + 
			"						    cdt_website.education edu ON (oppEdu.EducationID = edu.ID) \n" + 
			"						        INNER JOIN \n" + 
			"						    job_applications jobApplication ON (opp.ID = jobApplication.OpportunityID) \n" + 
			"						    INNER JOIN \n" + 
			"						    cdt_master_data.platform_master platform_master ON (opp.PlatformID = platform_master.ID) \n" + 
			"									where jobApplication.ID = ?1\n" + 
			"							Group by jobApplication.ID, \n" + 
			"						    jobApplication.Name,+ \n" + 
			"						    jobApplication.Email, \n" + 
			"						    jobApplication.Mobile,\n" + 
			"						    jobApplication.Address,\n" + 
			"						    jobApplication.ResumeUrl,\n" + 
			"						    platform_master.Name,\n" + 
			"						    dept.Name,\n" + 
			"						    posi.Name,\n" + 
			"						    opp.Experience,\n" + 
			"						    opp.Location,\n" + 
			"						    opp.Description,\n" + 
			"						    opp.Profile,\n" + 
			"						    opp.Remuneration, \n" + 
			"						    opp.ApplyTo,\n" + 
			"				            jobApplication.CreatedAt,\n" + 
			"		                    jobApplication.InterviewScheduledDate,\n" + 
			"                            jobApplication.AppliedCount", nativeQuery = true)
	JobApplicationDto getJobApplicationById(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_website.job_applications set status = 'Shortlisted'\n" + 
			"where id = ?1",nativeQuery = true)
	int shortlistApplication(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_website.job_applications set status = 'Hold'\n" + 
			"where id = ?1",nativeQuery = true)
	int holdApplication(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_website.job_applications set status = 'Interview Scheduled', InterviewScheduledDate = ?1\n" + 
			"			where id = ?2",nativeQuery = true)
	int scheduleInterview(String interviewScheduleDate, Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_website.job_applications set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectApplication(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_website.job_applications set status = 'Selected'\n" + 
			"where id = ?1",nativeQuery = true)
	int interviewSelection(Integer id);

}
