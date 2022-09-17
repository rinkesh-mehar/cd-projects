package in.cropdata.configserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.configserver.model.ApplicationProperties;

public interface ApplicationPropertiesRepository extends JpaRepository<ApplicationProperties, Integer>{

	List<ApplicationProperties> findByApplicationId(String application);

	List<ApplicationProperties> findByApplicationId(int id);

	List<ApplicationProperties> findByApplicationIdAndEnvProfile(int id, String envProfile);

	int deleteByApplicationIdAndEnvProfile(int applicationId, String envProfile);

}
