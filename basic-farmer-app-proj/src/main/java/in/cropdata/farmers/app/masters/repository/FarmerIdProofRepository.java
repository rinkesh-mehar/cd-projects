package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.model.FarmerIdProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 12/02/2021 - 3:17 PM
 */

@Repository
public interface FarmerIdProofRepository extends JpaRepository<FarmerIdProof, Integer> {

    List<FarmerIdProof> findByStatusIn(@NonNull List<String> status);
}
