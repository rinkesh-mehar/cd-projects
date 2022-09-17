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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.VasDTO;
import in.cropdata.cdtmasterdata.website.model.Vas;
import in.cropdata.cdtmasterdata.website.model.vo.VasVO;
import in.cropdata.cdtmasterdata.website.service.VasService;

@RestController
@RequestMapping("/site/vas")
public class VasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VasController.class);
	
	@Autowired
	private VasService vasService;

	@GetMapping("/list")
	public List<VasVO> getToolsList() {

		return vasService.getVasList();
	}
	
	@GetMapping("/paginatedList")
	public Page<VasVO> getEnginesListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return vasService.getVasListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addVas(@ModelAttribute VasDTO vasDTO) {

		if (vasDTO == null) {
			throw new InvalidDataException("Vas data can not be null!");
		}
		LOGGER.info("Add Vas From Controller...");
		return new ResponseEntity<>(vasService.addVas(vasDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public Vas getPositionById(@PathVariable(required = true) Integer id) {

		return vasService.getVasById(id);

	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponseMessage> updateVas(@PathVariable(required = true) Integer id,
			@ModelAttribute VasDTO vasDTO) {

		if (id == null) {
			throw new InvalidDataException("Vas Id can not be null!");
		}

		if (vasDTO == null) {
			throw new InvalidDataException("Vas data can not be null!");
		}

		LOGGER.info("Updating Vas From Controller for ID -> {}", id);
		return new ResponseEntity<>(vasService.updateVas(id, vasDTO), HttpStatus.OK);
	}

	@PutMapping("/active/{id}")
	public ResponseMessage activeTools(@PathVariable int id) {
		return vasService.activeVas(id);
	}
	
	@PutMapping("/deactive/{id}")
	public ResponseMessage deactiveTools(@PathVariable int id) {
		return vasService.deactiveVas(id);
	}
	
	@PutMapping("/delete/{id}")
	public ResponseMessage deleteTools(@PathVariable int id) {
		return vasService.deleteVas(id);
	}
	
}
