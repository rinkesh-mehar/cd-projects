package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriFertilizerMissing;

@Repository
public interface AgriFertilizerMissingRepository extends JpaRepository<AgriFertilizerMissing, Integer> {

}
