package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.VipDesignation;
import com.krishi.model.PoiDataModel;

public interface VipDesignationRepository extends JpaRepository<VipDesignation, Integer> {

	List<VipDesignation> findByStatus(Integer status);

	VipDesignation findByName(String designation);

	/** Added for POI */
	@Query(value = "select p.id, concat(p.name, ' (', pt.name, ')') as name "
			+ "from poi as p inner join poi_type as pt on pt.id = p.poi_type_id \n"
			+ "inner join farmer as f on f.village_id = p.village_id inner join task as t on t.entity_id = f.id \n"
			+ "where t.entity_type_id = 4 and t.id = :taskId \n"
			+ "union\n"
			+ "select '0' as id, 'Village (Center)' as name "
			+ "from village where id = (select f.village_id from task as t inner join farmer as f on f.id = t.entity_id "
			+ "where t.id = :taskId) \n"
			+ "order by id", nativeQuery = true)
	List<PoiDataModel> getMeetingPointListByTaskId(String taskId);
	
	@Query(value = "select p.id, concat(p.name, ' (', pt.name, ')') as name "
			+ "from poi as p inner join poi_type as pt on pt.id = p.poi_type_id \n"
			+ "where p.village_id = :villageId \n"
			+ "union\n"
			+ "select '0' as id, 'Village (Center)' as name "
			+ "from village where id = :villageId \n"
			+ "order by id", nativeQuery = true)
	List<PoiDataModel> getMeetingPointListByVillageId(Integer villageId);
}
