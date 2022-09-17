package in.cropdata.cdtmasterdata.drkrishi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.drkrishi.model.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Integer> {
	
	@Query(value="SELECT t.id as taskTypeID, t.name as taskTypeName FROM drkrishi.task_type t order by t.name",nativeQuery = true)
	public List<Map<String,Object>> getTaskTypeList();

}
