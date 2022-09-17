/**
 * 
 */
package in.cropdata.cdtmasterdata.dto;

/**
 * @author Vivek Gajbhiye
 *
 */
public interface AgriAgrochemicalDTO {
	
	
	public Integer getID();
	
	public Integer getCommodityID();
	
	public String getCommodity();
	
	public Integer getAgrochemicalTypeID();
	
	public String getAgrochemicalType();
	
	public String getCIBRCApproved();
	
	public String getName();
	
	public Integer getWaitingPeriod();
	
	public String getStatus();

}
