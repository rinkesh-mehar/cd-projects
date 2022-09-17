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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHealthInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriStandardQuantityChartInfDto;
import in.cropdata.cdtmasterdata.model.AgriHealth;
import in.cropdata.cdtmasterdata.service.AgriHealthService;

@RestController
@RequestMapping("/agri/health")
public class AgriHealthController {
	
	@Autowired
	private AgriHealthService agriHealthService;
	
	@GetMapping("/list")
	public List<AgriHealth> getAllAgriHealth(){
		return agriHealthService.getAllAgriAgriHealth();
	}//getAllAgriHealth

	@GetMapping()
	public Page<AgriHealthInfDto> getAllAgriHealthPaginated(@RequestParam("page") int page,
															@RequestParam("size") int size,
															@RequestParam("isValid") int isValid,
															@RequestParam(required = false, defaultValue = "") String searchText,
															@RequestParam(required = false, defaultValue = "0") String missing)
	{
		return agriHealthService.getAllAgriHealthPaginated(page, size, searchText, isValid,missing);
	}// getAllAgriHealthPaginated
	
	@PostMapping("/add")
	public ResponseMessage addAgriHealth(@RequestBody AgriHealth agriHealth) {
		return agriHealthService.addAgriHealth(agriHealth);
	}//addAgriHealth
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriHealthById(@PathVariable int id,@RequestBody AgriHealth agriHealth) {
		return agriHealthService.updateAgriHealthById(id, agriHealth);
	}//updateAgriHealthById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriHealthService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriHealthService.finalApproveById(id);
	}// finalApproveById

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriHealthById(@PathVariable int id) {
		return agriHealthService.deleteAgriHealthById(id);
	}//deleteAgriHealthById
	
	@GetMapping("/{id}")
	public AgriHealth findAgriHealthById(@PathVariable int id) {
		return agriHealthService.findAgriHealthById(id);
	}//findAgriHealthById
	
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriHealthService.moveToMaster(id);
	}
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectAgriHealthById(@PathVariable int id) {
		return agriHealthService.rejectAgriHealthById(id);
	}

}//AgriHealthController
