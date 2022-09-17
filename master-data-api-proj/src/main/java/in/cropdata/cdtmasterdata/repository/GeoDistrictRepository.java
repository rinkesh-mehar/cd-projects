package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeoDistrictInfDto;
import in.cropdata.cdtmasterdata.model.GeoDistrict;

public interface GeoDistrictRepository extends JpaRepository<GeoDistrict, Integer> {

	GeoDistrict findByNameAndStateCode(String districtName, int stateCode);

	GeoDistrict findByDistrictCode(int districtCode);

	@Query(value = "select GD.ID,GD.StateCode,GD.DistrictCode,GD.Name,GD.UpdatedAt,GD.CreatedAt,GD.Status,GS.Name as State \n"
			+ "FROM geo_district GD \n" + "LEFT JOIN geo_state GS ON (GD.StateCode = GS.StateCode)"
			+ "where GD.StateCode = ?1 AND GD.Status = 'Active' ORDER BY GD.NAME ASC ", nativeQuery = true)
	List<GeoDistrict> findAllByStateCode(int stateCode);

	@Query(value = "select GD.ID,GD.StateCode,GD.DistrictCode,GD.Name,GD.UpdatedAt,GD.CreatedAt,GD.Status,GS.Name as State \n"
			+ "FROM geo_district GD \n" + "LEFT JOIN geo_state GS ON (GD.StateCode = GS.StateCode)", nativeQuery = true)
	List<GeoDistrictInfDto> getGeoDistrictList();

	@Query(value = "SELECT GD.ID,GD.StateCode,GD.DistrictCode,GD.Name,GD.Status, GS.Name as State FROM geo_district GD\n"
			+ "Left Join geo_state GS ON(GS.StateCode = GD.StateCode)\n" + "where GD.Name like :searchText\n"
			+ "OR GD.Name like :searchText\n"
			+ "OR GS.Name like :searchText", countQuery = "SELECT GD.ID,GD.StateCode,GD.DistrictCode,GD.Name,GD.Status, GS.Name as State FROM geo_district GD\n"
					+ "Left Join geo_state GS ON(GS.StateCode = GD.StateCode)\n" + "where GD.Name like :searchText\n"
					+ "OR GD.Name like :searchText\n" + "OR GS.Name like :searchText", nativeQuery = true)
	Page<GeoDistrictInfDto> getGeoDistrictList(Pageable sortedByIdDesc, String searchText);

	@Query(value = "select distinct gda.ID as AliasID , gda.Alias, gda.StateCode, gs.Name as StateName \n"
			+ "from geo_district_alias as gda inner join geo_state as gs on gs.StateCode = gda.StateCode\n"
			+ "where gda.DistrictCode = 0 and gda.StateCode is not null order by gda.Alias", nativeQuery = true)
	List<Map<String, String>> getDistrictAlias();

	@Query(value = "select distinct gda.ID , gda.Alias, gda.StateCode, gs.Name as StateName \n"
			+ "from cdt_master_data.geo_district_alias as gda inner join cdt_master_data.geo_state as gs on gs.StateCode = gda.StateCode\n"
			+ "where gda.DistrictCode = 0 and gda.StateCode is not null",
			countQuery = "select distinct gda.ID as AliasID , gda.Alias, gda.StateCode, gs.Name as StateName \n"
			+ "from cdt_master_data.geo_district_alias as gda inner join cdt_master_data.geo_state as gs on gs.StateCode = gda.StateCode\n"
			+ "where gda.DistrictCode = 0 and gda.StateCode is not null", nativeQuery = true)
	Page<AliasDto> getDistrictAliasWithPage(Pageable sortedByIdDesc);

	@Query(value = "select distinct gd.DistrictCode, gd.Name from geo_district as gd \n"
			+ "where gd.StateCode in (select distinct StateCode from geo_district_alias where DistrictCode = 0) "
			+ "order by gd.Name", nativeQuery = true)
	List<Map<String, String>> getDistricts();

	@Query(value = "select distinct gd.DistrictCode,gd.StateCode, gd.Name from geo_district as gd \n"
			+ "where gd.StateCode in (select distinct StateCode from geo_district_alias where DistrictCode = 0) "
			+ "order by gd.Name", nativeQuery = true)
	List<GeoDistrictInfDto> getDistrictsWithPage();

	@Query(value = "select gs.Id stateCode, gs.Name as name from  geo_state as gs order by gs.Name", nativeQuery = true)
	List<Map<String, String>> getStates();

	@Query(value = "select distinct gd.ID districtCode, gd.Name name from geo_district as gd where gd.StateCode = ?1 " +
			"order by Name", nativeQuery = true)
	List<Map<String, String>> getDistrictsByStateCode(Integer stateCode);

	@Transactional
	@Modifying
	@Query(value = "update geo_district_alias set DistrictCode = ?2 where ID = ?3 and StateCode = ?1", nativeQuery = true)
	int tagDistrictAlias(Integer stateCode, Integer districtCode, Integer aliasId);

	@Transactional
	@Modifying
	@Query(value = "insert into geo_district_alias (StateCode, DistrictCode, Alias) values (?1, ?2, ?3)", nativeQuery = true)
	int addDistrictAlias(Integer stateCode, Integer districtCode, String aliasName);

	@Query(value = "select count(ID) as count from geo_district_alias where StateCode = ?1 and DistrictCode = ?2 and Alias = ?2 ", nativeQuery = true)
	int checkDistrictAlias(Integer stateCode, Integer districtCode, String aliasName);
}
