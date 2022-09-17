/**
 * 
 */
package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.dto.RegionalConnectivityDTO;
import in.cropdata.cdtmasterdata.model.RegionalConnectivity;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface RegionalConnectivityRepository extends JpaRepository<RegionalConnectivity, Integer> {
	
	
	 @Query(value=" select rct.ID as id ,rct.RegionID as RegionId,r.name as regionName,rct.SurfacedProportion as surfacedProportion,"+
	 		      "rct.UnsurfacedProportion as unsurfacedProportion,\n" + 
	 		      " rct.SurfacedTimeMinPerkm as surfacedTimeMin,rct.UnsurfacedTimeMinPerKm as unsurfacedTimeMin,  \n" + 
	 		      " rct.Status as status from cdt_master_data.regional_connectivity_time rct  \n" + 
	 		      " inner join drkrishi.region r on r.id = rct.RegionID order by r.name",nativeQuery = true)
     List<RegionalConnectivityDTO> getListOfRegionalConnectivity();
	 
	 @Query(value="select rct.ID as id ,rct.RegionID as RegionId,r.name as regionName,rct.SurfacedProportion as surfacedProportion,\n" + 
	 		"	 		     rct.UnsurfacedProportion as unsurfacedProportion,\n" + 
	 		"	 		      rct.SurfacedTimeMinPerkm as surfacedTimeMin,rct.UnsurfacedTimeMinPerKm as unsurfacedTimeMin,\n" + 
	 		"	 		      rct.Status as status from cdt_master_data.regional_connectivity_time rct\n" + 
	 		"	 		      inner join drkrishi.region r on r.id = rct.RegionID \n" + 
	 		"				  where rct.ID like :searchText OR rct.RegionID like :searchText OR r.name like :searchText OR rct.SurfacedProportion like :searchText OR \n" + 
	 		"	 		     rct.UnsurfacedProportion like :searchText OR \n" + 
	 		"	 		      rct.SurfacedTimeMinPerkm like :searchText OR rct.UnsurfacedTimeMinPerKm like :searchText OR \n" + 
	 		"	 		      rct.Status like :searchText",countQuery = "select rct.ID as id ,rct.RegionID as RegionId,r.name as regionName,rct.SurfacedProportion as surfacedProportion,\n" + 
	 				"	 		     rct.UnsurfacedProportion as unsurfacedProportion,\n" + 
	 				"	 		      rct.SurfacedTimeMinPerkm as surfacedTimeMin,rct.UnsurfacedTimeMinPerKm as unsurfacedTimeMin,\n" + 
	 				"	 		      rct.Status as status from cdt_master_data.regional_connectivity_time rct\n" + 
	 				"	 		      inner join drkrishi.region r on r.id = rct.RegionID \n" + 
	 				"				  where rct.ID like :searchText OR rct.RegionID like :searchText OR r.name like :searchText OR rct.SurfacedProportion like :searchText OR \n" + 
	 				"	 		     rct.UnsurfacedProportion like :searchText OR \n" + 
	 				"	 		      rct.SurfacedTimeMinPerkm like :searchText OR rct.UnsurfacedTimeMinPerKm like :searchText OR \n" + 
	 				"	 		      rct.Status like :searchText",nativeQuery = true)
		Page<RegionalConnectivityDTO> getRegionalConnectivityListByPagenation(Pageable sortedByIdDesc, String searchText);

}
