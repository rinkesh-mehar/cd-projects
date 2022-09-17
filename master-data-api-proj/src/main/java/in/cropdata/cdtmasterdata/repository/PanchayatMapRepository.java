package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.region.model.PanchayatMap;
import in.cropdata.cdtmasterdata.region.vo.panchayatMapVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 12/09/20
 * @time 11:01 AM
 */
@Repository
public interface PanchayatMapRepository extends JpaRepository<PanchayatMap, Integer>
{
    @Query(value = "SELECT DISTINCT\n" + 
    		"    gr.Name AS name,\n" + 
    		"    gr.RegionID AS regionId,\n" + 
    		"    rpm.IsValid,\n" + 
    		"    rpm.ErrorMessage\n" + 
    		"FROM\n" + 
    		"    geo_region gr\n" + 
    		"        INNER JOIN\n" + 
    		"    regional_panchayat_map rpm ON gr.RegionID = rpm.RegionID\n" + 
    		"WHERE\n" + 
    		"    gr.Name LIKE :searchText\n" + 
    		"        OR gr.RegionID LIKE :searchText", countQuery = "SELECT DISTINCT\n" + 
    				"    gr.Name AS name,\n" + 
    				"    gr.RegionID AS regionId,\n" + 
    				"    rpm.IsValid,\n" + 
    				"    rpm.ErrorMessage\n" + 
    				"FROM\n" + 
    				"    geo_region gr\n" + 
    				"        INNER JOIN\n" + 
    				"    regional_panchayat_map rpm ON gr.RegionID = rpm.RegionID\n" + 
    				"WHERE\n" + 
    				"    gr.Name LIKE :searchText\n" + 
    				"        OR gr.RegionID LIKE :searchText", nativeQuery = true)
    Page<panchayatMapVO> getPanchayatRegion(Pageable pageable, String searchText);
    
    @Query(value = "SELECT DISTINCT\n" + 
    		"    			    gr.Name AS name,\n" + 
    		"    			    gr.RegionID AS regionId\n" + 
    		"    			FROM\n" + 
    		"    			    geo_region gr\n" + 
    		"    			        INNER JOIN \n" + 
    		"    			    regional_panchayat_map_missing rpm ON gr.RegionID = rpm.RegionID\n" + 
    		"    			WHERE \n" + 
    		"    			    gr.Name LIKE :searchText\n" + 
    		"    			        OR gr.RegionID LIKE :searchText", countQuery = "SELECT DISTINCT\n" + 
    				"    			    gr.Name AS name,\n" + 
    				"    			    gr.RegionID AS regionId\n" + 
    				"    			FROM\n" + 
    				"    			    geo_region gr\n" + 
    				"    			        INNER JOIN \n" + 
    				"    			    regional_panchayat_map_missing rpm ON gr.RegionID = rpm.RegionID\n" + 
    				"    			WHERE \n" + 
    				"    			    gr.Name LIKE :searchText\n" + 
    				"    			        OR gr.RegionID LIKE :searchText", nativeQuery = true)
    Page<panchayatMapVO> getPanchayatRegionMissing(Pageable pageable, String searchText);

    @Query(value = "SELECT DISTINCT\n" + 
    		"    gr.Name AS name,\n" + 
    		"    gr.RegionID AS regionId,\n" + 
    		"    rpm.IsValid,\n" + 
    		"    rpm.ErrorMessage\n" + 
    		"FROM\n" + 
    		"    geo_region gr\n" + 
    		"        INNER JOIN\n" + 
    		"    regional_panchayat_map rpm ON gr.RegionID = rpm.RegionID\n" + 
    		"WHERE\n" + 
    		"    rpm.IsValid = 0\n" + 
    		"    And(gr.Name like :searchText\n" + 
    		"    OR gr.RegionID like :searchText)", countQuery = "SELECT DISTINCT\n" + 
    				"    gr.Name AS name,\n" + 
    				"    gr.RegionID AS regionId,\n" + 
    				"    rpm.IsValid,\n" + 
    				"    rpm.ErrorMessage\n" + 
    				"FROM\n" + 
    				"    geo_region gr\n" + 
    				"        INNER JOIN\n" + 
    				"    regional_panchayat_map rpm ON gr.RegionID = rpm.RegionID\n" + 
    				"WHERE\n" + 
    				"    rpm.IsValid = 0\n" + 
    				"    And(gr.Name like :searchText\n" + 
    				"    OR gr.RegionID like :searchText)", nativeQuery = true)
    Page<panchayatMapVO> getPanchayatRegionInvalidated(Pageable pageable, String searchText);
    
    @Query(value = "SELECT DISTINCT\n" + 
    		"    		    gr.Name AS name, \n" + 
    		"    		    gr.RegionID AS regionId\n" + 
    		"    		FROM\n" + 
    		"    		    geo_region gr\n" + 
    		"    		        INNER JOIN \n" + 
    		"    		    regional_panchayat_map_missing rpm ON gr.RegionID = rpm.RegionID\n" + 
    		"    		WHERE \n" + 
    		"    		    rpm.IsValid = 0\n" + 
    		"    		    And (gr.Name like :searchText\n" + 
    		"    		    OR gr.RegionID like :searchText)", countQuery = "SELECT DISTINCT\n" + 
    				"    		    gr.Name AS name, \n" + 
    				"    		    gr.RegionID AS regionId\n" + 
    				"    		FROM\n" + 
    				"    		    geo_region gr\n" + 
    				"    		        INNER JOIN \n" + 
    				"    		    regional_panchayat_map_missing rpm ON gr.RegionID = rpm.RegionID\n" + 
    				"    		WHERE \n" + 
    				"    		    rpm.IsValid = 0\n" + 
    				"    		    And (gr.Name like :searchText\n" + 
    				"    		    OR gr.RegionID like :searchText)", nativeQuery = true)
    Page<panchayatMapVO> getPanchayatRegionMissingInvalidated(Pageable pageable, String searchText);
}
