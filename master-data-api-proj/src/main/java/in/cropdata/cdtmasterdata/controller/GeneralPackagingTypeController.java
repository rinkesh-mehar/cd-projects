package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import in.cropdata.cdtmasterdata.model.GeneralPackagingType;
import in.cropdata.cdtmasterdata.model.vo.GeneralPackagingTypeVo;
import in.cropdata.cdtmasterdata.service.GeneralPackagingTypeService;

@RestController
@RequestMapping("/general/packaging-type")
public class GeneralPackagingTypeController {
	
	@Autowired
	private GeneralPackagingTypeService generalPackagingTypeService;

	@GetMapping("/list")
	public List<GeneralPackagingType> getGeneralPackagingTypeList() {
		return generalPackagingTypeService.getGeneralPackagingTypeList();
	}
	
	
	@GetMapping("/paginatedList")
	public Page<GeneralPackagingTypeVo> getGeneralPackagingTypePagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return generalPackagingTypeService.getGeneralPackagingTypePagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public GeneralPackagingType getGeneralPackagingTypeById(@PathVariable(required = true) Integer id) {
		return generalPackagingTypeService.getGeneralPackagingTypeById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addGeneralPackagingType(@RequestBody GeneralPackagingType generalPackagingType){
		return new ResponseEntity<ResponseMessage>(generalPackagingTypeService.addGeneralPackagingType(generalPackagingType), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateGeneralPackagingType(@RequestBody GeneralPackagingType generalPackagingType,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(generalPackagingTypeService.updateGeneralPackagingType(generalPackagingType,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveGeneralPackagingType(@PathVariable int id) {
		return generalPackagingTypeService.approveGeneralPackagingType(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeGeneralPackagingType(@PathVariable int id) {
		return generalPackagingTypeService.finalizeGeneralPackagingType(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectGeneralPackagingType(@PathVariable int id) {
		return generalPackagingTypeService.rejectGeneralPackagingType(id);
	}

	
}
