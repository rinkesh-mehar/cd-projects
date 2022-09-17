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
import in.cropdata.cdtmasterdata.model.GeneralModeOfPayment;
import in.cropdata.cdtmasterdata.service.GeneralModeOfPaymentService;

@RestController
@RequestMapping("/general/mode-of-payment")
public class GeneralModeOfPaymentController {
	
	@Autowired
	private GeneralModeOfPaymentService generalModeOfPaymentService;
	
	@GetMapping("/list")
	public List<GeneralModeOfPayment> getAllGeneralModeOfPayment() {
		return generalModeOfPaymentService.getAllGeneralModeOfPayment();
	}// getAllGeneralModeOfPayment

	@GetMapping("/paginatedList")
	public Page<GeneralModeOfPayment> getAllGeneralModeOfPaymentPaginated(@RequestParam int page, @RequestParam int size) {
		return generalModeOfPaymentService.getAllGeneralModeOfPaymentPaginated(page, size);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralModeOfPayment(@RequestBody GeneralModeOfPayment GeneralModeOfPayment) {
		return generalModeOfPaymentService.addGeneralModeOfPayment(GeneralModeOfPayment);
	}// addAllGeneralModeOfPayment

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralModeOfPaymentById(@PathVariable int id,
			@RequestBody GeneralModeOfPayment GeneralModeOfPayment) {
		return generalModeOfPaymentService.updateGeneralModeOfPaymentById(id, GeneralModeOfPayment);
	}// updateGeneralModeOfPaymentById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalModeOfPaymentService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalModeOfPaymentService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralModeOfPaymentById(@PathVariable int id) {
		return generalModeOfPaymentService.deleteGeneralModeOfPaymentById(id);
	}// deleteGeneralModeOfPaymentById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalModeOfPaymentService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralModeOfPayment findGeneralModeOfPaymentById(@PathVariable int id) {
		return generalModeOfPaymentService.findGeneralModeOfPaymentById(id);
	}// findGeneralModeOfPaymentById
	

}

