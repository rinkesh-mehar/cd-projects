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
import in.cropdata.cdtmasterdata.model.AgriRecommendation;
import in.cropdata.cdtmasterdata.model.vo.AgriRecommendationVo;
import in.cropdata.cdtmasterdata.service.AgriRecommendationService;

@RestController
@RequestMapping("/agri/recommendation")
public class AgriRecommendationController {
	
	@Autowired
	private AgriRecommendationService agriRecommendationService;

	@GetMapping("/list")
	public List<AgriRecommendation> getRecommendationList() {
		return agriRecommendationService.getRecommendationList();
	}
	
	
	@GetMapping("/paginatedList")
	public Page<AgriRecommendationVo> getRecommendationPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriRecommendationService.getRecommendationPagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriRecommendation getRecommendationById(@PathVariable(required = true) Integer id) {
		return agriRecommendationService.getRecommendationById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addRecommendation(@RequestBody AgriRecommendation agriRecommendation){
		return new ResponseEntity<ResponseMessage>(agriRecommendationService.addRecommendation(agriRecommendation), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateRecommendation(@RequestBody AgriRecommendation agriRecommendation,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriRecommendationService.updateRecommendation(agriRecommendation,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveRecommendation(@PathVariable int id) {
		return agriRecommendationService.approveRecommendation(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeRecommendation(@PathVariable int id) {
		return agriRecommendationService.finalizeRecommendation(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectRecommendation(@PathVariable int id) {
		return agriRecommendationService.rejectRecommendation(id);
	}

	
}
