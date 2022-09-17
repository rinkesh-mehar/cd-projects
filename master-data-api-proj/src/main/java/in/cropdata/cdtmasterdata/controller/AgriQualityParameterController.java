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
import in.cropdata.cdtmasterdata.model.AgriQualityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;
import in.cropdata.cdtmasterdata.service.AgriQualityParameterService;

@RestController
@RequestMapping("/agri/quality-parameter")
public class AgriQualityParameterController {
	
	@Autowired
	private AgriQualityParameterService agriQualityParameterService;

	@GetMapping("/list")
	public List<AgriQualityParameter> getQualityParameterList() {
		return agriQualityParameterService.getQualityParameterList();
	}
	
	
	@GetMapping("/paginatedList")
	public Page<AgriQualityParameterVo> getQualityParameterPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriQualityParameterService.getQualityParameterPagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriQualityParameter getQualityParameterById(@PathVariable(required = true) Integer id) {
		return agriQualityParameterService.getQualityParameterById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addQualityParameter(@RequestBody AgriQualityParameter agriQualityParameter){
		return new ResponseEntity<ResponseMessage>(agriQualityParameterService.addQualityParameter(agriQualityParameter), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateQualityParameter(@RequestBody AgriQualityParameter agriQualityParameter,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriQualityParameterService.updateQualityParameter(agriQualityParameter,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveQualityParameter(@PathVariable int id) {
		return agriQualityParameterService.approveQualityParameter(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeQualityParameter(@PathVariable int id) {
		return agriQualityParameterService.finalizeQualityParameter(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectQualityParameter(@PathVariable int id) {
		return agriQualityParameterService.rejectQualityParameter(id);
	}

	
}
