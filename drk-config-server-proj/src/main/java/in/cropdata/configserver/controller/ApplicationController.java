package in.cropdata.configserver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.cropdata.configserver.model.Application;
import in.cropdata.configserver.model.ApplicationLibraries;
import in.cropdata.configserver.model.ApplicationProperties;
import in.cropdata.configserver.model.Libraries;
import in.cropdata.configserver.model.LibrariesProperties;
import in.cropdata.configserver.service.ApplicationLibrariesService;
import in.cropdata.configserver.service.ApplicationService;
import in.cropdata.configserver.service.LibrariesPropertiesService;
import in.cropdata.configserver.service.LibrariesService;

@Controller
@RequestMapping("application")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationLibrariesService applicationLibrariesService;

	@Autowired
	private LibrariesService librariesService;

	@Autowired
	private LibrariesPropertiesService librariesPropertiesService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getApplication() {
		return "redirect:./list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getApplicationList() {
		List<Application> list = applicationService.getAllApplication();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userName", "Welcome Admin");

		modelAndView.addObject("applicationList", list);

		if (list != null && list.size() > 0) {

			HashMap<String, List<ApplicationProperties>> applicationPropertiesList = new HashMap<>();

			for (Application application : list) {

				List<ApplicationProperties> appPropList = new ArrayList<ApplicationProperties>();

				List<ApplicationLibraries> appLibList = applicationLibrariesService
						.getApplicationLibrariesByApplicationId(application.getId());

				if (appLibList != null && appLibList.size() > 0) {
					for (ApplicationLibraries applicationLibraries : appLibList) {

						List<LibrariesProperties> libProps = librariesPropertiesService
								.getAllByLibraryId(applicationLibraries.getLibraryId());

						for (LibrariesProperties libProp : libProps) {
							ApplicationProperties app = new ApplicationProperties();
							app.setAttrKey(libProp.getAttrKey());
							app.setAttrValue(libProp.getAttrValue());
							app.setEnvProfile(applicationLibraries.getEnvProfile());
							app.setLabel(applicationLibraries.getLabel());
							app.setApplicationId(application.getId());

							appPropList.add(app);
						}

					}
				}

				applicationPropertiesList.put(application.getName(), appPropList);

			}
			modelAndView.addObject("applicationPropertiesList", applicationPropertiesList);
		}

		modelAndView.setViewName("application/application-list");
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView getApplicationForm() {
		Application applicationModel = new Application();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("applicationModel", applicationModel);
		modelAndView.setViewName("application/application-form");
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView getApplicationEditForm(@PathVariable("id") int id) {

		Application applicationModel = applicationService.getApplicationById(id);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("applicationModel", applicationModel);
		modelAndView.setViewName("application/application-form");
		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveApplicationForm(@Valid Application applicationModel) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			applicationModel = applicationService.saveApplication(applicationModel);
			modelAndView.addObject("message", "Application Added Sucessfully");
		} catch (Exception e) {
			modelAndView.addObject("message", "Error while Saving Application : " + e.getMessage());
		}
		modelAndView.addObject("applicationModel", new Application());
		modelAndView.setViewName("application/application-form");
		return modelAndView;
	}

	@RequestMapping(value = "/properties-add", method = RequestMethod.GET)
	public ModelAndView getApplicationPropertyform() {

		ModelAndView modelAndView = new ModelAndView();

		List<Libraries> libList = librariesService.getAll();
		modelAndView.addObject("librariesList", libList);

		List<Application> appList = applicationService.getAllApplication();
		modelAndView.addObject("applicationList", appList);

		modelAndView.addObject("libraries", new ArrayList<Integer>());

		modelAndView.addObject("applicationProperty", new ApplicationProperties());
		modelAndView.setViewName("application/application-properties-form");
		return modelAndView;
	}

	@RequestMapping(value = "/properties-add", method = RequestMethod.POST)
	public ModelAndView saveApplicationPropertyform(@Valid ApplicationProperties applicationProperty,
			@RequestParam(value = "libraries", required = false) int[] libraries) {

		ModelAndView modelAndView = new ModelAndView();

		try {
			String result = applicationLibrariesService.saveApplicationLibraries(applicationProperty, libraries);
			modelAndView.addObject("message", result + " : Application Properties Added Sucessfully");
			modelAndView.addObject("applicationProperty", new ApplicationProperties());

		} catch (Exception e) {
			modelAndView.addObject("message", "Error : " + e.getMessage());

			modelAndView.addObject("applicationProperty", applicationProperty);
		}

		List<Application> appList = applicationService.getAllApplication();
		modelAndView.addObject("applicationList", appList);
		List<Libraries> libList = librariesService.getAll();
		modelAndView.addObject("librariesList", libList);
		modelAndView.setViewName("application/application-properties-form");
		return modelAndView;
	}

	@RequestMapping(value = "/properties-edit/{id}", method = RequestMethod.GET)
	public ModelAndView getApplicationPropertyEditform(@PathVariable("id") int id) {

		ModelAndView modelAndView = new ModelAndView();

		List<Libraries> libList = librariesService.getAll();
		modelAndView.addObject("librariesList", libList);
		List<Application> appList = applicationService.getAllApplication();
		modelAndView.addObject("applicationList", appList);
		modelAndView.addObject("operationMode", "edit");

//		List<ApplicationProperties> applicationPropertiesList = applicationPropertiesService.getAllByApplicationId(id);

		ApplicationProperties applicationProperties = new ApplicationProperties();

		Application app = applicationService.getApplicationById(id);
		applicationProperties.setApplicationId(id);
		applicationProperties.setApplicationName(app.getName());

		List<ApplicationLibraries> applicationLibrariesList = applicationLibrariesService
				.getApplicationLibrariesByApplicationId(id);

		if (applicationLibrariesList != null && applicationLibrariesList.size() > 0) {

			applicationProperties.setEnvProfile(applicationLibrariesList.get(0).getEnvProfile());
			applicationProperties.setLabel(applicationLibrariesList.get(0).getLabel());

		}

		modelAndView.addObject("applicationProperty", applicationProperties);

		modelAndView.setViewName("application/application-properties-form");

		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "/delete-application-property/{id}", method =
	 * RequestMethod.GET) public @ResponseBody String
	 * deleteApplicationProperty(@PathVariable("id") int id) {
	 * applicationPropertiesService.deleteApplicationPropertyById(id);
	 * 
	 * return " Application Property deleted with id " + id; // return
	 * applicationPropertiesService.
	 * getApplicationLibrariesByApplicationIdAndEnvProfile(applicationId,envProfile)
	 * ; }
	 * 
	 * @RequestMapping(value = "/edit-application-property/{id}", method =
	 * RequestMethod.GET) public ModelAndView
	 * getApplicationLibrary(@PathVariable("id") int id) {
	 * 
	 * ApplicationProperties applicationProperties =
	 * applicationPropertiesService.get(id); ModelAndView modelAndView = new
	 * ModelAndView(); modelAndView.addObject("applicationProperties",
	 * applicationProperties);
	 * modelAndView.setViewName("application/application-property-form"); return
	 * modelAndView; }
	 * 
	 * @RequestMapping(value = "/save-application-property", method =
	 * RequestMethod.POST) public ModelAndView saveApplicationPropertyForm(@Valid
	 * ApplicationProperties applicationProperties) {
	 * 
	 * ModelAndView modelAndView = new ModelAndView();
	 * 
	 * try { applicationProperties =
	 * applicationPropertiesService.save(applicationProperties);
	 * modelAndView.addObject("message",
	 * "Application Property Updated Sucessfully"); } catch (Exception e) {
	 * modelAndView.addObject("message", "Error while Saving Application : " +
	 * e.getMessage()); } modelAndView.addObject("applicationModel", new
	 * Application());
	 * modelAndView.setViewName("application/application-property-form"); return
	 * modelAndView; }
	 */

	@RequestMapping(value = "/application-library", method = RequestMethod.GET)
	public @ResponseBody List<ApplicationLibraries> getApplicationLibrary(@RequestParam int applicationId,
			@RequestParam String envProfile) {

		List<ApplicationLibraries> applicationLibrariesList = applicationLibrariesService
				.getApplicationLibrariesByApplicationIdAndEnvProfile(applicationId, envProfile);

		return applicationLibrariesList;
	}

}
