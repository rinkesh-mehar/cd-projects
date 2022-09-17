package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriQualityChartMissing;

@Repository
public interface AgriQualityChartMissingRepository extends JpaRepository<AgriQualityChartMissing, Integer> {

}
