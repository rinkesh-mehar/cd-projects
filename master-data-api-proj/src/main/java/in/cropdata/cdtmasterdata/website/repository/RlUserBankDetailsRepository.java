package in.cropdata.cdtmasterdata.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.website.model.RlUserBankDetails;

/**
 * For storing and fetching RL user bank account details.
 * 
 * @author PranaySK
 */

public interface RlUserBankDetailsRepository extends JpaRepository<RlUserBankDetails, Integer> {

	Optional<RlUserBankDetails> findByRlUserId(Integer rlUserId);

}
