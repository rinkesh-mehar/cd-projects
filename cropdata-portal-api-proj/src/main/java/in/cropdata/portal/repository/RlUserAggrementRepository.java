/**
 * 
 */
package in.cropdata.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.portal.model.RlUserAggrement;

/**
 * For handling RL user agreement operations.
 * 
 * @author Vivek Gajbhiye
 * @author PranaySK
 */

public interface RlUserAggrementRepository extends JpaRepository<RlUserAggrement, Integer> {

	public Optional<RlUserAggrement> findByRlUserIdAndStatus(Integer rlUserId, String status);
}
