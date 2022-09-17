package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.dto.CityVillagePinDTO;
import in.cropdata.farmers.app.masters.model.GeoVillage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.farmers.app.masters.repository
 * @date 05/02/21
 * @time 11:55 AM
 */
@Repository
public interface GeoVillageRepository extends JpaRepository<GeoVillage, Integer>
{
    @Query(value = "select villageCode, panchayatCode, tehsilCode, regionID, name, ID from cdt_master_data.geo_village where status = 'Active' " +
            "and panchayatCode= ?1 and name != '0' order by name", nativeQuery = true)
    List<GeoVillage> findGeoVillageByPanchayatCode(Integer panchayatCode);
    
    
    @Query(value = "Select v.StateCode, v.DistrictCode from cdt_master_data.geo_village v where v.VillageCode =?1  ",nativeQuery = true)
	Map<String,Object> getFarmerStateAndDistrictCodeForDrkrishi(Integer villageCode);

//    @Query(value = "select c.ID, c.Name, d.Name as DistrictName, 'city' as type \n" +
//            "   from cdt_master_data.geo_city as c inner join cdt_master_data.geo_district as d on d.DistrictCode = c.DistrictCode \n" +
//            "   where c.StateCode= :stateCode and c.Name like :searchText\n" +
//            "   union all select v.ID, v.Name, d.Name as DistrictName, 'vill' as type \n" +
//            "   from cdt_master_data.geo_village as v inner join cdt_master_data.geo_district as d on d.DistrictCode = v.DistrictCode \n" +
//            "   where v.StateCode= :stateCode and (v.Name like :searchText  or v.Pin like :searchText)\n" +
//            "   limit 10;", nativeQuery = true)
//    List<CityVillagePinDTO> findBystateCodeAndSearchedCharacter(Integer stateCode, String searchText);
    
    
    
    @Query(value = "SELECT \n"
    		+ "    c.ID,\n"
    		+ "    c.Name,\n"
    		+ "    d.DistrictCode,\n"
    		+ "    d.Name AS DistrictName,\n"
    		+ "    d.StateCode,\n"
    		+ "    d.StateName,\n"
    		+ "    NULL AS PanchayatCode,\n"
    		+ "    '' AS PanchayatName,\n"
    		+ "    NULL AS TehsilCode,\n"
    		+ "    '' AS TeshilName,\n"
    		+ "    'city' AS type\n"
    		+ "FROM\n"
    		+ "    cdt_master_data.geo_city AS c\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.geo_district AS d ON d.DistrictCode = c.DistrictCode\n"
    		+ "WHERE\n"
    		+ "    c.Name LIKE :searchText \n"
    		+ "UNION ALL SELECT \n"
    		+ "    v.ID,\n"
    		+ "    v.Name,\n"
    		+ "    d.DistrictCode,\n"
    		+ "    d.Name AS DistrictName,\n"
    		+ "    d.StateCode,\n"
    		+ "    d.StateName,\n"
    		+ "    p.PanchayatCode,\n"
    		+ "    p.Name AS PanchayatName,\n"
    		+ "    t.TehsilCode,\n"
    		+ "    t.Name AS TeshilName,\n"
    		+ "    'village' AS type\n"
    		+ "FROM\n"
    		+ "    cdt_master_data.geo_village AS v\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.geo_panchayat AS p ON p.PanchayatCode = v.PanchayatCode\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.geo_tehsil t ON t.TehsilCode = v.TehsilCode\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.geo_district AS d ON d.DistrictCode = v.DistrictCode\n"
    		+ "WHERE\n"
    		+ "    (v.Name LIKE :searchText)", nativeQuery = true)
    List<CityVillagePinDTO> findBySearchedCharacter(String searchText);
    
    @Query(value = "select v.RegionID from geo_village v where v.VillageCode = ?1", nativeQuery = true)
    Integer findRegionIDByVillage(Integer villageCode);
    
	
}
