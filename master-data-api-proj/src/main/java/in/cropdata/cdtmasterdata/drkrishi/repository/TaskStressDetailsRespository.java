/**
 * 
 */
package in.cropdata.cdtmasterdata.drkrishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.drkrishi.model.TaskStressDetails;

/**
 * @author cropdata-Aniket Naik
 *
 */

@Repository
public interface TaskStressDetailsRespository extends JpaRepository<TaskStressDetails, String> {

}
