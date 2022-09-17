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
import in.cropdata.cdtmasterdata.model.AgriDoseFactor;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.service.AgriDoseFactorService;

@RestController
@RequestMapping("/agri/dose-factor")
public class AgriDoseFactorController {
	
	@Autowired
	private AgriDoseFactorService agriDoseFactorService;

	@GetMapping("/list")
	public List<AgriDoseFactor> getAllAgriDoseFactor() {
		return agriDoseFactorService.getAllAgriDoseFactor();
	}// getAllAgriDoseFactor
	
	@GetMapping("/paginatedList")
	public Page<AgriDoseFactor> getPeginatedAgriDoseFactorList(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return agriDoseFactorService.getPeginatedAgriDoseFactorList(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addAgriDoseFactor(@RequestBody AgriDoseFactor AgriDoseFactor) {
		return agriDoseFactorService.addAgriDoseFactor(AgriDoseFactor);
	}// addAgriDoseFactor

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriDoseFactorById(@PathVariable int id, @RequestBody AgriDoseFactor AgriDoseFactor) {
		return agriDoseFactorService.updateAgriDoseFactorById(id, AgriDoseFactor);
	}// updateAgriDoseFactorById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriDoseFactorService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriDoseFactorService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriDoseFactorById(@PathVariable int id) {
		return agriDoseFactorService.deleteAgriDoseFactorById(id);
	}// deleteAgriDoseFactorById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriDoseFactorService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public AgriDoseFactor findAgriDoseFactorById(@PathVariable int id) {
		return agriDoseFactorService.findAgriDoseFactorById(id);
	}// findAgriDoseFactorById
}// AgriDoseFactorController

