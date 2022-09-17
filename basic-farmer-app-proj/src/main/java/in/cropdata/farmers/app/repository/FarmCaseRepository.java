package in.cropdata.farmers.app.repository;


import in.cropdata.farmers.app.model.FarmCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */

@Repository
public interface FarmCaseRepository extends JpaRepository<FarmCase, String> {
	
	



}
