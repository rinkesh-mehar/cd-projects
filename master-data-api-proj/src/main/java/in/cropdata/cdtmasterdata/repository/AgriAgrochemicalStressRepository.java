package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.AgriAgrochemicalStress;

public interface AgriAgrochemicalStressRepository extends JpaRepository<AgriAgrochemicalStress, Integer>{

	List<AgriAgrochemicalStress> findAllByAgrochemicalId(int id);

}
