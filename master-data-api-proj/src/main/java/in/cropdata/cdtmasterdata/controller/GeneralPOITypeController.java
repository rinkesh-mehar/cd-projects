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
import in.cropdata.cdtmasterdata.model.GeneralPOIType;
import in.cropdata.cdtmasterdata.service.GeneralPOITypeService;

@RestController
@RequestMapping("/general/poi-type")
public class GeneralPOITypeController {
	
	@Autowired
	private GeneralPOITypeService generalPOITypeService;
	
	@GetMapping("/list")
	public List<GeneralPOIType> getAllGeneralPOIType() {
		return generalPOITypeService.getAllGeneralPOIType();
	}// getAllGeneralPOIType

	@GetMapping("/paginatedList")
	public Page<GeneralPOIType> getGeneralPOITypeListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return generalPOITypeService.getGeneralPOITypeListByPagenation(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addGeneralPOIType(@RequestBody GeneralPOIType GeneralPOIType) {
		return generalPOITypeService.addGeneralPOIType(GeneralPOIType);
	}// addAllGeneralPOIType

	@PutMapping("/{id}/update")
	public ResponseMessage updateGeneralPOITypeById(@PathVariable int id,
			@RequestBody GeneralPOIType GeneralPOIType) {
		return generalPOITypeService.updateGeneralPOITypeById(id, GeneralPOIType);
	}// updateGeneralPOITypeById

	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return generalPOITypeService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return generalPOITypeService.finalApproveById(id);
	}// finalApproveById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGeneralPOITypeById(@PathVariable int id) {
		return generalPOITypeService.deleteGeneralPOITypeById(id);
	}// deleteGeneralPOITypeById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return generalPOITypeService.rejectById(id);
	}// rejectById

	@GetMapping("/{id}")
	public GeneralPOIType findGeneralPOITypeById(@PathVariable int id) {
		return generalPOITypeService.findGeneralPOITypeById(id);
	}// findGeneralPOITypeById
	

}

