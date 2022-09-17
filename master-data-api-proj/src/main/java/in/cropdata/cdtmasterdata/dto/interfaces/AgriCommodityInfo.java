package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AgriCommodityInfo {

    Integer getID();

    String getName();

    String getScientificName();

    String getStatus();

    Boolean getIsValid();
    
    Integer getCommodityGroupID();
    
    Integer getCommodityGroupIndex();
    
    String getCommodityGroup();
    
    String getDescription();
    
    String getLogo();

//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

}
