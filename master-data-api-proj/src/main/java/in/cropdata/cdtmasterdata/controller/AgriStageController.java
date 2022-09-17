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
import in.cropdata.cdtmasterdata.model.AgriStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;
import in.cropdata.cdtmasterdata.service.AgriStageService;

@RestController
@RequestMapping("/agri/stage")
public class AgriStageController {

	@Autowired
	private AgriStageService agriStageService;
	
	@GetMapping("/list")
	public List<AgriStage> getStageList() {
		return agriStageService.getStageList();
	}
	
	@GetMapping("/paginatedList")
	public Page<AgriStageVO> getStagePaginatedList(@RequestParam("page") Integer page,@RequestParam("size") Integer size,String searchText){
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}
		return agriStageService.getStagePaginatedList(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addStage(@RequestBody AgriStage agriStage){
		return new ResponseEntity<ResponseMessage>(agriStageService.addStage(agriStage),HttpStatus.CREATED);
	}
	
	@GetMapping("/id/{id}")
	public AgriStage getStageById(@PathVariable(required = true) Integer id) {
		return agriStageService.getStageById(id);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateStage(@RequestBody AgriStage agriStage, @PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriStageService.updateStage(agriStage, id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveStage(@PathVariable int id) {
		return agriStageService.approveStage(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeStage(@PathVariable int id) {
		return agriStageService.finalizeStage(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectStage(@PathVariable int id) {
		return agriStageService.rejectStage(id);
	}
}
