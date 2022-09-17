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
import in.cropdata.cdtmasterdata.model.AgriYieldCorrectionCriteria;
import in.cropdata.cdtmasterdata.service.AgriYieldCorrectionCriteriaService;

@RestController
@RequestMapping("/agri/yield-correction-criteria")
public class AgriYieldCorrectionCriteriaController {
	
	@Autowired
	private AgriYieldCorrectionCriteriaService agriYieldCorrectionCriteriaService;
	
	@GetMapping("/list")
	public List<AgriYieldCorrectionCriteria> getAllAgriYieldCorrectionCriteria(){
		return agriYieldCorrectionCriteriaService.getAllAgriYieldCorrectionCriteria();
	}//getAllAgriYieldCorrectionCriteria
	
	@PostMapping("/add")
	public ResponseMessage addAgriYieldCorrectionCriteria(@RequestBody AgriYieldCorrectionCriteria agriYieldCorrectionCriteria) {
		return agriYieldCorrectionCriteriaService.addAgriYieldCorrectionCriteria(agriYieldCorrectionCriteria);
	}// addAllAgriYieldCorrectionCriteria

	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriYieldCorrectionCriteriaById(@PathVariable int id, @RequestBody AgriYieldCorrectionCriteria agriYieldCorrectionCriteria) {
		return agriYieldCorrectionCriteriaService.updateAgriYieldCorrectionCriteriaById(id, agriYieldCorrectionCriteria);
	}// updateAgriYieldCorrectionCriteriaById

//	@PutMapping("/{id}/primary-approve")
//	public ResponseMessage primaryApproveById(@PathVariable int id) {
//		return agriYieldCorrectionCriteriaService.primaryApproveById(id);
//	}// primaryApproveById
//
//	@PutMapping("/{id}/final-approve")
//	public ResponseMessage finalApproveById(@PathVariable int id) {
//		return agriYieldCorrectionCriteriaService.finalApproveById(id);
//	}// finalApproveById
//
//	@PutMapping("/{id}/reject")
//	public ResponseMessage rejectById(@PathVariable int id) {
//		return agriYieldCorrectionCriteriaService.rejectById(id);
//	}// rejectById
//
//	@DeleteMapping("/{id}/delete")
//	public ResponseMessage deleteAgriYieldCorrectionCriteriaById(@PathVariable int id) {
//		return agriYieldCorrectionCriteriaService.deleteAgriYieldCorrectionCriteriaById(id);
//	}// deleteAgriYieldCorrectionCriteriaById

	@GetMapping("/{id}")
	public AgriYieldCorrectionCriteria findAgriYieldCorrectionCriteriaById(@PathVariable int id) {
		return agriYieldCorrectionCriteriaService.findAgriYieldCorrectionCriteriaById(id);
	}// findAgriYieldCorrectionCriteriaById

}//AgriYieldCorrectionCriteriaController
