package in.cropdata.cdtmasterdata.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.ToolsDTO;
import in.cropdata.cdtmasterdata.website.model.Tools;
import in.cropdata.cdtmasterdata.website.model.vo.ToolsVO;
import in.cropdata.cdtmasterdata.website.service.ToolsService;

@RestController
@RequestMapping("/site/tools")
public class ToolsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ToolsController.class);
	
	@Autowired
	private ToolsService toolsService;

	@GetMapping("/list")
	public List<ToolsVO> getToolsList() {

		return toolsService.getToolsList();
	}
	
	@GetMapping("/paginatedList")
	public Page<ToolsVO> getEnginesListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return toolsService.getToolsListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addTools(@ModelAttribute ToolsDTO toolsDTO) {

		if (toolsDTO == null) {
			throw new InvalidDataException("Tool data can not be null!");
		}
		LOGGER.info("Add Tool From Controller...");
		return new ResponseEntity<>(toolsService.addTools(toolsDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Tools getPositionById(@PathVariable(required = true) Integer id) {

		return toolsService.getToolsById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateTools(@PathVariable(required = true) Integer id,
			@ModelAttribute ToolsDTO toolsDTO) {

		if (id == null) {
			throw new InvalidDataException("Tool Id can not be null!");
		}

		if (toolsDTO == null) {
			throw new InvalidDataException("Tool data can not be null!");
		}

		LOGGER.info("Updating Tool From Controller for ID -> {}", id);
		return new ResponseEntity<>(toolsService.updateTools(id, toolsDTO), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeTools(@PathVariable int id) {
		return toolsService.activeTools(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveTools(@PathVariable int id) {
		return toolsService.deactiveTools(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteTools(@PathVariable int id) {
		return toolsService.deleteTools(id);
	}
	
}
