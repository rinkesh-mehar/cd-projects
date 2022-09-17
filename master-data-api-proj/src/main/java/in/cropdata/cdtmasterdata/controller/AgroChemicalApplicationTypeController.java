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
import in.cropdata.cdtmasterdata.model.AgriAgroChemicalApplicationType;
import in.cropdata.cdtmasterdata.service.AgriAgroChemicalApplicationTypeService;

@RestController
@RequestMapping("/agri/agrochemical-application-type")
public class AgroChemicalApplicationTypeController {

	@Autowired
	private AgriAgroChemicalApplicationTypeService agriAgroChemicalApplicationTypeService;

	@GetMapping("/list")
	public List<AgriAgroChemicalApplicationType> getAllAgriAgroChemicalApplicationType() {
		return agriAgroChemicalApplicationTypeService.getAllAgriAgroChemicalApplicationType();
	}// getAllAgriAgroChemicalApplicationType
	
	@GetMapping("/paginatedList")
	public Page<AgriAgroChemicalApplicationType> getPeginatedAgriAgroChemicalApplicationTypeList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriAgroChemicalApplicationTypeService.getPeginatedAgriAgroChemicalApplicationTypeList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriAgroChemicalApplictionType(
			@RequestBody AgriAgroChemicalApplicationType agriAgroChemicalApplicationType) {
		return agriAgroChemicalApplicationTypeService
				.addAgriAgroChemicalApplicationType(agriAgroChemicalApplicationType);
	}// addAgriAgroChemicalApplictionType

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriAgroChemicalApplicationTypeById(@PathVariable int id,
			@RequestBody AgriAgroChemicalApplicationType agriAgroChemicalApplicationType) {
		return agriAgroChemicalApplicationTypeService.updateAgriAgroChemicalApplicationTypeById(id,
				agriAgroChemicalApplicationType);
	}// updateAgriAgroChemicalApplicationTypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriAgroChemicalApplicationTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriAgroChemicalApplicationTypeService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriAgroChemicalApplicationType(@PathVariable int id) {
		return agriAgroChemicalApplicationTypeService.deleteAgriAgroChemicalApplicationTypeById(id);
	}// deleteAgriAgroChemicalApplicationType

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriAgroChemicalApplicationTypeService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriAgroChemicalApplicationType findAgriAgroChemicalApplicationTypeById(@PathVariable int id) {
		return agriAgroChemicalApplicationTypeService.findAgriAgroChemicalApplicationTypeById(id);
	}// findAgriAgroChemicalApplicationTypeById

}
