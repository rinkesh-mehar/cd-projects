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
import in.cropdata.cdtmasterdata.model.AgriFarmMachinery;
import in.cropdata.cdtmasterdata.service.AgriFarmMachineryService;

@RestController
@RequestMapping("/agri/farm-machinery")
public class AgriFarmMachineryController {
	
	@Autowired
	private AgriFarmMachineryService agriFarmMachineryService;
	
	@GetMapping("/list")
	public List<AgriFarmMachinery> getAllAgriFarmMachinery(){
		return agriFarmMachineryService.getAllAgriFarmMachinery();
	}//getAllAgriFarmMachinery
	
	@GetMapping("/paginatedList")
	public Page<AgriFarmMachinery> getFarmMachineryListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriFarmMachineryService.getFarmMachineryListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseMessage addAgriFarmMachinery(@RequestBody AgriFarmMachinery agriFarmMachinery) {
		return agriFarmMachineryService.addAgriFarmMachinery(agriFarmMachinery);
	}//addAgriFarmMachinery
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriFarmMachineryById(@PathVariable int id, @RequestBody AgriFarmMachinery agriFarmMachinery) {
		return agriFarmMachineryService.updateAgriFarmMachineryById(id, agriFarmMachinery);
	}//updateAgriFarmMachineryById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriFarmMachineryService.primaryApproveById(id);
	}//primaryApproveById
	
	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriFarmMachineryService.finalApproveById(id);
	}//finalApproveById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriFarmMachineryById(@PathVariable int id){
		return agriFarmMachineryService.deleteAgriFarmMachineryById(id);
	}//deleteAgriFarmMachineryById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriFarmMachineryService.rejectById(id);
	}// rejectById
	
	@GetMapping("/{id}")
	public AgriFarmMachinery findAgriFarmMachineryById(@PathVariable int id) {
		return agriFarmMachineryService.findAgriFarmMachineryById(id);
	}//findAgriFarmMachineryById

}//AgriFarmMachineryController
