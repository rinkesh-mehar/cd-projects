package in.cropdata.toolsuite.drk.model.tileassignment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.model.tileassignment
 * @date 02/07/20
 * @time 12:40 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
@Entity(name = "geo_subregion_metadata")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GeoSubRegionMetaData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
   private Integer id;

    @Column(name = "RegionID")
    private Integer regionId;

    @Column(name = "SubRegionID")
    private Integer subRegionId;

    @Column(name = "isInRegion")
    private Integer isInRegion;

    @Column(name = "FocusCrops")
    private String focusCrops;

    @Column(name = "Color")
    private String color;

    @Column(name = "IrrigationPercent")
    private String irrigationPercent;

    @Column(name = "AvgLandHoldingSize")
    private String avgLandHoldingSize;

    @Transient
    private String bcss;

    @Column(name = "RowNo")
    private Integer rowNo;

    @Column(name = "ColNo")
    private Integer colNo;

    @Column(name = "VillageCount")
    private String villageCount;

    @Column(name = "AssignedVillageCount")
    private String AssignedVillageCount;

    @Column(name = "LeadCount")
    private String leadCount;

    @Transient
    private String totalAssignVillage;

    @Transient
    private String CreatedAt;

    @Transient
    private String UpdatedAt;
}
