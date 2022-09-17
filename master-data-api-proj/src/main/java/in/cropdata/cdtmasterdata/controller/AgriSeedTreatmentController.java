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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriSeedTreatmentInfDto;
import in.cropdata.cdtmasterdata.model.AgriSeedTreatment;
import in.cropdata.cdtmasterdata.service.AgriSeedTreatmentService;

@RestController
@RequestMapping("/agri/seed-treatment")
public class AgriSeedTreatmentController {

	@Autowired
	private AgriSeedTreatmentService agriSeedTreatmentService;

	@GetMapping("/list")
	public List<AgriSeedTreatmentInfDto> getAllAgriSeedTreatment() {
		return agriSeedTreatmentService.getAllAgriSeedTreatment();
	}// getAllAgriSeedTreatment

	@GetMapping()
	public Page<AgriSeedTreatmentInfDto> getAllAgriSeedTreatmentPaginated(@RequestParam("page") int page,
																		  @RequestParam("size") int size,
																		  @RequestParam("isValid") int isValid,
																		  @RequestParam(required = false, defaultValue = "") String searchText,
																		  @RequestParam(required = false,defaultValue = "0") String missing,
																		  @RequestParam(required = false, defaultValue = "") String commodityId,
																		  @RequestParam(required = false, defaultValue = "") String varietyId,
																		  @RequestParam(required = false, defaultValue = "") String name,
																		  @RequestParam(required = false, defaultValue = "") String filter)
	{
		return agriSeedTreatmentService.getAllAgriSeedTreatmentPaginated(page, size, searchText,isValid,missing,commodityId,varietyId,name,filter);
	}// getAllAgriSeedTreatmentPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriSeedTreatment(@RequestBody AgriSeedTreatment agriSeedTreatment) {
		return agriSeedTreatmentService.addAgriSeedTreatment(agriSeedTreatment);
	}// addAgriSeedTreatment

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriSeedTreatmentById(@PathVariable int id,
			@RequestBody AgriSeedTreatment agriSeedTreatment) {
		return agriSeedTreatmentService.updateAgriSeedTreatmentById(id, agriSeedTreatment);
	}// updateAgriSeedTreatmentById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriSeedTreatmentService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriSeedTreatmentService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriSeedTreatmentById(@PathVariable int id) {
		return agriSeedTreatmentService.deleteAgriSeedTreatmentById(id);
	}// deleteAgriSeedTreatmentById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriSeedTreatmentService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriSeedTreatment findAgriSeedTreatmentById(@PathVariable int id) {
		return agriSeedTreatmentService.findAgriSeedTreatmentById(id);
	}// findAgriSeedTreatmentById
	

	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriSeedTreatmentService.moveToMaster(id);
	}
	

}// AgriSeedTreatmentController
