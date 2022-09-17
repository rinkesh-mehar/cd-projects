package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.dto.interfaces.TaskTypeSpecificationsInfDto;
import in.cropdata.cdtmasterdata.model.TaskTypeSpecifications;

@Repository
public interface TaskTypeSpecificationsRepository extends JpaRepository<TaskTypeSpecifications, Integer> {
	
	
	@Query(value="  SELECT tts.ID as id,tts.StateID as state_id,tts.SeasonID as season_id,tts.CommodityID as commodity_id,\n" + 
			"tts.VarietyID as variety_id,tts.PhenophaseID as phenophase_id,tts.PushBackLimit as pushback_limit,\n" + 
			"tts.Priority as priority,tts.TaskTime as task_time, tts.TaskTypeID as task_type_id,tts.SpotDependency as spot_dependency,\n" + 
			"tts.CreatedAt as created_at,tts.UpdatedAt as updated_at,tts.Status as status\n" + 
			"FROM cdt_master_data.task_type_specifications tts  where tts.Status in ('Active','Inactive','Deleted')  ",nativeQuery = true)
	public List<Map<String,Object>> getRegionalTaskTypeSpecificationList();
	
	@Query(value="SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " + 
			"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " + 
			"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.IsValid, tts.ErrorMessage " +
			"FROM cdt_master_data.task_type_specifications tts " + 
			"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " + 
			"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " + 
			"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " + 
			"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " + 
			"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " + 
			"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " + 
			"where s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " + 
			"tt.name like :searchText",
			countQuery = "SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " + 
					"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " + 
					"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.IsValid, tts.ErrorMessage " +
					"FROM cdt_master_data.task_type_specifications tts " + 
					"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " + 
					"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " + 
					"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " + 
					"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " + 
					"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " + 
					"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " + 
					"where s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " + 
					"tt.name like :searchText",nativeQuery = true)
	Page<TaskTypeSpecificationsInfDto> getRegionalTaskTypeSpecificationList(Pageable pageable,String searchText);
	
	@Query(value="SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " + 
			"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " + 
			"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.IsValid " +
			"FROM cdt_master_data.task_type_specifications_missing tts " + 
			"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " + 
			"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " + 
			"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " + 
			"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " + 
			"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " + 
			"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " + 
			"where s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " + 
			"tt.name like :searchText",
			countQuery = "SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " + 
					"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " + 
					"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.IsValid " +
					"FROM cdt_master_data.task_type_specifications_missing tts " + 
					"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " + 
					"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " + 
					"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " + 
					"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " + 
					"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " + 
					"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " + 
					"where s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " + 
					"tt.name like :searchText",nativeQuery = true)
	Page<TaskTypeSpecificationsInfDto> getRegionalTaskTypeSpecificationMissingList(Pageable pageable,String searchText);

	@Query(value="SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " +
			"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " +
			"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.ErrorMessage " +
			"FROM cdt_master_data.task_type_specifications tts " +
			"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " +
			"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " +
			"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " +
			"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " +
			"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " +
			"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " +
			"where tts.IsValid = 0 and (s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " +
			"tt.name like :searchText)",
			countQuery = "SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " +
					"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " +
					"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status, tts.ErrorMessage " +
					"FROM cdt_master_data.task_type_specifications tts " +
					"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " +
					"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " +
					"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " +
					"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " +
					"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " +
					"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " +
					"tts.IsValid = 0 and (s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " +
					"tt.name like :searchText)",nativeQuery = true)
	Page<TaskTypeSpecificationsInfDto> getRegionalTaskTypeSpecificationListInvalidated(Pageable pageable,String searchText);
	
	@Query(value="SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " +
			"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " +
			"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status " +
			"FROM cdt_master_data.task_type_specifications_missing tts " +
			"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " +
			"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " +
			"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " +
			"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " +
			"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " +
			"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " +
			"where tts.IsValid = 0 and (s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " +
			"tt.name like :searchText)",
			countQuery = "SELECT tts.ID,tts.StateID,s.Name as StateName ,tts.SeasonID, ass.Name as SeasonName ,tts.CommodityID, ac.Name as CommodityName , " +
					"tts.VarietyID, av.Name as VarietyName ,tts.PhenophaseID, ap.Name as PhenophaseName,tts.PushBackLimit,tts.Priority,tts.TaskTime, " +
					"tts.TaskTypeID, tt.name as TaskTypeName ,tts.SpotDependency,tts.CreatedAt,tts.UpdatedAt,tts.Status " +
					"FROM cdt_master_data.task_type_specifications_missing tts " +
					"inner join cdt_master_data.geo_state s on s.ID = tts.StateID " +
					"inner join cdt_master_data.agri_season ass on ass.ID = tts.SeasonID " +
					"inner join cdt_master_data.agri_commodity ac on ac.ID = tts.CommodityID " +
					"inner join cdt_master_data.agri_variety av on av.ID = tts.VarietyID " +
					"inner join cdt_master_data.agri_phenophase ap on ap.ID = tts.PhenophaseID " +
					"inner join drkrishi.task_type tt on tt.id = tts.TaskTypeID " +
					"tts.IsValid = 0 and (s.Name like :searchText OR ass.Name like :searchText OR ac.Name like :searchText OR av.Name like :searchText OR ap.Name like :searchText OR " +
					"tt.name like :searchText)",nativeQuery = true)
	Page<TaskTypeSpecificationsInfDto> getRegionalTaskTypeSpecificationMissingListInvalidated(Pageable pageable,String searchText);
	

	@Query(value="select distinct apd.StateCode as stateID ,gs.Name as stateName from cdt_master_data.agri_phenophase_duration apd\n" + 
			"inner join  cdt_master_data.geo_state gs on gs.ID =apd.StateCode  where gs.Status='Active' order by gs.Name",nativeQuery = true)
	List<Map<String,Object>> getStateList();
	
	@Query(value="select distinct apd.SeasonID as seasonID,gs.Name as seasonName from cdt_master_data.agri_phenophase_duration apd\n" + 
			"inner join  cdt_master_data.agri_season gs on gs.ID =apd.SeasonID  where gs.Status='Active' order by gs.Name",nativeQuery = true)
	List<Map<String,Object>> getSeasonList();
	
	@Query(value = "select distinct apd.CommodityID as commodityID,gs.Name as commodityName  from cdt_master_data.agri_phenophase_duration apd\n" + 
			"inner join  cdt_master_data.agri_commodity gs on gs.ID =apd.CommodityID  where gs.Status='Active' order by gs.Name",nativeQuery = true)
	List<Map<String,Object>> getCommodityList();
	
	@Query(value = "select distinct apd.VarietyID as varietyID,gs.Name as varietyName from cdt_master_data.agri_phenophase_duration apd\n" + 
			"inner join  cdt_master_data.agri_variety gs on gs.ID =apd.VarietyID  where gs.Status='Active' and apd.CommodityID =?1 order by gs.Name",nativeQuery = true)
	List<Map<String,Object>> getVarietyList(Integer commodityID);
	
	@Query(value = "select distinct apd.PhenophaseID as phenophaseID,gs.Name as phenophaseName from cdt_master_data.agri_phenophase_duration apd\n" + 
			"inner join  cdt_master_data.agri_phenophase gs on gs.ID =apd.PhenophaseID  where gs.Status='Active' and apd.CommodityID =?1 and apd.VarietyID =?2 order by  gs.Name",nativeQuery = true)
	List<Map<String,Object>> getPhenophaseList(Integer commodityID ,Integer varietyID);
}
