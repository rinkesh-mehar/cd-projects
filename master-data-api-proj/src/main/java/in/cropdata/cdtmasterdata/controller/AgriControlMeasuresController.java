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
import in.cropdata.cdtmasterdata.model.AgriControlMeasures;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.service.AgriControlMeasuresService;

@RestController
@RequestMapping("/agri/control-measures")
public class AgriControlMeasuresController {

	@Autowired
	private AgriControlMeasuresService agriStressControlMeasuresService;

	@GetMapping("/list")
	public List<AgriControlMeasures> getAllStressControlMeasures() {
		return agriStressControlMeasuresService.getAllAgriStressControlMeasures();
	}// getAllAgriActivity
	
	@GetMapping("/paginatedList")
	public Page<AgriControlMeasures> getPeginatedControlMeasuresList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriStressControlMeasuresService.getPeginatedControlMeasuresList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriStressControlMeasures(
			@RequestBody AgriControlMeasures agriStressControlMeasures) {
		return agriStressControlMeasuresService.addAgriStressControlMeasures(agriStressControlMeasures);
	}// addAllAgriActivity

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriStressControlMeasuresById(@PathVariable int id,
			@RequestBody AgriControlMeasures agriStressControlMeasures) {
		return agriStressControlMeasuresService.updateAgriStressControlMeasuresById(id, agriStressControlMeasures);
	}// updateAgriActivityById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriStressControlMeasuresById(@PathVariable int id) {
		return agriStressControlMeasuresService.deleteAgriStressControlMeasuresById(id);
	}// deleteAgriActivityById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriStressControlMeasuresService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriStressControlMeasuresService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriStressControlMeasuresService.rejectById(id);
	}// finalApproveById

	

	@GetMapping("/{id}")
	public AgriControlMeasures findAgriStressControlMeasuresById(@PathVariable int id) {
		return agriStressControlMeasuresService.findAgriStressControlMeasuresById(id);
	}// findAgriActivityById

}// AgriActivityController
