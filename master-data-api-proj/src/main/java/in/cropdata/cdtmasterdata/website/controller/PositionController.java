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
import in.cropdata.cdtmasterdata.website.dto.PositionDto;
import in.cropdata.cdtmasterdata.website.model.Position;
import in.cropdata.cdtmasterdata.website.service.PositionService;

@RestController
@RequestMapping("/site/position")
public class PositionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);
	
	@Autowired
	private PositionService positionService;

	@GetMapping("/list")
	public List<PositionDto> getPositionList() {

		return positionService.getPositionList();
	}
	
	@GetMapping("/paginatedList")
	public Page<PositionDto> getPositionListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return positionService.getPositionListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addPosition(@RequestBody Position position) {

		LOGGER.info("Add Position From Controller...");
		return new ResponseEntity<>(positionService.addPosition(position), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Position getPositionById(@PathVariable(required = true) Integer id) {

		return positionService.getPositionById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateDepartment(@PathVariable(required = true) Integer id,
			@RequestBody Position position) {

		if (id == null) {
			throw new InvalidDataException("Position Id can not be null!");
		}

		if (position == null) {
			throw new InvalidDataException("Position data can not be null!");
		}

		LOGGER.info("Updating Position From Controller for ID -> {}", id);
		return new ResponseEntity<>(positionService.updatePosition(id, position), HttpStatus.OK);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactivePosition(@PathVariable int id) {
		return positionService.deactivePosition(id);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeEducation(@PathVariable int id) {
		return positionService.activePosition(id);
	}

	@GetMapping("/opportunityCount/{positionId}")
	public Integer opportunityCountByPosition(@PathVariable int positionId) {
		return positionService.opportunityCountByPosition(positionId);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deletePosition(@PathVariable int id) {
		return positionService.deletePosition(id);
	}
	
}
