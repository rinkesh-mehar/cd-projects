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
import in.cropdata.cdtmasterdata.model.AgriStressType;
import in.cropdata.cdtmasterdata.service.AgriStressTypeService;

@RestController
@RequestMapping("/agri/stress-type")
public class AgriStressTypeController {

	@Autowired
	private AgriStressTypeService agriStressTypeService;

	@GetMapping("/list")
	public List<AgriStressType> getAllAgriStressType() {
		return agriStressTypeService.getAllAgriStressType();
	}// getAllAgriStressType
	
	@GetMapping("/paginatedList")
	public Page<AgriStressType> getPeginatedStressTypeList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriStressTypeService.getPeginatedStressTypeList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriStressType(@RequestBody AgriStressType agriStressType) {
		return agriStressTypeService.addAgriStressType(agriStressType);
	}// addAllAgriStressType

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressTypeById(@PathVariable int id, @RequestBody AgriStressType agriStressType) {
		return agriStressTypeService.updateAgriStressTypeById(id, agriStressType);
	}// updateAgriStressTypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressTypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressTypeService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressTypeById(@PathVariable int id) {
		return agriStressTypeService.deleteAgriStressTypeById(id);
	}// deleteAgriStressTypeById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStressTypeService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriStressType findAgriStressTypeById(@PathVariable int id) {
		return agriStressTypeService.findAgriStressTypeById(id);
	}// findAgriStressTypeById

}// AgriStressTypeController
