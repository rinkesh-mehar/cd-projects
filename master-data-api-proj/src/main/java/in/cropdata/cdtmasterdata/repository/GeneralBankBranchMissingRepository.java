package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.GeneralBankBranchMissing;

@Repository
public interface GeneralBankBranchMissingRepository extends JpaRepository<GeneralBankBranchMissing, Integer> {

}
