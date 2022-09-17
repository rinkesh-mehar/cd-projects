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
import in.cropdata.cdtmasterdata.model.AgriDisposalMethod;
import in.cropdata.cdtmasterdata.service.AgriDisposalMethodService;

@RestController
@RequestMapping("/agri/disposal-method")
public class AgriDisposalMethodController {
	
	@Autowired
	private AgriDisposalMethodService agriDisposalMethodService;
	
	@GetMapping("/list")
	public List<AgriDisposalMethod> getAllAgriDisposalMethod(){
		return agriDisposalMethodService.getAllAgriDisposalMethod();
	}//getAllAgriDisposalMethod
	
	@GetMapping("/paginatedList")
	public Page<AgriDisposalMethod> getDisposalMethodListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriDisposalMethodService.getDisposalMethodListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseMessage addAgriDisposalMethod(@RequestBody AgriDisposalMethod agriDisposalMethod) {
		return agriDisposalMethodService.addAgriDisposalMethod(agriDisposalMethod);
	}//addAgriDisposalMethod
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriDisposalMethodById(@PathVariable int id, @RequestBody AgriDisposalMethod agriDisposalMethod) {
		return agriDisposalMethodService.updateAgriDisposalMethodById(id, agriDisposalMethod);
	}//updateAgriDisposalMethodById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriDisposalMethodService.primaryApproveById(id);
	}//primaryApproveById
	
	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriDisposalMethodService.finalApproveById(id);
	}//finalApproveById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriDisposalMethodById(@PathVariable int id){
		return agriDisposalMethodService.deleteAgriDisposalMethodById(id);
	}//deleteAgriDisposalMethodById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriDisposalMethodService.rejectById(id);
	}// rejectById
	
	@GetMapping("/{id}")
	public AgriDisposalMethod findAgriDisposalMethodById(@PathVariable int id) {
		return agriDisposalMethodService.findAgriDisposalMethodById(id);
	}//findAgriDisposalMethodById

}//AgriDisposalMethodController
