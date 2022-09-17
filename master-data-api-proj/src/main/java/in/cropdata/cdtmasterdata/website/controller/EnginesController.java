package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.model.Engines;
import in.cropdata.cdtmasterdata.website.model.vo.EnginesVO;
import in.cropdata.cdtmasterdata.website.service.EnginesService;

@RestController
@RequestMapping("/site/engines")
public class EnginesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnginesController.class);
	
	@Autowired
	private EnginesService enginesService;

	@GetMapping("/list")
	public List<EnginesVO> getEnginesList() {

		return enginesService.getEnginesList();
	}
	
	@GetMapping("/paginatedList")
	public Page<EnginesVO> getEnginesListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return enginesService.getEnginesListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addEngines(@RequestBody Engines engines) {

		LOGGER.info("Add Engine From Controller...");
		return new ResponseEntity<>(enginesService.addEngines(engines), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Engines getPositionById(@PathVariable(required = true) Integer id) {

		return enginesService.getEnginesById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateEngines(@PathVariable(required = true) Integer id,
			@RequestBody Engines engines) {

		if (id == null) {
			throw new InvalidDataException("Engine Id can not be null!");
		}

		if (engines == null) {
			throw new InvalidDataException("Engine data can not be null!");
		}

		LOGGER.info("Updating Engine From Controller for ID -> {}", id);
		return new ResponseEntity<>(enginesService.updateEngines(id, engines), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeEngines(@PathVariable int id) {
		return enginesService.activeEngines(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveEngines(@PathVariable int id) {
		return enginesService.deactiveEngines(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteEngines(@PathVariable int id) {
		return enginesService.deleteEngines(id);
	}
	
}
