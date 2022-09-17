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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalBrandInfDto;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalBrand;
import in.cropdata.cdtmasterdata.service.AgriAgrochemicalBrandService;

@RestController
@RequestMapping("/agri/agrochemical-brand")
public class AgriAgrochemicalBrandController {

	@Autowired
	private AgriAgrochemicalBrandService agriAgrochemicalBrandService;

	@GetMapping("/list")
	public List<AgriAgrochemicalBrand> getAllAgriRemedialMeasures() {
		return agriAgrochemicalBrandService.getAllAgriRemedialMeasures();
	}// getAllAgriRemedialMeasures

	@GetMapping()
	public Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrandPaginated(@RequestParam("page") int page,
																			  @RequestParam("size") int size,
																			  @RequestParam("isValid") int isValid,
																			  @RequestParam(required = false, defaultValue = "") String searchText,
																			  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriAgrochemicalBrandService.getAllAgrochemicalBrandPaginated(page, size, searchText, isValid,missing);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriRemedialMeasures(@RequestBody AgriAgrochemicalBrand agriAgrochemicalBrand) {
		return agriAgrochemicalBrandService.addAgriRemedialMeasures(agriAgrochemicalBrand);
	}// addAllAgriRemedialMeasures

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriRemedialMeasuresById(@PathVariable int id,
			@RequestBody AgriAgrochemicalBrand agriAgrochemicalBrand) {
		return agriAgrochemicalBrandService.updateAgriRemedialMeasuresById(id, agriAgrochemicalBrand);
	}// updateAgriRemedialMeasuresById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriAgrochemicalBrandService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriAgrochemicalBrandService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriRemedialMeasuresById(@PathVariable int id) {
		return agriAgrochemicalBrandService.deleteAgriRemedialMeasuresById(id);
	}// deleteAgriRemedialMeasuresById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriAgrochemicalBrandService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriAgrochemicalBrand findAgriRemedialMeasuresById(@PathVariable int id) {
		return agriAgrochemicalBrandService.findAgriRemedialMeasuresById(id);
	}// findAgriRemedialMeasuresById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriAgrochemicalBrandService.moveToMaster(id);
	}

}// AgriAgrochemicalBrandController
