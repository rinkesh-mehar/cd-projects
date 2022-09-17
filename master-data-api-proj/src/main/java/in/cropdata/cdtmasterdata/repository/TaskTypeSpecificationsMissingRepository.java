package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.TaskTypeSpecificationsMissing;

@Repository
public interface TaskTypeSpecificationsMissingRepository extends JpaRepository<TaskTypeSpecificationsMissing, Integer> {

}
