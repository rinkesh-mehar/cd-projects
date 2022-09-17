package in.cropdata.cdtmasterdata.controller;

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
import in.cropdata.cdtmasterdata.model.AgriStateCommodity;
import in.cropdata.cdtmasterdata.model.vo.AgriStateCommodityVO;
import in.cropdata.cdtmasterdata.service.AgriStateCommodityService;

@RestController
@RequestMapping("/agri/state-commodity")
public class AgriStateCommodityController {
	
	@Autowired
	private AgriStateCommodityService agriStateCommodityService;
	
	
	@GetMapping("/paginatedList")
	public Page<AgriStateCommodityVO> getAgriStateCommodityPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriStateCommodityService.getAgriStateCommodityPagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriStateCommodity getAgriStateCommodityById(@PathVariable(required = true) Integer id) {
		return agriStateCommodityService.getAgriStateCommodityById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addStressStage(@RequestBody AgriStateCommodity agriStateCommodity){
		return new ResponseEntity<ResponseMessage>(agriStateCommodityService.addStateCommodity(agriStateCommodity), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateStateCommodity(@RequestBody AgriStateCommodity agriStateCommodity,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriStateCommodityService.updateStateCommodity(agriStateCommodity,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveAgriStateCommodity(@PathVariable int id) {
		return agriStateCommodityService.approveAgriStateCommodity(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeAgriStateCommodity(@PathVariable int id) {
		return agriStateCommodityService.finalizeAgriStateCommodity(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectAgriStateCommodity(@PathVariable int id) {
		return agriStateCommodityService.rejectAgriStateCommodity(id);
	}

	
}
