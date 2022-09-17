package in.cropdata.cdtmasterdata.model.vo;

/**
 * Used for fetching platform master data.
 * 
 * @author OM
 */
public interface PlatformMasterVO {

	public Integer getID();
	public String getName();
	public String getDescription();
	public Integer getViewOrder();
	public String getLogo();
	public String getStatus();
	
}
