package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriPhenophaseDurationMissing;

@Repository
public interface AgriPhenophaseDurationMissingRepository extends JpaRepository<AgriPhenophaseDurationMissing, Integer> {

}
