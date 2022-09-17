package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.CropdataCalendar;
import in.cropdata.cdtmasterdata.website.model.vo.CropdataCalendarVO;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;

@Repository
public interface CropdataCalendarRepository extends JpaRepository<CropdataCalendar, Integer> {

	@Query(value = "SELECT \n" + 
			"    GR.RegionID, CONCAT(GR.RegionID, '-', GR.Name) AS Name\n" + 
			"FROM\n" + 
			"    cdt_master_data.geo_region GR\n" + 
			"        LEFT JOIN\n" + 
			"    cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode) order by GR.RegionID", nativeQuery = true)
	List<GeoRegionDto> getGeoRegionList();
	
	@Query(value="SELECT\n" + 
			"						    cropdata_calendar.ID, \n" + 
			"						    cropdata_calendar.RegionID,  \n" + 
			"						    upper(GR.Name) as Region, \n" + 
			"						    upper(cropdata_calendar.Name) as Name, \n" + 
			"						    upper(cropdata_calendar.Description) as Description, \n" + 
			"						  date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') AS HolidayDate, \n" + 
			"							cropdata_calendar.Status \n" + 
			"						FROM\n" + 
			"						    cdt_master_data.cropdata_calendar cropdata_calendar\n" + 
			"						        INNER JOIN  \n" + 
			"						    cdt_master_data.geo_region GR ON (cropdata_calendar.RegionID = GR.RegionID)  \n" + 
			"						    where cropdata_calendar.ID like :searchText\n" + 
			"						    OR cropdata_calendar.RegionID like :searchText\n" + 
			"						    OR GR.Name like :searchText\n" + 
			"						    OR cropdata_calendar.Name like :searchText\n" + 
			"						    OR cropdata_calendar.Description like :searchText\n" + 
			"						    OR cropdata_calendar.Status like :searchText\n" + 
			"				            OR date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') like :searchText",countQuery = "SELECT\n" + 
					"						    cropdata_calendar.ID, \n" + 
					"						    cropdata_calendar.RegionID,  \n" + 
					"						    upper(GR.Name) as Region, \n" + 
					"						    upper(cropdata_calendar.Name) as Name, \n" + 
					"						    upper(cropdata_calendar.Description) as Description, \n" + 
					"						  date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') AS HolidayDate, \n" + 
					"							cropdata_calendar.Status \n" + 
					"						FROM\n" + 
					"						    cdt_master_data.cropdata_calendar cropdata_calendar\n" + 
					"						        INNER JOIN  \n" + 
					"						    cdt_master_data.geo_region GR ON (cropdata_calendar.RegionID = GR.RegionID)  \n" + 
					"						    where cropdata_calendar.ID like :searchText\n" + 
					"						    OR cropdata_calendar.RegionID like :searchText\n" + 
					"						    OR GR.Name like :searchText\n" + 
					"						    OR cropdata_calendar.Name like :searchText\n" + 
					"						    OR cropdata_calendar.Description like :searchText\n" + 
					"						    OR cropdata_calendar.Status like :searchText\n" + 
					"				            OR date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') like :searchText",nativeQuery = true)
	Page<CropdataCalendarVO> getHolidayListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="SELECT \n" + 
			"				    cropdata_calendar.ID,\n" + 
			"				    cropdata_calendar.RegionID,\n" + 
			"				    GR.Name Region,\n" + 
			"				    cropdata_calendar.Name,\n" + 
			"				    cropdata_calendar.Description,\n" + 
			"				    date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') AS HolidayDate,\n" + 
			"					cropdata_calendar.Status\n" + 
			"				FROM \n" + 
			"				    cdt_master_data.cropdata_calendar cropdata_calendar \n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_master_data.geo_region GR ON (cropdata_calendar.RegionID = GR.RegionID) \n" + 
			"				    where cropdata_calendar.RegionID = :regionId  \n" + 
			"					and (cropdata_calendar.ID like :searchText\n" + 
			"						    OR cropdata_calendar.RegionID like :searchText\n" + 
			"						    OR GR.Name like :searchText\n" + 
			"						    OR cropdata_calendar.Name like :searchText\n" + 
			"						    OR cropdata_calendar.Description like :searchText\n" + 
			"						    OR cropdata_calendar.Status like :searchText\n" + 
			"				            OR date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') like :searchText)",countQuery = "SELECT \n" + 
					"				    cropdata_calendar.ID,\n" + 
					"				    cropdata_calendar.RegionID,\n" + 
					"				    GR.Name Region,\n" + 
					"				    cropdata_calendar.Name,\n" + 
					"				    cropdata_calendar.Description,\n" + 
					"				    date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') AS HolidayDate,\n" + 
					"					cropdata_calendar.Status\n" + 
					"				FROM \n" + 
					"				    cdt_master_data.cropdata_calendar cropdata_calendar \n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_master_data.geo_region GR ON (cropdata_calendar.RegionID = GR.RegionID) \n" + 
					"				    where cropdata_calendar.RegionID = :regionId  \n" + 
					"					and (cropdata_calendar.ID like :searchText\n" + 
					"						    OR cropdata_calendar.RegionID like :searchText\n" + 
					"						    OR GR.Name like :searchText\n" + 
					"						    OR cropdata_calendar.Name like :searchText\n" + 
					"						    OR cropdata_calendar.Description like :searchText\n" + 
					"						    OR cropdata_calendar.Status like :searchText\n" + 
					"				            OR date_format(STR_TO_DATE(CONCAT(cropdata_calendar.Day,'-',cropdata_calendar.Month,'-',cropdata_calendar.Year), '%d-%m-%Y'), '%d-%m-%Y') like :searchText)",nativeQuery = true)
	Page<CropdataCalendarVO> getHolidayListByRegionId(Pageable sortedByIdDesc, Integer regionId, String searchText);
	
	@Query(value = "SELECT CONCAT(cropdata_calendar.Day,\n" + 
			"            '-',\n" + 
			"            cropdata_calendar.Month,\n" + 
			"            '-',\n" + 
			"            cropdata_calendar.Year) AS HolidayDate FROM cdt_master_data.cropdata_calendar cropdata_calendar\n" + 
			"            where cropdata_calendar.RegionID = ?", nativeQuery = true)
	String[] getHolidayDateList(Integer regionId);
	
	@Query(value="SELECT count(*) as count FROM cdt_master_data.cropdata_calendar cropdata_calendar\n" + 
			"where cropdata_calendar.RegionID = ? and cropdata_calendar.Day = ? and cropdata_calendar.Month = ? and cropdata_calendar.Year = ?",nativeQuery = true)
	Integer checkHolidayAlreadyExist(Integer regionId, Integer day, Integer month, Integer year);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_master_data.cropdata_calendar set Status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activateHoliday(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update cdt_master_data.cropdata_calendar set Status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactivateHoliday(Integer id);
}
