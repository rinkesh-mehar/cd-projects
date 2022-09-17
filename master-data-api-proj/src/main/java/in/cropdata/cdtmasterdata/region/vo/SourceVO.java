package in.cropdata.cdtmasterdata.region.vo;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.vo
 * @date 18/01/21
 * @time 3:25 PM
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SourceVO
{
    public Integer getId();

    public String getName();

    public String getDescription();

    public String getAddress();

    public Integer getCountryCode();

    public Integer getStateCode();

    public String getHeadquarter();

    public String getApiKey();

    public String getStateName();

    public String getCountryName();

}
