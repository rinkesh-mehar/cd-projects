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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStressInfDto;
import in.cropdata.cdtmasterdata.model.RegionalStress;
import in.cropdata.cdtmasterdata.service.RegionalStressService;

@RestController
@RequestMapping("/regional/stress")
public class RegionalStressController {

	@Autowired
	private RegionalStressService regionalStressService;

	@GetMapping("/list")
	public List<RegionalStressInfDto> getAllRegionalStress() {
		return regionalStressService.getAllRegionalStress();
	}// getAllRegionalStress

	@GetMapping()
	public Page<RegionalStressInfDto> getAllRegionalStressPaginated(@RequestParam("page") int page,
																	@RequestParam("size") int size,
																	@RequestParam(required = false,defaultValue = "0") String missing,
																	@RequestParam("isValid") int isValid,
																	@RequestParam(required = false, defaultValue = "") String searchText)
	{
		return regionalStressService.getAllRegionalStressPaginated(page, size,missing, searchText,isValid);

	}// getAllRegionalStressPaginated

	@PostMapping("/add")
	public ResponseMessage addRegionalStress(@RequestBody RegionalStress regionalStress) {
		return regionalStressService.addRegionalStress(regionalStress);
	}// addAllRegionalStress

	@PutMapping("/{id}/update")
	public ResponseMessage updateRegionalStressById(@PathVariable int id, @RequestBody RegionalStress regionalStress) {
		return regionalStressService.updateRegionalStressById(id, regionalStress);
	}// updateRegionalStressById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return regionalStressService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return regionalStressService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return regionalStressService.rejectById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteRegionalStressById(@PathVariable int id) {
		return regionalStressService.deleteRegionalStressById(id);
	}// deleteRegionalStressById

	@GetMapping("/{id}")
	public RegionalStress findRegionalStressById(@PathVariable int id) {
		return regionalStressService.findRegionalStressById(id);
	}// findRegionalStressById
	
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return regionalStressService.moveToMaster(id);
	}

}// RegionalStressController
