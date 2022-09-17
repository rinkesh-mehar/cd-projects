package in.cropdata.configserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.configserver.model.ApplicationLibraries;


public interface ApplicationLibrariesRepository  extends JpaRepository<ApplicationLibraries, Integer>{

	public int deleteByApplicationIdAndEnvProfile(int applicationId, String envProfile);

	public List<ApplicationLibraries> findAllByApplicationIdAndEnvProfile(int id, String envProfile);

	public List<ApplicationLibraries> findAllByApplicationId(int id);

}
