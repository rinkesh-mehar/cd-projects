package in.cropdata.cdtmasterdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author RinkeshKM
 * @Date 17/09/20
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Dashboard {

    public Dashboard(BigInteger totalLeads, BigInteger currentLeads) {
        this.totalLeads = totalLeads;
        this.currentLeads = currentLeads;
    }

    public Dashboard() {
    }

    private BigInteger totalLeads;

    private BigInteger currentLeads;

    private Integer verifiedLeads;

    private Integer rejectedLeads;

    private Integer regionId;

    private Integer id;

    private String commodityId;

    private Integer pendingLead;

    private List<Integer> commodityIds;

    private List<Integer> regionIds;

    private List<String> commodityNames;

    Integer editFlag;

    String regionName;

    BigInteger currentCropArea;

    BigInteger totalCropArea;

    Double landHoldingSizeAvg;

    List<Integer> landSize;

    List<Map<String, String>> commodityIdName;

    List<Map<String, String>> lastSevenDayArea;

    List<Map<String, String>> pastDaysLead;

    List<Map<String, String>> currentVerifiedLead;

    List<Map<String, String>> pastDaysVerifiedLead;

    List<Map<String, String>> allRegionList;

    List<Map<String, String>> commodityAreaList;

    List<Map<String, String>> lead;
}
