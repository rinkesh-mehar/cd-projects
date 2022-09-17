package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.AgriFieldActivityDTO;
import in.cropdata.cdtmasterdata.dto.AgroCommodityDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFieldActivityInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriSeedTreatmentInfDto;
import in.cropdata.cdtmasterdata.model.AgriFieldActivity;
import in.cropdata.cdtmasterdata.service.AgriFieldActivityService;

@RestController
@RequestMapping("/agri/field-activity")
public class AgriFieldActivityController {

	@Autowired
	private AgriFieldActivityService agriFieldActivityService;

	@GetMapping("/list")
	public List<AgriFieldActivityInfDto> getAllAgriFieldActivity() {
		return agriFieldActivityService.getAllAgriFieldActivity();
	}// getAllAgriFieldActivity

	@GetMapping()
	public Page<AgriFieldActivityInfDto> getAllAgriFieldActivityPaginated(@RequestParam("page") int page,
																		  @RequestParam("size") int size,
																		  @RequestParam("isValid") int isValid,
																		  @RequestParam(required = false, defaultValue = "") String searchText,
																		  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriFieldActivityService.getAllAgriFieldActivityPaginated(page, size, searchText, isValid,missing);
	}// getAllAgriFieldActivityPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriFieldActivity(@ModelAttribute AgriFieldActivityDTO agriFieldActivityDTO) {
		return agriFieldActivityService.addAgriFieldActivity(agriFieldActivityDTO);
	}// addAgriFieldActivity

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriFieldActivityById(@PathVariable int id,@ModelAttribute AgriFieldActivityDTO agriFieldActivityDTO) {
		return agriFieldActivityService.updateAgriFieldActivityById(id, agriFieldActivityDTO);
	}// updateAgriFieldActivityById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriFieldActivityService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriFieldActivityService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriFieldActivityById(@PathVariable int id) {
		return agriFieldActivityService.deleteAgriFieldActivityById(id);
	}// deleteAgriFieldActivityById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriFieldActivityService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriFieldActivity findAgriFieldActivityById(@PathVariable int id) {
		return agriFieldActivityService.findAgriFieldActivityById(id);
	}// findAgriFieldActivityById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriFieldActivityService.moveToMaster(id);
	}

}// AgriFieldActivityController
