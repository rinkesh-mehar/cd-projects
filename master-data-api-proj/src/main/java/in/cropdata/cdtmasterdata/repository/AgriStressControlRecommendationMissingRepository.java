package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriStressControlRecommendationMissing;

@Repository
public interface AgriStressControlRecommendationMissingRepository extends JpaRepository<AgriStressControlRecommendationMissing, Integer> {

}
