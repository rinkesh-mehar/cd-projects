package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.TaskStressDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author RinkeshKM
 * @Date 16/04/21
 */
@Repository
public interface TaskStressDetailsRepository extends JpaRepository<TaskStressDetails, String> {


}
