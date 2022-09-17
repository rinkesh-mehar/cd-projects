package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriBenchmarkVarietyMissing;

@Repository
public interface AgriBenchmarkVarietyMissingRepository extends JpaRepository<AgriBenchmarkVarietyMissing, Integer>{

}
