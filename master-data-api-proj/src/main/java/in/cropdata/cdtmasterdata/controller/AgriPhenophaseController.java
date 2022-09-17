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
import in.cropdata.cdtmasterdata.model.AgriPhenophase;
import in.cropdata.cdtmasterdata.model.AgriPlantPart;
import in.cropdata.cdtmasterdata.service.AgriPhenophaseService;

@RestController
@RequestMapping("/agri/phenophase")
public class AgriPhenophaseController {

	@Autowired
	private AgriPhenophaseService agriPhenophaseService;

	@GetMapping("/list")
	public List<AgriPhenophase> getAllAgriPhenophase() {
		return agriPhenophaseService.getAllAgriPheonphase();
	}// getAllAgriPhenophase
	
	@GetMapping()
	public Page<AgriPhenophase> getAllAgriPhenophasePeginated(@RequestParam int page, @RequestParam int size, @RequestParam String searchText) {
		return agriPhenophaseService.getAllAgriPhenophasePeginated(page, size,searchText);
	}// getAllAgriPhenophasePeginated

	@GetMapping("/paginatedList")
	public Page<AgriPhenophase> getPhenophaseListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriPhenophaseService.getPhenophaseListByPagenation(page, size, searchText);
	}

	
	@PostMapping("/add")
	public ResponseMessage addAgriPhenophase(@RequestBody AgriPhenophase agriPhenophase) {
		return agriPhenophaseService.addAgriPhenophase(agriPhenophase);
	}// addAgriPhenophase

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriPhenophaseById(@PathVariable int id, @RequestBody AgriPhenophase agriPhenophase) {
		return agriPhenophaseService.updateAgriPhenophaseById(id, agriPhenophase);
	}// updateAgriPhenophaseById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriPhenophaseService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriPhenophaseService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriPhenophaseById(@PathVariable int id) {
		return agriPhenophaseService.deleteAgriPhenophaseById(id);
	}// deleteAgriPhenophaseById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriPhenophaseService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriPhenophase findAgriPhenophaseById(@PathVariable int id) {
		return agriPhenophaseService.findAgriPhenophaseById(id);
	}// findAgriPhenophaseById

}// AgriPhenophaseController
