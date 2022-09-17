package in.cropdata.configserver.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.configserver.model.LibrariesProperties;
import in.cropdata.configserver.repository.LibrariesPropertiesRepository;

@Service
public class LibrariesPropertiesService {

	@Autowired
	LibrariesPropertiesRepository librariesPropertiesRepository;

	public List<LibrariesProperties> getAll() {
		return librariesPropertiesRepository.findAll();
	}

	public LibrariesProperties saveLibrariesProperties(@Valid LibrariesProperties librariesPropertiesModel) {
		try {
			librariesPropertiesModel = librariesPropertiesRepository.save(librariesPropertiesModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		librariesPropertiesModel = librariesPropertiesRepository.save(librariesPropertiesModel);
		return librariesPropertiesModel;
	}

	public List<LibrariesProperties> getAllByLibraryId(int libId) {
		return librariesPropertiesRepository.findAllByLibraryId(libId);
	}

	public LibrariesProperties getById(int id) {
		Optional<LibrariesProperties> librariesProperties = librariesPropertiesRepository.findById(id);
		return librariesProperties.get();
	}

	public void deleteLibrariesPropertyById(int id) {
		librariesPropertiesRepository.deleteById(id);
	}
}
