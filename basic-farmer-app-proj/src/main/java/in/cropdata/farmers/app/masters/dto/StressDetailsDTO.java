package in.cropdata.farmers.app.masters.dto;


/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:19 PM
 */

public interface StressDetailsDTO {

    Integer getSymptomID();

    String getSymptom();

    String getStressName();

    String getGenericImage();
    
    String getFarmerID();
    
    Integer getCommodityID();
    
    String getReported();
    
    String getCaseID();
}
