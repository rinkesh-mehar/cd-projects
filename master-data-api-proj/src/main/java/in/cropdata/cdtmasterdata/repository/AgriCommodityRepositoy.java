package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodity;

public interface AgriCommodityRepositoy extends JpaRepository<AgriCommodity, Integer> {

	Optional<AgriCommodity> findById(int commodityId);

	@Query(value = "SELECT distinct AC.Name as commodity, AC.ID as commodityId from regional_commodity RC \n"
			+ "LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n"
			+ "LEFT JOIN agri_commodity AC ON (AC.ID = RC.commodityId) where RC.SeasonID =?1 AND AC.Status='Active'\n"
			+ "order by AC.Name", nativeQuery = true)
	List<Map<String, Object>> getCommodityBySeasonId(int seasonId);

	@Query(value = "SELECT AC.ID,AC.Name,AC.ScientificName,AC.Status,ACG.Name as CommodityGroup,AC.CommodityGroupIndex,Logo FROM cdt_master_data.agri_commodity AC\n" + 
			"			left join cdt_master_data.agri_commodity_group ACG On (AC.CommodityGroupID = ACG.ID)\n" + 
			"			where AC.Name like :searchText\n" + 
			"			OR AC.ScientificName like :searchText\n" + 
			"            OR ACG.Name like :searchText\n" + 
			"            OR AC.CommodityGroupIndex like :searchText", countQuery = "SELECT AC.ID,AC.Name,AC.ScientificName,AC.Status,ACG.Name as CommodityGroup,AC.CommodityGroupIndex,Logo FROM cdt_master_data.agri_commodity AC\n" + 
					"			left join cdt_master_data.agri_commodity_group ACG On (AC.CommodityGroupID = ACG.ID)\n" + 
					"			where AC.Name like :searchText\n" + 
					"			OR AC.ScientificName like :searchText\n" + 
					"            OR ACG.Name like :searchText\n" + 
					"            OR AC.CommodityGroupIndex like :searchText", nativeQuery = true)
	Page<AgriCommodityInfo> findAllWithSearch(Pageable sortedByIdDesc, String searchText);

	@Query(value = "select distinct AP.ID as PhenophaseId,AP.Name as Phenophase from agri_phenophase_duration APD \n"
			+ "INNER JOIN agri_phenophase AP ON (APD.PhenophaseID = AP.ID)\n" + "where CommodityID = ?1\n"
			+ "order by AP.Name", nativeQuery = true)
	List<AgriFieldActivityInfDto> getPhenophaseByCommodity(int commodityId);

	@Query(value = "SELECT ID as commodityId, Name as commodity FROM agri_commodity "
			+ "WHERE ID IN (SELECT CommodityID FROM regional_commodity WHERE StateCode = ?1 AND SeasonID = ?2) order by Name ", nativeQuery = true)
	List<Map<String, Object>> getCommodityByStateCodeAndSeasonId(int stateCode, int seasonId);

	@Query(value = "SELECT DISTINCT rc.CommodityID as id, ac.Name as name FROM regional_commodity rc \n"
			+ "inner join agri_commodity ac on ac.ID = rc.CommodityID and ac.Status = 'Active' \n"
			+ "where rc.StateCode = ?1 ORDER BY ac.Name", nativeQuery = true)
	List<Map<String, Object>> getCommoditiesByStateCode(int stateCode);

	@Query(value = "select distinct ID, Name from agri_commodity order by Name", nativeQuery = true)
	List<Map<String, String>> getCommodities();

	@Query(value = "select distinct ID, Alias from agri_commodity_alias where CommodityID = 0 order by Alias", nativeQuery = true)
	List<Map<String, String>> getCommodityAlias();

	@Query(value = "select distinct ID, Alias from agri_commodity_alias where CommodityID = 0 "
			+ "order by Alias", countQuery = "select distinct ID, Alias from agri_commodity_alias where CommodityID = 0 ", nativeQuery = true)
	Page<AliasDto> getCommodityAliasWithPage(Pageable sortedByIdDesc);

	@Query(value = "select distinct ID, Name from agri_commodity order by Name", nativeQuery = true)
	List<AgriCommodityInfo> getCommodityOfPage();

	@Transactional
	@Modifying
	@Query(value = "update agri_commodity_alias set CommodityID = ?1 where ID = ?2", nativeQuery = true)
	int tagCommodityAlias(Integer commodityId, Integer aliasId);

	@Transactional
	@Modifying
	@Query(value = "insert into agri_commodity_alias (CommodityID, Alias) values (?1, ?2)", nativeQuery = true)
	int addCommodityAlias(Integer commodityId, String aliasName);

	@Query(value = "select count(ID) as count from agri_commodity_alias where CommodityID = ?1 and Alias = ?2 ", nativeQuery = true)
	int checkCommodityAlias(Integer commodityId, String aliasName);

	@Query(value = "select ID, Name, ScientificName, IsFocusCrop, Status, IsValid from cdt_master_data.agri_commodity ac \n"
			+ " where ac.Status IN ('Active', 'Approved', 'Inactive') Order by Name"
			, nativeQuery = true)
	List<AgriCommodityInfo> getAllCommodity();
}
