/**
 * 
 */
package in.cropdata.farmers.app.gstmTransitory.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.farmers.app.gstmTransitory.entity.PhenophaseGroupModel;

import java.math.BigInteger;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface PhenophaseGroupRepository extends JpaRepository<PhenophaseGroupModel, Integer> {
	
	
	
	
	@Modifying
	@Transactional
	@Query(value = "INSERT IGNORE INTO gstm_transitory.phenophase_group (\n"
			+ "	SeasonID, \n"
			+ "	StateCode, \n"
			+ "	DistrictCode, \n"
			+ "	SubRegionID, \n"
			+ "	CommodityID, \n"
			+ "	VarietyID, \n"
			+ "	PhenophaseID, \n"
			+ "	SowingWeek, \n"
			+ "	SowingYear, \n"
			+ "	CommonSowingDate, \n"
			+ "	NextCalculationDate\n"
			+ ")\n"
			+ "SELECT \n"
			+ "	cd.SeasonID,\n"
			+ "	v.StateCode,\n"
			+ "	v.DistrictCode,\n"
			+ "	v.SubRegionID,\n"
			+ "	cd.CommodityID,\n"
			+ "	cd.VarietyID,\n"
			+ "	pd.PhenophaseID,\n"
			+ "	Week(cd.CorrectedSowingDate) as SowingWeek,\n"
			+ "	Year(cd.CorrectedSowingDate) as SowingYear,\n"
			+ "	STR_TO_DATE(concat(Year(cd.CorrectedSowingDate), Week(cd.CorrectedSowingDate), ' Wednesday'), '%X %V %W') as CommonSowingDate,\n"
			+ "	DATE_ADD(cd.CorrectedSowingDate, INTERVAL pd.PhenophaseEnd DAY) as NextCalculationDate\n"
			+ "FROM gstm_transitory.case_details as cd\n"
			+ "INNER JOIN cdt_master_data.geo_village as v on v.VillageCode = cd.VillageCode\n"
			+ "INNER JOIN cdt_master_data.agri_phenophase_duration as pd \n"
			+ "	ON pd.StateCode = v.StateCode \n"
			+ "	and pd.SeasonID = cd.SeasonID \n"
			+ "	and pd.CommodityID = cd.CommodityID \n"
			+ "	and pd.VarietyID=cd.VarietyID\n"
			+ "	and datediff(curdate(), cd.CorrectedSowingDate) between PhenophaseStart and PhenophaseEnd\n"
			+ "WHERE cd.CaseID = ?1 ON DUPLICATE KEY UPDATE SeasonID = values(SeasonID)",nativeQuery = true)
	void savePhenophaseGroup(BigInteger caseId);
	
	
	

}
