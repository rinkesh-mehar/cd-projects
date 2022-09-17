package in.cropdata.cdtmasterdata.region.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 12/09/20
 * @time 10:38 AM
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "regional_panchayat_map")
public class PanchayatMap
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "RegionID")
    private int regionID;

    @Column(name = "SubRegionID")
    private Integer subRegionID;

    @Column(name = "VillageCode")
    private Integer villageCode;

    @Transient
    private String createdAt;

    @Transient
    private String updatedAt;

    @Transient
    private String status;

    @Transient
    private String name;
}
