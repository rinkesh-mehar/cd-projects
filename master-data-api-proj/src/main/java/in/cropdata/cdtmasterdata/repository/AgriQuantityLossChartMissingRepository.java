package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriQuantityLossChartMissing;

@Repository
public interface AgriQuantityLossChartMissingRepository extends JpaRepository<AgriQuantityLossChartMissing, Integer> {

}
