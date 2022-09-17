package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeneralBankInfDto {
	
	public Integer getID();
	
	public String getName();
	
	public Integer getBankCategoryID();
	
	public String getBankCategory();
	
	public String getStatus();

	Date getCreatedAt();

	Date getUpdatedAt();

	Boolean getIsValid();

	String getErrorMessage();
}
