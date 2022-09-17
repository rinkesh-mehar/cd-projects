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
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalInstructions;
import in.cropdata.cdtmasterdata.model.vo.AgriAgrochemicalInstructionsVo;
import in.cropdata.cdtmasterdata.service.AgriAgrochemicalInstructionsService;

@RestController
@RequestMapping("/agri/agrochemical-instructions")
public class AgriAgrochemicalInstructionsController {
	
	@Autowired
	private AgriAgrochemicalInstructionsService agriAgrochemicalInstructionsService;

	@GetMapping("/list")
	public List<AgriAgrochemicalInstructions> getAgrochemicalInstructionsList() {
		return agriAgrochemicalInstructionsService.getAgrochemicalInstructionsList();
	}
	
	@GetMapping("/paginatedList")
	Page<AgriAgrochemicalInstructionsVo> getAgrochemicalInstructionsPaginatedList(@RequestParam("page") Integer page,@RequestParam("size") Integer size,
			@RequestParam(required = false,defaultValue = "") String searchText){
		return agriAgrochemicalInstructionsService.getAgrochemicalInstructionsPaginatedList(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addAgrochemicalInstructions(@RequestBody AgriAgrochemicalInstructions agriAgrochemicalInstructions){
		return new ResponseEntity<ResponseMessage>(agriAgrochemicalInstructionsService.addAgrochemicalInstructions(agriAgrochemicalInstructions),HttpStatus.CREATED);
	}
	
	@GetMapping("id/{id}")
	public AgriAgrochemicalInstructions getAgrochemicalInstructionsById(@PathVariable(required = true) Integer id) {
		return agriAgrochemicalInstructionsService.getAgrochemicalInstructionsById(id);
	}
 	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateAgrochemicalInstructions(@RequestBody AgriAgrochemicalInstructions agriAgrochemicalInstructions, @PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriAgrochemicalInstructionsService.updateAgrochemicalInstructions(agriAgrochemicalInstructions, id),HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveAgrochemicalInstructions(@PathVariable int id) {
		return agriAgrochemicalInstructionsService.approveAgrochemicalInstructions(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeAgrochemicalInstructions(@PathVariable int id) {
		return agriAgrochemicalInstructionsService.finalizeAgrochemicalInstructions(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectAgrochemicalInstructions(@PathVariable int id) {
		return agriAgrochemicalInstructionsService.rejectAgrochemicalInstructions(id);
	}
	
}
