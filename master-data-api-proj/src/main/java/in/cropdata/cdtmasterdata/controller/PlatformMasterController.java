package in.cropdata.cdtmasterdata.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.PlatformMasterDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.model.vo.PlatformMasterVO;
import in.cropdata.cdtmasterdata.service.PlatformMasterService;

@RestController
@RequestMapping("/platform")
public class PlatformMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMasterController.class);
	
	@Autowired
	private PlatformMasterService platformMasterService;

	@GetMapping("/list")
	public List<PlatformMasterVO> getPlatformMasterList() {

		return platformMasterService.getPlatformMasterList();
	}
	
	@GetMapping("/paginatedList")
	public Page<PlatformMasterVO> getPlatformMasterListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return platformMasterService.getPlatformMasterListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addPlatformMaster(@ModelAttribute PlatformMasterDTO platformMasterDTO) {

		if (platformMasterDTO == null) {
			throw new InvalidDataException("Platform data can not be null!");
		}
		LOGGER.info("Add Tool From Controller...");
		return new ResponseEntity<>(platformMasterService.addPlatformMaster(platformMasterDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public PlatFormMaster getPositionById(@PathVariable(required = true) Integer id) {

		return platformMasterService.getPlatformMasterById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updatePlatformMaster(@PathVariable(required = true) Integer id,
			@ModelAttribute PlatformMasterDTO platformMasterDTO) {

		if (id == null) {
			throw new InvalidDataException("Platform Id can not be null!");
		}

		if (platformMasterDTO == null) {
			throw new InvalidDataException("Platform data can not be null!");
		}

		LOGGER.info("Updating Platform From Controller for ID -> {}", id);
		return new ResponseEntity<>(platformMasterService.updatePlatformMaster(id, platformMasterDTO), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activePlatformMaster(@PathVariable int id) {
		return platformMasterService.activePlatformMaster(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactivePlatformMaster(@PathVariable int id) {
		return platformMasterService.deactivePlatformMaster(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deletePlatformMaster(@PathVariable int id) {
		return platformMasterService.deletePlatformMaster(id);
	}
	
}
