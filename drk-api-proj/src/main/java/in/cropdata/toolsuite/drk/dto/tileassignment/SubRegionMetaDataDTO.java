package in.cropdata.toolsuite.drk.dto.tileassignment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.dto.tileassignment
 * @date 25/08/20
 * @time 7:20 PM
 * To change this template use File | Settings | File and Code Templates
 */
public interface SubRegionMetaDataDTO
{
    public Integer getId();
    public Integer getRegionId();
    public String getSubRegionId();
    public Integer getIsInRegion();
    public String getFocusCrops();
    public String getColor();
    public String getIrrigationPercent();
    public String getAvgLandHoldingSize();
    public int getIsPanchayat();
    public String getIsRlOffice();
    public int getRingNumber();
    public String getBcss();
    public Integer getColNo();
    public Integer getRowNo();
    public String getAssignedVillageCount();
    public String getVillageCount();
    public String getLeadCount();

}
