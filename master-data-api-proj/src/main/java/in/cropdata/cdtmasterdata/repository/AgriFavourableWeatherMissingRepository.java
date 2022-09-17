/**
 * 
 */
package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriFavourableWeatherMissing;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface AgriFavourableWeatherMissingRepository extends JpaRepository<AgriFavourableWeatherMissing, Integer> {

}
