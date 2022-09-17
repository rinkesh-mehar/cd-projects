package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriVarietyTolerantStress;

public interface AgriVarietyTolerantStressRepository extends JpaRepository<AgriVarietyTolerantStress, Integer> {

	List<AgriVarietyTolerantStress> findByVarietyStressID(int id);

}
