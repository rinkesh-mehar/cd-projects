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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriBenchmarkVarietyInfo;
import in.cropdata.cdtmasterdata.model.AgriBenchmarkVariety;
import in.cropdata.cdtmasterdata.service.AgriBenchmarkVarietyService;

@RestController
@RequestMapping("/agri/benchmark-variety")
public class AgriBenchmarkVarietyController {

	@Autowired
	private AgriBenchmarkVarietyService agriBenchmarkVarietyService;

	@GetMapping("/list")
	public List<AgriBenchmarkVarietyInfo> getAllAgriBenchmarkVariety() {
		return agriBenchmarkVarietyService.getAllAgriBenchmarkVariety();

	}// getAllAgriBenchmarkVariety

	@GetMapping()
	public Page<AgriBenchmarkVarietyInfo> getAllBenchmarkVarietyByPaginated(@RequestParam int page, @RequestParam int size,@RequestParam String searchText,
																			@RequestParam(required = false,defaultValue = "0") String missing) {
		return agriBenchmarkVarietyService.getAllAgriBenchmarkVarietyPaginated(page, size, searchText,missing);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriBenchmarkVariety(@RequestBody AgriBenchmarkVariety agriBenchmarkVariety) {
		return agriBenchmarkVarietyService.addAgriBenchmarkVariety(agriBenchmarkVariety);
	}// addAgriBenchmarkVariety

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriBenchmarkVarietyById(@PathVariable int id, @RequestBody AgriBenchmarkVariety agriBenchmarkVariety) {
		return agriBenchmarkVarietyService.updateAgriBenchmarkVarietyById(id, agriBenchmarkVariety);
	}// updateAgriBenchmarkVarietyById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriBenchmarkVarietyService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriBenchmarkVarietyService.finalyApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriBenchmarkVarietyById(@PathVariable int id) {
		return agriBenchmarkVarietyService.deleteAgriBenchmarkVarietyById(id);
	}// deleteAgriBenchmarkVarietyById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriBenchmarkVarietyService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriBenchmarkVariety findAgriBenchmarkVarietyById(@PathVariable int id) {
		return agriBenchmarkVarietyService.findAgriBenchmarkVarietyById(id);
	}// findAgriBenchmarkVarietyById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriBenchmarkVarietyService.moveToMaster(id);
	}

}//AgriBenchmarkVarietyController
