package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriCommodityPhenophaseInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getPhenophaseId();

	public String getCommodity();

	public String getPhenophase();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

}
