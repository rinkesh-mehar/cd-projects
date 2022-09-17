package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.RegionalSeasonInfDto;
import in.cropdata.cdtmasterdata.model.RegionalSeason;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalSeasonRepository extends JpaRepository<RegionalSeason, Integer> {

	@Query(value = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek as StartWeek, RS.SeasonEndWeek as EndWeek,RS.UpdatedAt,\n"
			+ "RS.CreatedAt,RS.Status, GR.Name as Region,ASEA.Name as Season\n" + "FROM regional_season RS \n"
			+ "LEFT JOIN geo_region GR ON (RS.RegionID = GR.StateCode)\n"
			+ "LEFT JOIN geo_state GS ON (RS.StateCode = GR.RegionID)\n"
			+ "LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)", nativeQuery = true)
	List<RegionalSeasonInfDto> getRegionalSeasonList();

	@Query(value = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek as StartWeek, RS.SeasonEndWeek as EndWeek,\n" +
			"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season, RS.IsValid, RS.ErrorMessage FROM regional_season RS \n" +
			"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
			"			 where ASEA.Name like :searchText\n" +
			"			 OR GS.Name like :searchText\n" +
			"			 OR GR.Name like :searchText\n" +
			"			 OR RS.SeasonStartWeek like :searchText\n" +
			"			 OR RS.SeasonEndWeek like :searchText",
			countQuery = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek,RS.SeasonEndWeek,\n" +
					"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season, RS.IsValid, RS.ErrorMessage FROM regional_season RS \n" +
					"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
					"			 where ASEA.Name like :searchText\n" +
					"			 OR GS.Name like :searchText\n" +
					"			 OR GR.Name like :searchText\n" +
					"			 OR RS.SeasonStartWeek like :searchText\n" +
					"			 OR RS.SeasonEndWeek like :searchText", nativeQuery = true)
	Page<RegionalSeasonInfDto> getRegionalSeasonList(Pageable pageable,String searchText);
	
	@Query(value = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek as StartWeek, RS.SeasonEndWeek as EndWeek,\n" +
			"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season FROM regional_season_missing RS \n" + 
			"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" + 
			"			 where ASEA.Name like :searchText\n" + 
			"			 OR GS.Name like :searchText\n" + 
			"			 OR GR.Name like :searchText\n" + 
			"			 OR RS.SeasonStartWeek like :searchText\n" +
			"			 OR RS.SeasonEndWeek like :searchText",
			countQuery = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek,RS.SeasonEndWeek,\n" +
					"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season FROM regional_season_missing RS \n" + 
					"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" + 
					"			 where ASEA.Name like :searchText\n" + 
					"			 OR GS.Name like :searchText\n" + 
					"			 OR GR.Name like :searchText\n" + 
					"			 OR RS.SeasonStartWeek like :searchText\n" +
					"			 OR RS.SeasonEndWeek like :searchText", nativeQuery = true)
	Page<RegionalSeasonInfDto> getRegionalSeasonMissingList(Pageable pageable,String searchText);

	@Query(value = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek as StartWeek, RS.SeasonEndWeek as EndWeek,\n" +
			"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season, RS.IsValid, RS.ErrorMessage FROM regional_season RS \n" +
			"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
			"			 where RS.IsValid = 0 and ( ASEA.Name like :searchText\n" +
			"			 OR GS.Name like :searchText\n" +
			"			 OR GR.Name like :searchText\n" +
			"			 OR RS.SeasonStartWeek like :searchText\n" +
			"			 OR RS.SeasonEndWeek like :searchText)",
			countQuery = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek,RS.SeasonEndWeek,\n" +
					"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season, RS.IsValid, RS.ErrorMessage FROM regional_season RS \n" +
					"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
					"			  where RS.IsValid = 0 and (ASEA.Name like :searchText\n" +
					"			 OR GS.Name like :searchText\n" +
					"			 OR GR.Name like :searchText\n" +
					"			 OR RS.SeasonStartWeek like :searchText\n" +
					"			 OR RS.SeasonEndWeek like :searchText)", nativeQuery = true)
	Page<RegionalSeasonInfDto> getRegionalSeasonListInvalidated(Pageable pageable,String searchText);
	
	@Query(value = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek as StartWeek, RS.SeasonEndWeek as EndWeek,\n" +
			"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season FROM regional_season_missing RS \n" +
			"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
			"			 where RS.IsValid = 0 and ( ASEA.Name like :searchText\n" +
			"			 OR GS.Name like :searchText\n" +
			"			 OR GR.Name like :searchText\n" +
			"			 OR RS.SeasonStartWeek like :searchText\n" +
			"			 OR RS.SeasonEndWeek like :searchText)",
			countQuery = "SELECT RS.ID,RS.RegionID,RS.StateCode,RS.SeasonID,RS.SeasonStartWeek,RS.SeasonEndWeek,\n" +
					"			RS.Status,GS.Name as State,GR.Name as Region,ASEA.Name as Season FROM regional_season_missing RS \n" +
					"			LEFT JOIN geo_region GR ON (RS.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN geo_state GS ON (RS.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASEA ON (RS.SeasonID = ASEA.ID)\n" +
					"			  where RS.IsValid = 0 and (ASEA.Name like :searchText\n" +
					"			 OR GS.Name like :searchText\n" +
					"			 OR GR.Name like :searchText\n" +
					"			 OR RS.SeasonStartWeek like :searchText\n" +
					"			 OR RS.SeasonEndWeek like :searchText)", nativeQuery = true)
	Page<RegionalSeasonInfDto> getRegionalSeasonMissingListInvalidated(Pageable pageable,String searchText);

}
