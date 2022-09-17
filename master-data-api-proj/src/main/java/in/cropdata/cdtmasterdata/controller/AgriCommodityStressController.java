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
import in.cropdata.cdtmasterdata.model.AgriCommodityStress;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityStressVO;
import in.cropdata.cdtmasterdata.service.AgriCommodityStressService;

@RestController
@RequestMapping("/agri/commodity-stress")
public class AgriCommodityStressController {
	
	@Autowired
	private AgriCommodityStressService agriCommodityStressService;	
	
	@GetMapping("/paginatedList")
	public Page<AgriCommodityStressVO> getCommodityStressPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriCommodityStressService.getCommodityStressPagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriCommodityStress getCommodityStressById(@PathVariable(required = true) Integer id) {
		return agriCommodityStressService.getCommodityStressById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addCommodityStress(@RequestBody AgriCommodityStress agriCommodityStress){
		return new ResponseEntity<ResponseMessage>(agriCommodityStressService.addCommodityStress(agriCommodityStress), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateCommodityStress(@RequestBody AgriCommodityStress agriCommodityStress,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriCommodityStressService.updateCommodityStress(agriCommodityStress,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveCommodityStress(@PathVariable int id) {
		return agriCommodityStressService.approveCommodityStress(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeCommodityStress(@PathVariable int id) {
		return agriCommodityStressService.finalizeCommodityStress(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectCommodityStress(@PathVariable int id) {
		return agriCommodityStressService.rejectCommodityStress(id);
	}

	
}
