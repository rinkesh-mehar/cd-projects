package in.cropdata.toolsuite.drk.repository.tileassignment;

import in.cropdata.toolsuite.drk.dto.tileassignment.SubRegionMetaDataDTO;
import in.cropdata.toolsuite.drk.model.tileassignment.GeoSubRegionMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.repository.tileassignment
 * @date 02/07/20
 * @time 12:39 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface GeoSubRegionMetaDataRepository  extends JpaRepository<GeoSubRegionMetaData, Integer>
{
    @Query(value = "SELECT gsm.ID id, gsm.Color color,gsm.AvgLandHoldingSize avgLandHoldingSize,\n" +
            "             group_concat(distinct ac.Name) focusCrops,\n" +
            "            gsm.IrrigationPercent irrigationPercent, gsm.isInRegion isInRegion,\n" +
            "            grr.RegionID regionID, grr.SubRegionID subRegionID,\n" +
            "            /*if(isnull(rpm.SubRegionID), 0, 1) as isPanchayat,*/ gsm.IsRlOffice,  gsm.LeadCount, gsm.AssignedVillageCount, gsm.VillageCount,\n" +
            "            grr.RingNumber, grr.bcss, gsm.RowNo, gsm.ColNo\n" +
            "            from cdt_master_data.geo_subregion_metadata as gsm\n" +
            "            left join  cdt_master_data.agri_commodity ac on find_in_set(ac.ID,gsm.FocusCrops)\n" +
           /* "            left join cdt_master_data.regional_panchayat_map as rpm on rpm.SubRegionID = gsm.SubRegionID\n" +*/
            "            inner join cdt_master_data.geo_region_rings grr on grr.RegionID = gsm.RegionID and grr.SubregionID= gsm.SubRegionID\n" +
            "            and gsm.RingNumber=grr.RingNumber\n" +
            "            where gsm.RegionID=?1 group by gsm.ID , gsm.Color ,gsm.AvgLandHoldingSize, grr.bcss , gsm.IrrigationPercent , gsm.isInRegion ,\n" +
            "            grr.RegionID , grr.SubRegionID ,\n" +
            "             gsm.IsRlOffice,  gsm.LeadCount, gsm.AssignedVillageCount, gsm.VillageCount,\n" +
            "            grr.RingNumber, grr.bcss, gsm.RowNo, gsm.ColNo order by gsm.RowNo, gsm.ColNo", nativeQuery = true)
    List<SubRegionMetaDataDTO> getByRegionId(Integer regionId);
}
