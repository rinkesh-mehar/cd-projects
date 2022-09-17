package com.drkrishi.usermanagement.dao.repository;

import com.drkrishi.usermanagement.entity.MasterGstmSync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MasterGstmSyncRepository extends JpaRepository<MasterGstmSync, Integer> {

	@Query(value = "select gs.id as ID,screen.name as screenTitle,gs.label_name as label,gs.schema_name as tableName,gs.zipping_level as zippingLevel, gs.url as url, gs.endpoint_name as endpointName, UNIX_TIMESTAMP(last_sync)  as lastSync, role_id as roleId \n" +
			"            from drkrishi_s1.gstm_sync gs\n" +
			"\tinner join screen screen on (gs.screen_id = screen.id)  and gs.screen_id=1 and gs.download_in_android= 'Yes'\n" +
			"\twhere role_id = ?1 or role_id is null", nativeQuery = true)
	List<Map<String, Object>> getMasterSyncDetailsByRoleId(Integer roleId);

	@Query(value = "select distinct gs.id as ID,screen.name as screenTitle,gs.label_name as label,gs.schema_name as tableName,gs.zipping_level as zippingLevel, gs.url as url, gs.endpoint_name as endpointName from drkrishi_s1.gstm_sync gs\n" +
			"inner join screen screen on (gs.screen_id = screen.id) and screen.id=3\n" +
			"where role_id = ?1", nativeQuery = true)
	List<Map<String, Object>> getPlatPartAndSymptomsByRoleId(Integer roleId);

	@Query(value = "SELECT \n" +
			"    gs.id AS ID,\n" +
			"    screen.name AS screenTitle,\n" +
			"    gs.label_name AS label,\n" +
			"    gs.schema_name AS tableName,\n" +
			"    gs.zipping_level AS zippingLevel,\n" +
			"    gs.url AS url,\n" +
			"    gs.endpoint_name AS endpointName,\n" +
			"    r.mmpk_parts_count AS mmpkPartCount\n" +
			"FROM\n" +
			"  gstm_sync gs\n" +
			"        INNER JOIN\n" +
			"    screen screen ON (gs.screen_id = screen.id)\n" +
			"        AND screen.id = 4\n" +
			"        INNER JOIN\n" +
			"    users u ON u.id = ?1\n" +
			"        INNER JOIN\n" +
			"    region r ON r.id = u.region_id\n" +
			"WHERE\n" +
			"    u.id = ?1", nativeQuery = true)
	Map<String, Object> getMmpkByuserId(Integer userId);

	@Query(value = "SELECT gs.endpoint_name AS endpointName\n" +
			"FROM\n" +
			"    drkrishi_s1.gstm_sync gs\n" +
			"        INNER JOIN\n" +
			"    screen screen ON (gs.screen_id = screen.id)\n" +
			"        AND gs.screen_id = 2\n" +
			"WHERE\n" +
			"    role_id = ?1", nativeQuery = true)
	String getScoutUserDataAPI(Integer roleId);


}
