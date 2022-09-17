package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriVarietyResistantStress;

public interface AgriVarietyResistantStressRepository extends JpaRepository<AgriVarietyResistantStress, Integer> {

	List<AgriVarietyResistantStress> findByVarietyStressID(int id);

}
