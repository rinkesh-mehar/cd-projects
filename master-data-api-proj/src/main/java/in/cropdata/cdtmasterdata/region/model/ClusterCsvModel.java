package in.cropdata.cdtmasterdata.region.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.model
 * @date 30/06/20
 * @time 6:31 PM
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClusterCsvModel
{
    @CsvBindByName
    private Integer subRegionID;

    @CsvBindByName
    private Integer isInRegion;

    private Integer totalRow;

    private Integer totalColumn;

    private Integer _regionId;

    private String _act;

    private String mmpkFileUrl;

    @CsvBindByName
    private String bcss;

    @CsvBindByName
    private Integer colNo;

    @CsvBindByName
    private Integer rowNo;

    @CsvBindByName
    private Integer ringNumber;

    @CsvBindByName
    private String isRlOffice;





}
