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
import in.cropdata.cdtmasterdata.model.AgriCommodityGroup;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityGroupVo;
import in.cropdata.cdtmasterdata.service.AgriCommodityGroupService;

@RestController
@RequestMapping("/agri/commodity-group")
public class AgriCommodityGroupController {
	
	@Autowired
	private AgriCommodityGroupService agriCommodityGroupService;

	@GetMapping("/list")
	public List<AgriCommodityGroup> getCommodityGroupList() {
		return agriCommodityGroupService.getCommodityGroupList();
	}
	
	
	@GetMapping("/paginatedList")
	public Page<AgriCommodityGroupVo> getCommodityGroupPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriCommodityGroupService.getCommodityGroupPagenatedList(page, size, searchText);
	}
	
	@GetMapping("/id/{id}")
	public AgriCommodityGroup getCommodityGroupById(@PathVariable(required = true) Integer id) {
		return agriCommodityGroupService.getCommodityGroupById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addCommodityGroup(@RequestBody AgriCommodityGroup agriCommodityGroup){
		return new ResponseEntity<ResponseMessage>(agriCommodityGroupService.addCommodityGroup(agriCommodityGroup), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateCommodityGroup(@RequestBody AgriCommodityGroup agriCommodityGroup,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriCommodityGroupService.updateCommodityGroup(agriCommodityGroup,id), HttpStatus.OK);
	}
	
	@PutMapping("/primary-approve/{id}")
	public ResponseMessage approveCommodityGroup(@PathVariable int id) {
		return agriCommodityGroupService.approveCommodityGroup(id);
	}
	
	@PutMapping("/final-approve/{id}")
	public ResponseMessage finalizeCommodityGroup(@PathVariable int id) {
		return agriCommodityGroupService.finalizeCommodityGroup(id);
	}
	
	@PutMapping("/reject/{id}")
	public ResponseMessage rejectCommodityGroup(@PathVariable int id) {
		return agriCommodityGroupService.rejectCommodityGroup(id);
	}

	
}
