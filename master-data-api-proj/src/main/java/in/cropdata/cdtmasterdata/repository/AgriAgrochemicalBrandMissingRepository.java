package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriAgrochemicalBrandMissing;

@Repository
public interface AgriAgrochemicalBrandMissingRepository extends JpaRepository<AgriAgrochemicalBrandMissing, Integer> {

}
