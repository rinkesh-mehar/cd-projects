/**
 * 
 */
package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriCondusiveWeatherMissing;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface AgriCondusiveWeatherMissingRepository extends JpaRepository<AgriCondusiveWeatherMissing, Integer> {

}
