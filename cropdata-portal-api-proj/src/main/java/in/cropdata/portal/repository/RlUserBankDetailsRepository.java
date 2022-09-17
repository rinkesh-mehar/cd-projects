package in.cropdata.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.portal.model.RlUserBankDetails;

/**
 * For storing and fetching RL user bank account details.
 * 
 * @author PranaySK
 */

public interface RlUserBankDetailsRepository extends JpaRepository<RlUserBankDetails, Integer> {

	Optional<RlUserBankDetails> findByRlUserId(Integer rlUserId);
}
