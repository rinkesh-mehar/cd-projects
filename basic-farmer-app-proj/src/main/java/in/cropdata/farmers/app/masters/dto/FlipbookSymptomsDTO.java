package in.cropdata.farmers.app.masters.dto;


/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:19 PM
 */

public interface FlipbookSymptomsDTO {

    Integer getStressID();

    Integer getSymptomID();

    String getSymptom();

    String getStressName();

    String getGenericImage();

    Boolean getIsSelected();
    
    String getFarmerID();
    
    String getDeviceToken();
    
    Integer getCommodityID();
    
    String getUrl();
    
    String getReported();
}
