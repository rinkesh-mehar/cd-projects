package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.masters.model.AgriCropType;

@Repository
public interface AgriCropTypeRepository extends JpaRepository<AgriCropType, Integer> {
	
	List<AgriCropType> findAllByStatus(String status);

}
