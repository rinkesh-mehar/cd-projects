package in.cropdata.portal.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface JobApplicationVO
{

	public Integer getId();

	public String getDepartment();

	public String getPosition();

	public String getEducation();

	public Integer getExperience();

	public String getState();

	public String getDistrict();

	public String getDescription();

	public String getProfile();

	public String getRemuneration();

	public String getApplyTo();
	
	public Integer getJobApplicationId();
	
	public String getApplicantName();

	public String getEmail();

	public String getMobile();

	public String getAddress();

	public String getResumeUrl();
	
	public Integer getAppliedCount();
	
}
