package in.cropdata.farmers.app.gstmTransitory.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 2:33 PM
 */

@Component
public interface CaseDetailsDTO {

    Integer getStateCode();

    Integer getSeasonID();

    Integer getCommodityID();

    Integer getVarietyID();

    Date getCorrectedSowingDate();

    String getCommodityName();

    String getSeasonName();

    String getStateName();

    String getVarietyName();
    
    Integer getZonalVarietyID();
    
    Integer getZonalCommodityID();
    
    Integer getCurrentSowingWeek();
    
    String getSowingDate();


}
