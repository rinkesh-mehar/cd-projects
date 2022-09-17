package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.AgriMspMbep;
import in.cropdata.cdtmasterdata.service.AgriMspMbepService;

@RestController
@RequestMapping("/agri/msp-mbep")
public class AgriMspMbepController {

	@Autowired
	private AgriMspMbepService agriMspMbepService;

	@GetMapping("/list")
	public List<AgriMspMbep> getAllAgriMspMbep() {
		return agriMspMbepService.getAllAgriMspMbep();

	}// getAllAgriMspMbep

//	@GetMapping()
//	public Page<AgriMspMbepInfo> getAllMspMbepByPaginated(@RequestParam int page, @RequestParam int size,@RequestParam String searchText) {
//		return agriMspMbepService.getAllAgriMspMbepPaginated(page, size, searchText);
//	}

	@PostMapping("/add")
	public ResponseMessage addAgriMspMbep(@RequestBody AgriMspMbep agriMspMbep) {
		return agriMspMbepService.addAgriMspMbep(agriMspMbep);
	}// addAgriMspMbep

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriMspMbepById(@PathVariable int id, @RequestBody AgriMspMbep agriMspMbep) {
		return agriMspMbepService.updateAgriMspMbepById(id, agriMspMbep);
	}// updateAgriMspMbepById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriMspMbepService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriMspMbepService.finalyApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriMspMbepById(@PathVariable int id) {
		return agriMspMbepService.deleteAgriMspMbepById(id);
	}// deleteAgriMspMbepById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriMspMbepService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriMspMbep findAgriMspMbepById(@PathVariable int id) {
		return agriMspMbepService.findAgriMspMbepById(id);
	}// findAgriMspMbepById
}
