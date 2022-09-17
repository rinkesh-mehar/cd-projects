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
import in.cropdata.cdtmasterdata.website.model.Indices;
import in.cropdata.cdtmasterdata.website.model.vo.IndicesVO;
import in.cropdata.cdtmasterdata.website.service.IndicesService;

@RestController
@RequestMapping("/site/indices")
public class IndicesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndicesController.class);
	
	@Autowired
	private IndicesService indicesService;

	@GetMapping("/list")
	public List<IndicesVO> getEnginesList() {

		return indicesService.getIndicesList();
	}
	
	@GetMapping("/paginatedList")
	public Page<IndicesVO> getIndicesListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return indicesService.getIndicesListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addEngines(@RequestBody Indices indices) {

		LOGGER.info("Add Indices From Controller...");
		return new ResponseEntity<>(indicesService.addIndices(indices), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Indices getPositionById(@PathVariable(required = true) Integer id) {

		return indicesService.getIndicesById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateEngines(@PathVariable(required = true) Integer id,
			@RequestBody Indices indices) {

		if (id == null) {
			throw new InvalidDataException("Indices Id can not be null!");
		}

		if (indices == null) {
			throw new InvalidDataException("Indices data can not be null!");
		}

		LOGGER.info("Updating Indices From Controller for ID -> {}", id);
		return new ResponseEntity<>(indicesService.updateIndices(id, indices), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeIndices(@PathVariable int id) {
		return indicesService.activeIndices(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveIndices(@PathVariable int id) {
		return indicesService.deactiveIndices(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteIndices(@PathVariable int id) {
		return indicesService.deleteIndices(id);
	}
	
}
