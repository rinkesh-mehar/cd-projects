package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeoRegionInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStateInf;
import in.cropdata.cdtmasterdata.model.GeoRegion;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;

public interface GeoRegionRepository extends JpaRepository<GeoRegion, Integer> {

	Optional<GeoRegion> findByRegionId(int regionId);

	void deleteByRegionId(int regionId);

	@Query(value = "SELECT GR.RegionID,GR.StateCode,GR.Latitude,GR.Longitude,GR.Name,\n"
			+ "GR.Description,GR.Onboarded,GR.Status,GR.CreatedAt,GR.UpdatedAt,\n"
			+ "GS.Name as State  FROM geo_region GR\n"
			+ "LEFT JOIN geo_state GS ON (GR.StateCode = GS.StateCode) order by GR.Name", nativeQuery = true)
	List<GeoRegionInfDto> getGeoRegionList();

	@Query(value = "SELECT GR.RegionID,GR.StateCode,GR.Name,\n" + 
			"			GR.Description,GR.Status,\n" + 
			"			GS.Name as State  FROM geo_region GR LEFT JOIN geo_state GS ON (GR.StateCode = GS.StateCode)\n" + 
			"			where GR.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Description like :searchText",
			countQuery = "SELECT GR.RegionID,GR.StateCode,GR.Name,\n" + 
					"			GR.Description,GR.Status,\n" + 
					"			GS.Name as State  FROM geo_region GR LEFT JOIN geo_state GS ON (GR.StateCode = GS.StateCode)\n" + 
					"			where GR.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Description like :searchText", nativeQuery = true)
	Page<GeoRegionInfDto> getGeoRegionList(Pageable pageable, String searchText);

	@Query(value = "SELECT GS.ID,GS.Status, GS.Name AS State FROM geo_region gR INNER JOIN geo_state GS ON gR.StateCode = GS.StateCode WHERE gR.RegionID = :regionId",nativeQuery = true)
	List<RegionalStateInf> findAllRegionByStateCode(int regionId);
	
	List<GeoRegion> findAllByStateCode(int StateCode);
	
	@Query(value = "select RegionID,Name from geo_region gr where gr.StateCode = ?1 order by Name",nativeQuery = true)
	List<GeoRegionDto> getRegionByStateCode(int StateCode);

}

