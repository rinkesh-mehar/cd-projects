package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface TaskTypeSpecificationsInfDto {

    public Integer getID();
	
    public Integer getStateID();
	
    public Integer getSeasonID();
	
    public Integer getCommodityID();
	
    public Integer getVarietyID();
	
    public Integer getPhenophaseID();
	
    public Integer getPushBackLimit();
	
    public Integer getPriority();
	
    public Integer getTaskTime();
	
    public Integer getTaskTypeID();
	
    public String getSpotDependency();
	
    public Date getCreatedAt();
	
    public Date getUpdatedAt();
	
    public String getStatus();
    
    public String  getStateName();
    
    public String getSeasonName();
    
    public String getCommodityName();
    
    public String getVarietyName();
    
    public String getPhenophaseName();
    
    public String getTaskTypeName();

    Boolean getIsValid();

    String getErrorMessage();
}
