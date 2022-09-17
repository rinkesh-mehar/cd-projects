package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import in.cropdata.cdtmasterdata.model.AgriAgroChemicalType;
import in.cropdata.cdtmasterdata.service.AgriAgroChemicalTypeService;

@RestController
@RequestMapping("/agri/agrochemical-type")
public class AgriAgroChemicalTypeController {

	@Autowired
	private AgriAgroChemicalTypeService agriAgroChemicalTypeService;

	@GetMapping("/list")
	public List<AgriAgroChemicalType> getAllAgriAgroChemicalType() {
		return agriAgroChemicalTypeService.getAllAgriAgroChemicalType();
	}// getAllAgriAgroChemicalType
	
	@GetMapping("/paginatedList")
	public Page<AgriAgroChemicalType> getAgroChemicalTypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriAgroChemicalTypeService.getAgroChemicalTypeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriAgroChemicalType(@RequestBody AgriAgroChemicalType agriAgroChemicalType) {
		return agriAgroChemicalTypeService.addAgriAgroChemicalType(agriAgroChemicalType);
	}// addAgriAgroChemicalType

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriAgroChemicalTypeById(@PathVariable int id,
			@RequestBody AgriAgroChemicalType agriAgroChemicalType) {
		return agriAgroChemicalTypeService.updateAgriAgroChemicalTypeById(id, agriAgroChemicalType);
	}// updateAgriAgroChemicalTypeById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriAgroChemicalTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriAgroChemicalTypeService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriAgroChemicalTypeById(@PathVariable int id) {
		return agriAgroChemicalTypeService.deleteAgriAgroChemicalTypeById(id);
	}// deleteAgriAgroChemicalTypeById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriAgroChemicalTypeService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriAgroChemicalType findAgriAgroChemicalTypeById(@PathVariable int id) {
		return agriAgroChemicalTypeService.findAgriAgroChemicalTypeById(id);
	}// findAgriAgroChemicalTypeById

}// AgriAgroChemicalTypeController
