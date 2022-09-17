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
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.model.AgriEcosystem;
import in.cropdata.cdtmasterdata.service.AgriEcosystemService;

@RestController
@RequestMapping("/agri/ecosystem")
public class AgriEcosystemController {

	@Autowired
	private AgriEcosystemService agriEcosystemService;

	@GetMapping("/list")
	public List<AgriEcosystem> getAllAgriEcosystem() {
		return agriEcosystemService.getAllAgriEcosystem();
	}// getAllAgriEcosystem
	
	@GetMapping("/paginatedList")
	public Page<AgriEcosystem> getAgriEcosystemListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriEcosystemService.getAgriEcosystemListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriEcosystem(@RequestBody AgriEcosystem agriEcosystem) {
		return agriEcosystemService.addAgriEcosystem(agriEcosystem);
	}// addAgriEcosystem

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriEcosystemById(@PathVariable int id, @RequestBody AgriEcosystem agriEcosystem) {
		return agriEcosystemService.updateAgriEcosystemById(id, agriEcosystem);
	}// updateAgriEcosystemById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriEcosystemService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriEcosystemService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriEcosystemById(@PathVariable int id) {
		return agriEcosystemService.deleteAgriEcosystemById(id);
	}// deleteAgriEcosystemById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriEcosystemService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriEcosystem findAgriEcosystemById(@PathVariable int id) {
		return agriEcosystemService.findAgriEcosystemById(id);
	}// findAgriEcosystemById
}// AgriEcosystemController
