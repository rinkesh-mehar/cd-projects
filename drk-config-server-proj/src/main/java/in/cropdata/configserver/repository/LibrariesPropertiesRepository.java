package in.cropdata.configserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.configserver.model.LibrariesProperties;

public interface LibrariesPropertiesRepository extends JpaRepository<LibrariesProperties, Integer>{

	List<LibrariesProperties> findAllByLibraryId(int libId);

}
