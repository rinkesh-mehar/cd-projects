package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriVarietySusceptibleStress;

public interface AgriVarietySusceptibleStressRepository extends JpaRepository<AgriVarietySusceptibleStress, Integer> {

	List<AgriVarietySusceptibleStress> findByVarietyStressID(int id);

	void deleteByvarietyStressID(int id);

}
