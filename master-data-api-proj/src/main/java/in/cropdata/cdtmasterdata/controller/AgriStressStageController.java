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
import in.cropdata.cdtmasterdata.model.AgriStressStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStressStageVO;
import in.cropdata.cdtmasterdata.service.AgriStressStageService;

@RestController
@RequestMapping("/agri/stress-stage")
public class AgriStressStageController {
	
	@Autowired
	private AgriStressStageService agriStressStageService;
	
	
	@GetMapping("/paginatedList")
	public Page<AgriStressStageVO> getStressStagePagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriStressStageService.getStressStagePagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriStressStage getRecommendationById(@PathVariable(required = true) Integer id) {
		return agriStressStageService.getRecommendationById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addStressStage(@RequestBody AgriStressStage agriRecommendation){
		return new ResponseEntity<ResponseMessage>(agriStressStageService.addStressStage(agriRecommendation), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateStressStage(@RequestBody AgriStressStage agriStressStage,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriStressStageService.updateStressStage(agriStressStage,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveStressStage(@PathVariable int id) {
		return agriStressStageService.approveStressStage(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeStressStage(@PathVariable int id) {
		return agriStressStageService.finalizeStressStage(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectStressStage(@PathVariable int id) {
		return agriStressStageService.rejectStressStage(id);
	}

	
}
