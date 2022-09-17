package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoVillageInfDto;
import in.cropdata.cdtmasterdata.model.GeoVillage;

public interface GeoVillageRepository extends JpaRepository<GeoVillage, Integer> {

	GeoVillage findByVillageCode(int villageCode);

	@Query(value = "SELECT GV.ID, GV.StateCode, GV.DistrictCode, GV.TehsilCode, GV.PanchayatCode, GV.VillageCode, \n"
			+ "GV.RegionID, GV.Name, GV.Status, GS.Name as State, GTT.Name as District, GT.Name as Tehsil FROM geo_village GV \n"
			+ "LEFT JOIN geo_tehsil GT ON (GV.TehsilCode = GT.TehsilCode) \n"
			+ "LEFT JOIN geo_state GS ON (GV.StateCode = GS.StateCode) \n"
			+ "LEFT JOIN geo_district GTT ON (GV.DistrictCode = GTT.DistrictCode)", nativeQuery = true)
	List<GeoVillageInfDto> getGeoVillageList();

	@Query(value = "SELECT GV.ID, GV.StateCode, GV.DistrictCode, GV.TehsilCode, GV.PanchayatCode, GV.VillageCode, \n"
			+ "GV.RegionID, GV.Name, GV.Status, GS.Name as State, GTT.Name as District, GT.Name as Tehsil FROM geo_village GV \n"
			+ "LEFT JOIN geo_tehsil GT ON (GV.TehsilCode = GT.TehsilCode) \n"
			+ "LEFT JOIN geo_state GS ON (GV.StateCode = GS.StateCode) \n"
			+ "LEFT JOIN geo_district GTT ON (GV.DistrictCode = GTT.DistrictCode) \n"
			+ "where GV.Name like :searchText OR GV.VillageCode like :searchText \n"
			+ "OR GS.Name like :searchText OR GTT.Name like :searchText \n"
			+ "OR GT.Name like :searchText", countQuery = "SELECT count(GV.ID) as Count \n"
					+ "FROM geo_village GV \n"
					+ "LEFT JOIN geo_tehsil GT ON (GV.TehsilCode = GT.TehsilCode) \n"
					+ "LEFT JOIN geo_state GS ON (GV.StateCode = GS.StateCode) \n"
					+ "LEFT JOIN geo_district GTT ON (GV.DistrictCode = GTT.DistrictCode) \n"
					+ "where GV.Name like :searchText OR GV.VillageCode like :searchText \n"
					+ "OR GS.Name like :searchText OR GTT.Name like :searchText \n"
					+ "OR GT.Name like :searchText", nativeQuery = true)
	Page<GeoVillageInfDto> getGeoVillageList(Pageable pageable, String searchText);

	// Optional<GeoVillage> findByVillageCode(String village);

	/** For Handling Village Alias */
	@Query(value = "select distinct gva.ID, gva.Alias, gva.StateCode, gs.Name as StateName, gva.DistrictCode, gd.Name as DistrictName, gva.TehsilCode, gt.Name as TehsilName \n"
			+ "from geo_village_alias as gva inner join geo_state as gs on gs.StateCode = gva.StateCode \n"
			+ "inner join geo_district as gd on gd.DistrictCode = gva.DistrictCode and gd.StateCode = gva.StateCode \n"
			+ "inner join geo_tehsil as gt on gt.TehsilCode = gva.TehsilCode and gt.DistrictCode = gva.DistrictCode and gt.StateCode = gva.StateCode \n"
			+ "where gva.VillageCode = 0 and gva.StateCode is not null and gva.DistrictCode is not null and gva.TehsilCode is not null", countQuery = "select distinct gva.ID, "
					+ "gva.Alias, gva.StateCode, gs.Name as StateName, gva.DistrictCode, gd.Name as DistrictName, gva.TehsilCode, gt.Name as TehsilName \n"
					+ "from geo_village_alias as gva inner join geo_state as gs on gs.StateCode = gva.StateCode \n"
					+ "inner join geo_district as gd on gd.DistrictCode = gva.DistrictCode and gd.StateCode = gva.StateCode \n"
					+ "inner join geo_tehsil as gt on gt.TehsilCode = gva.TehsilCode and gt.DistrictCode = gva.DistrictCode and gt.StateCode = gva.StateCode \n"
					+ "where gva.VillageCode = 0 and gva.StateCode is not null and gva.DistrictCode is not null and gva.TehsilCode is not null", nativeQuery = true)
	Page<AliasDto> getVillageAliasWithPage(Pageable sortedByAliasAsc);

	@Query(value = "select distinct gv.VillageCode, gv.TehsilCode, gv.DistrictCode, gv.StateCode, concat(gv.Name, ' (', gv.VillageCode20,')') Name \n"
			+ "from geo_village as gv where gv.StateCode in (select distinct StateCode from geo_village_alias where VillageCode = 0) \n"
			+ "and gv.DistrictCode in (select distinct DistrictCode from geo_village_alias where VillageCode = 0) \n"
			+ "and gv.TehsilCode in (select distinct TehsilCode from geo_village_alias where VillageCode = 0) \n"
			+ "order by Name", nativeQuery = true)
	List<GeoVillageInfDto> getVillages();

	@Query(value = "select distinct VillageCode, Name from geo_village where TehsilCode = ?1 order by Name", nativeQuery = true)
	List<GeoVillageInfDto> getGeoVillagesByTehsilCode(Integer tehsilCode);

	@Query(value = "select count(ID) as count from geo_village_alias where StateCode = ?1 and DistrictCode = ?2 "
			+ "and TehsilCode = ?3 and VillageCode = ?4 and Alias = ?5 ", nativeQuery = true)
	int checkVillageAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, Integer villageCode,
			String aliasName);

	@Transactional
	@Modifying
	@Query(value = "update geo_village_alias set VillageCode = ?4 where ID = ?5 and StateCode = ?1 and DistrictCode = ?2 and TehsilCode = ?3 ", nativeQuery = true)
	int tagVillageAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, Integer villageCode,
			Integer aliasId);

	@Transactional
	@Modifying
	@Query(value = "insert into geo_village_alias (StateCode, DistrictCode, TehsilCode, VillageCode, Alias) values (?1, ?2, ?3, ?4, ?5) ", nativeQuery = true)
	int addVillageAlias(Integer stateCode, Integer districtCode, Integer tehsilCode, Integer villageCode,
			String aliasName);

}
