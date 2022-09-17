package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.model.GeoState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface GeoStateRepository extends JpaRepository<GeoState, Integer> {

	GeoState findByStateCode(int stateCode);

	GeoState findByName(String stateName);

	List<GeoState> findByCountryCode(Integer countryId);

	@Query(value = "SELECT GS.ID,GS.StateCode,GS.CountryCode,GS.Name,GS.Status,GC.Name as Country  FROM geo_state GS\n"
			+ "LEFT JOIN geo_country GC ON (GS.CountryCode = GC.CountryCode)\n"
			+ "WHERE GS.Status = 'Active' ORDER BY GS.NAME ASC", nativeQuery = true)
	List<GeoStateInfDto> getActiveGeoStateList();

	@Query(value = "SELECT gs.Id,gs.StateCode,gs.Name FROM cdt_master_data.geo_state gs WHERE gs.StateCode = ?1 "
			+ " or gs.Name = ?2", nativeQuery = true)
	List<GeoStateInfDto> existState(int stateCode, String stateName);

	@Query(value = "SELECT GS.ID,GS.StateCode,GS.CountryCode,GS.Name,\n" + "GS.Status,GS.CreatedAt,GS.UpdatedAt,\n"
			+ "GC.Name as Country  FROM geo_state GS\n"
			+ "LEFT JOIN geo_country GC ON (GS.CountryCode = GC.CountryCode) order by GS.Name", nativeQuery = true)
	List<GeoStateInfDto> getGeoStateList();

	@Query(value = "select distinct StateCode, Name from geo_state order by Name", nativeQuery = true)
	List<Map<String, String>> getStates();

	@Query(value = "select distinct ID, Alias from geo_state_alias where StateCode = 0 order by Alias", nativeQuery = true)
	List<Map<String, String>> getStateAlias();

	@Query(value = "select distinct StateCode, Name from geo_state order by Name", nativeQuery = true)
	List<GeoStateInfDto> getStateList();

	@Query(value = "select distinct ID, Alias from geo_state_alias where StateCode = 0 order by Alias",
			countQuery = "select distinct ID, Alias from geo_state_alias where StateCode = 0 order by Alias",nativeQuery = true)
	Page<AliasDto> getStateAliasWithPage(Pageable sortedByIdDesc);

	@Transactional
	@Modifying
	@Query(value = "update geo_state_alias set StateCode = ?1 where ID = ?2", nativeQuery = true)
	int tagStateAlias(Integer stateCode, Integer aliasId);

	@Transactional
	@Modifying
	@Query(value = "insert into geo_state_alias (StateCode, Alias) values (?1, ?2)", nativeQuery = true)
	int addStateAlias(Integer stateCode, String aliasName);

	@Query(value = "select count(ID) as count from geo_state_alias where StateCode = ?1 and Alias = ?2 ", nativeQuery = true)
	int checkStateAlias(Integer stateCode, String aliasName);
	
	@Query(value="SELECT GS.ID,GS.StateCode,GS.CountryCode,GS.Name,GS.Status,GC.Name as Country  \n" + 
			"FROM geo_state GS LEFT JOIN geo_country GC ON (GS.CountryCode = GC.CountryCode) \n" + 
			"where GS.ID like :searchText OR GS.StateCode like :searchText OR GS.CountryCode like :searchText OR GS.Name like :searchText OR GS.Status like :searchText OR GC.Name like :searchText",countQuery = "SELECT count(GS.ID) as Count\n" + 
					"FROM geo_state GS LEFT JOIN geo_country GC ON (GS.CountryCode = GC.CountryCode) \n" + 
					"where GS.ID like :searchText OR GS.StateCode like :searchText OR GS.CountryCode like :searchText OR GS.Name like :searchText OR GS.Status like :searchText OR GC.Name like :searchText",nativeQuery = true)
	Page<GeoStateInfDto> getStateListByPagenation(Pageable sortedByIdDesc, String searchText);

}
