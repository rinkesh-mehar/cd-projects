package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriSeedTreatmentMissing;

@Repository
public interface AgriSeedTreatmentMissingRepository extends JpaRepository<AgriSeedTreatmentMissing, Integer> {

}
