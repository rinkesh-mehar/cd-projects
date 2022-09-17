package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.farmers.app.DTO.StateDTO;
import in.cropdata.farmers.app.masters.model.State;

public interface StateRepository extends JpaRepository<State, Integer> {
	
	List<State> findAllByStatusAndCountryCode(String status,Integer countryCode);

}
