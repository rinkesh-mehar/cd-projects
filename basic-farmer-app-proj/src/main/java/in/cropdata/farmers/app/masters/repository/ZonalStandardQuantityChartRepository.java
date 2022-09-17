package in.cropdata.farmers.app.masters.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.farmers.app.masters.model.ZonalStandardQuantityChart;

public interface ZonalStandardQuantityChartRepository extends JpaRepository<ZonalStandardQuantityChart, Integer> {
	
	Optional<ZonalStandardQuantityChart> findAllByZonalVarietyID(Integer zonalVarietyID);

}
