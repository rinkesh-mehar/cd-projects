package in.cropdata.configserver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.cropdata.configserver.model.Libraries;
import in.cropdata.configserver.model.LibrariesProperties;
import in.cropdata.configserver.service.LibrariesPropertiesService;
import in.cropdata.configserver.service.LibrariesService;

@Controller
public class LibrariesPropertyController {

	@Autowired
	private LibrariesService librariesService;

	@Autowired
	private LibrariesPropertiesService librariesPropertiesService;

	@RequestMapping(value = "/libraries-property-add", method = RequestMethod.GET)
	public ModelAndView getLibrariesPropertyForm() {
		LibrariesProperties librariesPropertiesModel = new LibrariesProperties();
		ModelAndView modelAndView = new ModelAndView();
		List<Libraries> libList = librariesService.getAll();
		modelAndView.addObject("librariesList", libList);
		modelAndView.addObject("librariesPropertiesModel", librariesPropertiesModel);
		modelAndView.setViewName("libraries/library-properties-form");
		return modelAndView;
	}

	@RequestMapping(value = "/libraries-properties-save", method = RequestMethod.POST)
	public ModelAndView saveLibrariesPropertiesForm(@Valid LibrariesProperties librariesPropertiesModel) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			librariesPropertiesModel = librariesPropertiesService.saveLibrariesProperties(librariesPropertiesModel);
			modelAndView.addObject("message", "Library Properties Saved Sucessfully");
			modelAndView.addObject("librariesPropertiesModel", new LibrariesProperties());

		} catch (Exception e) {
			modelAndView.addObject("message", "Error while Saving Library Properties : " + e.getMessage());
			modelAndView.addObject("librariesPropertiesModel", librariesPropertiesModel);
		}
//		List<Libraries> libList = librariesService.getAll();

		List<Libraries> libList = new ArrayList<Libraries>();
		libList.add(librariesService.getById(librariesPropertiesModel.getLibraryId()));

		modelAndView.addObject("librariesList", libList);
		modelAndView.setViewName("libraries/library-properties");

//		----------------------

		List<LibrariesProperties> librariesPropertiesList = librariesPropertiesService
				.getAllByLibraryId(librariesPropertiesModel.getLibraryId());
		modelAndView.addObject("librariesPropertiesList", librariesPropertiesList);

//		--------------------------

		return modelAndView;
	}

	@RequestMapping(value = "/libraries-property-edit/{id}", method = RequestMethod.GET)
	public ModelAndView getLibrariesEditForm(@PathVariable("id") int id) {
		LibrariesProperties librariesPropertiesModel = librariesPropertiesService.getById(id);
		ModelAndView modelAndView = new ModelAndView();

		List<Libraries> libList = new ArrayList<Libraries>();
		libList.add(librariesService.getById(librariesPropertiesModel.getLibraryId()));
		modelAndView.addObject("librariesList", libList);

		modelAndView.addObject("librariesPropertiesModel", librariesPropertiesModel);
		modelAndView.setViewName("libraries/library-properties");

//		----------------------

		List<LibrariesProperties> librariesPropertiesList = librariesPropertiesService
				.getAllByLibraryId(librariesPropertiesModel.getLibraryId());
		modelAndView.addObject("librariesPropertiesList", librariesPropertiesList);

//		--------------------------

		return modelAndView;
	}

	@RequestMapping(value = "/libraries-property-delete/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteLibrariesProperty(@PathVariable("id") int id) {
		librariesPropertiesService.deleteLibrariesPropertyById(id);
		return "Libraries Property deleted with id " + id;
	}

	@RequestMapping(value = "/libraries-properties-add/{librariesId}", method = RequestMethod.GET)
	public ModelAndView getLibrariesPropertiesEditForm(@PathVariable("librariesId") int librariesId) {
		List<LibrariesProperties> librariesPropertiesList = librariesPropertiesService.getAllByLibraryId(librariesId);
		ModelAndView modelAndView = new ModelAndView();

		List<Libraries> libList = new ArrayList<Libraries>();
		libList.add(librariesService.getById(librariesId));
		modelAndView.addObject("librariesList", libList);
		modelAndView.addObject("librariesPropertiesList", librariesPropertiesList);

		LibrariesProperties librariesPropertiesModel = new LibrariesProperties();

		librariesPropertiesModel.setLibraryId(librariesId);

		modelAndView.addObject("librariesPropertiesModel", librariesPropertiesModel);
		modelAndView.setViewName("libraries/library-properties");
		return modelAndView;
	}

}
