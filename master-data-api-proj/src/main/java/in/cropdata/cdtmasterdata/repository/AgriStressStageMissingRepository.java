package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriStressStageMissing;

@Repository
public interface AgriStressStageMissingRepository extends JpaRepository<AgriStressStageMissing, Integer> {

}
