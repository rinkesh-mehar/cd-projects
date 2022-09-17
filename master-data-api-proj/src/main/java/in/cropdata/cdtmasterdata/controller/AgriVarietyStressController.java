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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriVarietyStress;
import in.cropdata.cdtmasterdata.service.AgriVarietyStressService;

@RestController
@RequestMapping("/agri/variety-stress")
public class AgriVarietyStressController {

	@Autowired
	private AgriVarietyStressService agriVarietyStressService;

	@GetMapping("/list")
	public List<AgriVarietyStress> getAllAgriVarietyStress() {
		return agriVarietyStressService.getAllAgriVarietyStress();
	}// getAllAgriVarietyStress

	@GetMapping()
	public Page<AgriVarietyStressInfDto> getAllAgriVArietyStressPaginated(@RequestParam("page") int page,
																		  @RequestParam("isValid") int isValid,
																		  @RequestParam("size") int size,
																		  @RequestParam(required = false, defaultValue = "") String searchText,
																		  @RequestParam(required = false,defaultValue = "0") String missing)
	{
		return agriVarietyStressService.getAllAgriVarietyStressPaginated(page, size, searchText, isValid,missing);
	}// getAllAgriVArietyStressPaginated

	@PostMapping("/add")
	public ResponseMessage addAgriVarietyStress(@RequestBody AgriVarietyStress agriVarietyStress) {
		return agriVarietyStressService.addAgriVarietyStress(agriVarietyStress);
	}// addAgriVarietyStress

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriVarietyStressById(@PathVariable int id,
			@RequestBody AgriVarietyStress agriVarietyStress) {
		return agriVarietyStressService.updateAgriVarietyStressById(id, agriVarietyStress);
	}// updateAgriVarietyStressById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriVarietyStressService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriVarietyStressService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriVarietyStressById(@PathVariable int id) {
		return agriVarietyStressService.deleteAgriVarietyStressById(id);
	}// deleteAgriVarietyStressById

	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriVarietyStressService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriVarietyStress findAgriVarietyStressById(@PathVariable int id) {
		return agriVarietyStressService.findAgriVarietyStressById(id);
	}
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriVarietyStressService.moveToMaster(id);
	}

}// AgriVarietyStressController