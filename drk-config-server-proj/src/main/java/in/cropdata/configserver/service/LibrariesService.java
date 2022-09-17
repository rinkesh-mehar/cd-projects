package in.cropdata.configserver.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.configserver.model.Libraries;
import in.cropdata.configserver.repository.LibrariesRepository;

@Service
public class LibrariesService {
	@Autowired
	LibrariesRepository librariesRepository;

	public List<Libraries> getAll() {
		return librariesRepository.findAll();
	}

	public Libraries saveLibraries(@Valid Libraries librariesModel) {
		librariesModel = librariesRepository.save(librariesModel);
		return librariesModel;
	}

	public Libraries getById(int id) {
		// TODO Auto-generated method stub
		return librariesRepository.findById(id).get();
	}
}
