package in.cropdata.cdtmasterdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author RinkeshKM
 * @Date 25/09/20
 */

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DashboardHeader {


    public DashboardHeader(String firstValue, String secondValue) {

        this.firstValue = firstValue;

        this.secondValue = secondValue;
    }

    private String firstValue;

    private String secondValue;

    private String totalUnverifiedLeads;

    private String currentUnverifiedLeads;

    private String totalUnverifiedLeadsArea;

    private String currentUnverifiedLeadsArea;

    private String totalVerifiedLeads;

    private String currentVerifiedLeads;

    private String totalVerifiedLeadsArea;

    private String currentVerifiedLeadsArea;

    private List<Map<String, String>> AllRegionList;

    private Map<String, String> leadUnverifiedCount;

    private List<Map<String, String>> leadUnverifiedList;

    private Map<String, String> leadCropAreaCount;

    private List<Map<String, String>> leadUnverifiedAreaList;

    private Map<String, String> leadVerifiedCount;

    private List<Map<String, String>> leadVerifiedList;

    private Map<String, String> leadVerifiedCropAreaCount;

    private List<Map<String, String>> leadVerifiedCropAreaList;

    Integer editFlag;

    List<Integer> regionIds;

}
