package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoTehsilInfDto;
import in.cropdata.cdtmasterdata.model.GeoTehsil;

public interface GeoTehsilRepository extends JpaRepository<GeoTehsil, Integer> {

	GeoTehsil findByTehsilCode(int tehsilCode);

	GeoTehsil findByNameAndDistrictCode(String TehsilName, int DistrictCode);

	@Query(value = "SELECT GT.ID,GT.StateCode,GT.DistrictCode,GT.TehsilCode,GT.Name,\n"
			+ "GT.Status,GT.CreatedAt,GT.UpdatedAt,GS.Name as State, GTT.Name as District\n" + "FROM geo_tehsil GT\n"
			+ "LEFT JOIN  geo_state GS ON (GT.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN geo_district GTT ON (GT.DistrictCode = GTT.DistrictCode) "
			+ "WHERE GT.DistrictCode = ?1 AND GT.Status = 'Active' ORDER BY GT.Name ASC ", nativeQuery = true)
	List<GeoTehsilInfDto> findAllByDistrictCode(int districtCode);

	@Query(value = "SELECT GT.ID,GT.StateCode,GT.DistrictCode,GT.TehsilCode,GT.Name,\n"
			+ "GT.Status,GT.CreatedAt,GT.UpdatedAt,GS.Name as State, GTT.Name as District\n" + "FROM geo_tehsil GT\n"
			+ "LEFT JOIN  geo_state GS ON (GT.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN geo_district GTT ON (GT.DistrictCode = GTT.DistrictCode)", nativeQuery = true)
	List<GeoTehsilInfDto> getGeoTehsilList();

	@Query(value = "SELECT GT.ID,GT.StateCode,GT.DistrictCode,GT.TehsilCode,GT.Name,\n"
			+ "GT.Status,GT.CreatedAt,GT.UpdatedAt,GS.Name as State, GTT.Name as District FROM geo_tehsil GT\n"
			+ "LEFT JOIN  geo_state GS ON (GT.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN geo_district GTT ON (GT.DistrictCode = GTT.DistrictCode)\n"
			+ "where GT.Name like :searchText\n" + "OR GT.TehsilCode like :searchText\n"
			+ "OR GS.Name like :searchText\n"
			+ "OR GTT.Name like :searchText", countQuery = "SELECT count(GT.ID) as Count\n"
					+ " FROM geo_tehsil GT\n"
					+ "LEFT JOIN  geo_state GS ON (GT.StateCode = GS.StateCode)\n"
					+ "LEFT JOIN geo_district GTT ON (GT.DistrictCode = GTT.DistrictCode)\n"
					+ "where GT.Name like :searchText\n" + "OR GT.TehsilCode like :searchText\n"
					+ "OR GS.Name like :searchText\n" + "OR GTT.Name like :searchText", nativeQuery = true)
	Page<GeoTehsilInfDto> getGeoTehsilList(Pageable pageable, String searchText);

	/** For Handling Tehsil Alias */
	@Query(value = "select distinct gta.ID, gta.Alias, gta.StateCode, gs.Name as StateName, gta.DistrictCode, gd.Name as DistrictName \n"
			+ "from geo_tehsil_alias as gta inner join geo_state as gs on gs.StateCode = gta.StateCode \n"
			+ "inner join geo_district as gd on gd.DistrictCode = gta.DistrictCode and gd.StateCode = gta.StateCode \n"
			+ "where gta.TehsilCode = 0 and gta.StateCode is not null and gta.DistrictCode is not null", countQuery = "select distinct gta.ID, "
					+ "gta.Alias, gta.StateCode, gs.Name as StateName, gta.DistrictCode, gd.Name as DistrictName \n"
					+ "from geo_tehsil_alias as gta inner join geo_state as gs on gs.StateCode = gta.StateCode \n"
					+ "inner join geo_district as gd on gd.DistrictCode = gta.DistrictCode and gd.StateCode = gta.StateCode \n"
					+ "where gta.TehsilCode = 0 and gta.StateCode is not null and gta.DistrictCode is not null", nativeQuery = true)
	Page<AliasDto> getTehsilAliasWithPage(Pageable sortedByAliasAsc);

	@Query(value = "select distinct gt.TehsilCode, gt.DistrictCode, gt.StateCode, gt.Name \n"
			+ "from geo_tehsil as gt where gt.StateCode in (select distinct StateCode from geo_tehsil_alias where TehsilCode = 0) \n"
			+ "and gt.DistrictCode in (select distinct DistrictCode from geo_tehsil_alias where TehsilCode = 0) \n"
			+ "order by gt.Name", nativeQuery = true)
	List<GeoTehsilInfDto> getTehsils();

	@Query(value = "select count(ID) as count from geo_tehsil_alias where StateCode = ?1 and DistrictCode = ?2 and TehsilCode = ?3 and Alias = ?4 ", nativeQuery = true)
	int checkTehsilAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, String aliasName);

	@Transactional
	@Modifying
	@Query(value = "update geo_tehsil_alias set TehsilCode = ?3 where ID = ?4 and StateCode = ?1 and DistrictCode = ?2 ", nativeQuery = true)
	int tagTehsilAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, Integer aliasId);

	@Transactional
	@Modifying
	@Query(value = "insert into geo_tehsil_alias (StateCode, DistrictCode, TehsilCode, Alias) values (?1, ?2, ?3, ?4) ", nativeQuery = true)
	int addTehsilAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, String aliasName);

}
