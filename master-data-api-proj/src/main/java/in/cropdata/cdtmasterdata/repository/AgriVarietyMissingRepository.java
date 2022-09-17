package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriVarietyMissing;

@Repository
public interface AgriVarietyMissingRepository extends JpaRepository<AgriVarietyMissing, Integer> {

}
