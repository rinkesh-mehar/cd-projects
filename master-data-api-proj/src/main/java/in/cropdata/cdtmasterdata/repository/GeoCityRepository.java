package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeoCityInfDto;
import in.cropdata.cdtmasterdata.model.GeoCity;

public interface GeoCityRepository extends JpaRepository<GeoCity, Integer> {
	
//	@Query(value = "SELECT GC.ID,GC.Name,GC.CityCode,GC.StateCode,GC.DistrictCode,GC.Status,GS.Name State,GD.Name as District FROM geo_city GC\n" + 
//			"			LEFT JOIN geo_state GS ON(GS.StateCode = GC.StateCode)\n" + 
//			"			LEFT JOIN geo_district GD ON(GD.DistrictCode = GC.DistrictCode)\n" + 
//			"			where GC.Name like :searchText\n" + 
//			"			OR GC.CityCode like :searchText\n" + 
//			"			OR GS.Name like :searchText\n" + 
//			"			OR GD.Name like :searchText", nativeQuery = true)
//	List<GeoCity> getCity();
	
	@Query(value = "SELECT GC.ID,GC.Name,GC.CityCode,GC.StateCode,GC.DistrictCode,GC.Status,GS.Name State,GD.Name as District FROM geo_city GC\n" + 
			"LEFT JOIN geo_state GS ON(GS.StateCode = GC.StateCode)\n" + 
			"LEFT JOIN geo_district GD ON(GD.DistrictCode = GC.DistrictCode)\n" + 
			"where GC.Name like :searchText\n" + 
			"OR GC.CityCode like :searchText\n" + 
			"OR GS.Name like :searchText\n" + 
			"OR GD.Name like :searchText",
			countQuery = "SELECT GC.ID,GC.Name,GC.CityCode,GC.StateCode,GC.DistrictCode,GC.Status,GS.Name State,GD.Name as District FROM geo_city GC\n" + 
					"LEFT JOIN geo_state GS ON(GS.StateCode = GC.StateCode)\n" + 
					"LEFT JOIN geo_district GD ON(GD.DistrictCode = GC.DistrictCode)\n" + 
					"where GC.Name like :searchText\n" + 
					"OR GC.CityCode like :searchText\n" + 
					"OR GS.Name like :searchText\n" + 
					"OR GD.Name like :searchText", nativeQuery = true)
	Page<GeoCityInfDto> getGeoCityList(Pageable sortedByIdDesc, String searchText);

}
