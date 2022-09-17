package in.cropdata.cdtmasterdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 09/11/20
 * @time 11:30 AM
 */
@Data
@Entity(name = "regional_monthly_weather_based_travel_time")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionalMonthWBTravelTime
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RegionID")
    private Integer regionID;

    @Column(name = "Month")
    private String month;

    @Column(name = "UnitType")
    private Integer unitType;

    @Transient
    private String createdAt;

    @Transient
    private String updatedAt;

    @Transient
    private String unitName;

    @Transient
    private String regionName;

}
