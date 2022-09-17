package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriFieldActivityMissing;

@Repository
public interface AgriFieldActivityMissingRepository extends JpaRepository<AgriFieldActivityMissing, Integer> {

}
