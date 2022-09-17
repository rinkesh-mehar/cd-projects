package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriPlantHealthIndexMissing;

@Repository
public interface AgriPlantHealthIndexMissingRepository extends JpaRepository<AgriPlantHealthIndexMissing, Integer> {

}
