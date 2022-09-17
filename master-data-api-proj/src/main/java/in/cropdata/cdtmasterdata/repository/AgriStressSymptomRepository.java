package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriStressSymptom;

public interface AgriStressSymptomRepository extends JpaRepository<AgriStressSymptom, Integer>{
	List<AgriStressSymptom> findAllByStressId(int id);
}
