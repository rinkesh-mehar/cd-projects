/**
 * 
 */
package in.cropdata.cdtmasterdata.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.website.model.RlUserAggrement;

/**
 * @author vivek-cropdata
 * @author PranaySK
 */
public interface RlUserAggrementRepository extends JpaRepository<RlUserAggrement, Integer> {

	Optional<RlUserAggrement> findByRlUserId(Integer rlUserId);

}
