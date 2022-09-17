/**
 * 
 */
package in.cropdata.farmers.app.drk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.FarmerAddressBookDTO;
import in.cropdata.farmers.app.drk.model.FarmerAddressBook;



/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface FarmerAddressBookRepository extends JpaRepository<FarmerAddressBook, Integer> {
	
	@Query(value = "Select f.id as farmerID, gv.RegionID as regionID,\n" + 
			"fab.StateCode as StateCode,fab.DistrictCode,\n" + 
			"f.village_id as VillageCode, gv.SubRegionID\n" + 
			"from drkrishi_ts.farmer f\n" + 
			"left join drkrishi_ts.farmer_address_book fab on fab.id = f.address_id\n" + 
			"left join cdt_master_data.geo_village gv on gv.VillageCode = f.Village_id\n" + 
			"where f.id = ?1", nativeQuery = true)
	FarmerAddressBookDTO getFarmerGeoDetails(String farmerId);

}
