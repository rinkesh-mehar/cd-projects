/**
 * 
 */
package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriHealthMissing;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Repository
public interface AgriHealthMissingRepository extends JpaRepository<AgriHealthMissing, Integer> {

}
