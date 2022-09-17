package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriBioticStressInfDto {

	public Integer getId();

	public Integer getCommodityId();

//	public int getPhenophaseId();

	public Integer getStressTypeId();

	public Integer getStateCode();

	public String getName();

	public String getState();

	public String getScientificName();

	public String getSymptoms();

	public String getCommodity();

	public String getStressType();
	
	public Integer getStart();
	
	public Integer getEnd();

//	public String getPhenophase();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();
	


}
