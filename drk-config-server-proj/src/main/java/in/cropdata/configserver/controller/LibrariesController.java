package in.cropdata.configserver.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import in.cropdata.configserver.model.Application;
import in.cropdata.configserver.model.ApplicationProperties;
import in.cropdata.configserver.model.Libraries;
import in.cropdata.configserver.model.LibrariesProperties;
import in.cropdata.configserver.service.LibrariesPropertiesService;
import in.cropdata.configserver.service.LibrariesService;

@Controller
public class LibrariesController {
	@Autowired
	private LibrariesService librariesService;

	@Autowired
	private LibrariesPropertiesService librariesPropertiesService;

	@RequestMapping(value = "/libraries-list", method = RequestMethod.GET)
	public ModelAndView getApplicationList() {
		List<Libraries> list = librariesService.getAll();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("librariesList", list);

		if (list != null && list.size() > 0) {
			Map<String, List<LibrariesProperties>> librariesPropertiesList = new HashMap<String, List<LibrariesProperties>>();
			for (Libraries libraries : list) {
				List<LibrariesProperties> libList = librariesPropertiesService.getAllByLibraryId(libraries.getId());
				librariesPropertiesList.put("" + libraries.getName(), libList);
			}
			modelAndView.addObject("librariesPropertiesList", librariesPropertiesList);
		}
		modelAndView.setViewName("libraries/libraries-list");
		return modelAndView;
	}

	@RequestMapping(value = "/libraries-add", method = RequestMethod.GET)
	public ModelAndView getLibrariesForm() {
		Libraries librariesModel = new Libraries();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("librariesModel", librariesModel);
		modelAndView.setViewName("libraries/libraries-form");
		return modelAndView;
	}

	@RequestMapping(value = "/libraries-edit/{id}", method = RequestMethod.GET)
	public ModelAndView getLibrariesEditForm(@PathVariable("id") int id) {
		Libraries librariesModel = librariesService.getById(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("librariesModel", librariesModel);
		modelAndView.setViewName("libraries/libraries-form");
		return modelAndView;
	}

	@RequestMapping(value = "/libraries-save", method = RequestMethod.POST)
	public ModelAndView saveApplicationForm(@Valid Libraries librariesModel) {
		System.out.println("savelibrariesForm--> " + librariesModel.getName());
		ModelAndView modelAndView = new ModelAndView();
		try {
			librariesModel = librariesService.saveLibraries(librariesModel);
			modelAndView.addObject("message", "Library Saved Sucessfully");
			modelAndView.addObject("librariesModel", new Libraries());

		} catch (Exception e) {
			modelAndView.addObject("message", "Error while Saving Library : " + e.getMessage());
			modelAndView.addObject("librariesModel", librariesModel);
		}
		modelAndView.setViewName("libraries/libraries-form");
		return modelAndView;
	}

	

}
