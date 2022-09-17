package in.cropdata.configserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.configserver.model.Application;
import in.cropdata.configserver.model.ApplicationProperties;
import in.cropdata.configserver.repository.ApplicationRepository;

@Service
public class ApplicationService {

	@Autowired
	public ApplicationRepository applicationRepository;

	public List<Application> getAllApplication() {
		return applicationRepository.findAll();
	}

	public Application saveApplication(Application application) {

		application = applicationRepository.save(application);
		return application;

	}

	public Application getApplicationById(int id) {
		return applicationRepository.findById(id).get();
	}

}
