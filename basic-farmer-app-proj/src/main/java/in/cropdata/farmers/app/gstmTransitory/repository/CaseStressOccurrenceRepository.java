package in.cropdata.farmers.app.gstmTransitory.repository;

import in.cropdata.farmers.app.gstmTransitory.dto.CaseDetailsDTO;
import in.cropdata.farmers.app.gstmTransitory.entity.CaseStressOccurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 11:40 AM
 */

@Repository
public interface CaseStressOccurrenceRepository extends JpaRepository<CaseStressOccurrence, BigInteger> {

}
