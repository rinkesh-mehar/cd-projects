package in.cropdata.configserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.configserver.model.ApplicationLibraries;
import in.cropdata.configserver.model.ApplicationProperties;
import in.cropdata.configserver.repository.ApplicationLibrariesRepository;

@Service
public class ApplicationLibrariesService {

	@Autowired
	public ApplicationService applicationService;

	@Autowired
	public ApplicationLibrariesRepository applicationLibrariesRepository;

	public List<ApplicationLibraries> getApplicationLibrariesByApplicationId(int id) {
		return applicationLibrariesRepository.findAllByApplicationId(id);
	}

	public List<ApplicationLibraries> getApplicationLibrariesByApplicationIdAndEnvProfile(int id, String EnvProfile) {
		return applicationLibrariesRepository.findAllByApplicationIdAndEnvProfile(id, EnvProfile);
	}

	@Transactional
	public String saveApplicationLibraries(@Valid ApplicationProperties applicationProperty, int[] libraries) {
		List<ApplicationLibraries> applicationLibrariesList = new ArrayList<ApplicationLibraries>();

		if (libraries != null && libraries.length > 0) {
			for (int libId : libraries) {

				ApplicationLibraries applicationLibraries = new ApplicationLibraries();

				applicationLibraries.setApplicationId(applicationProperty.getApplicationId());
				applicationLibraries.setEnvProfile(applicationProperty.getEnvProfile());
				applicationLibraries.setLibraryId(libId);
				applicationLibraries.setLabel(applicationProperty.getLabel());

				applicationLibrariesList.add(applicationLibraries);
			}
		}

		try {
			int i = applicationLibrariesRepository.deleteByApplicationIdAndEnvProfile(
					applicationProperty.getApplicationId(), applicationProperty.getEnvProfile());
		} catch (Exception e) {
			System.out.println("delete error-->  " + e.getMessage());
		}
		if (applicationLibrariesList != null && applicationLibrariesList.size() > 0)
			applicationLibrariesList = applicationLibrariesRepository.saveAll(applicationLibrariesList);

		return "Sucess";

	}

}
