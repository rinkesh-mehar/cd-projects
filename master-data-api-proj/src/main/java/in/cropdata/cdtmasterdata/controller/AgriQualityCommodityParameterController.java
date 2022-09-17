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
import in.cropdata.cdtmasterdata.model.AgriQualityCommodityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityCommodityParameterVO;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;
import in.cropdata.cdtmasterdata.service.AgriQualityCommodityParameterService;

@RestController
@RequestMapping("/agri/quality-commodity-parameter")
public class AgriQualityCommodityParameterController {
	
	@Autowired
	private AgriQualityCommodityParameterService agriQualityCommodityParameterService;
	
	
	@GetMapping("/paginatedList")
	public Page<AgriQualityCommodityParameterVO> getQualityCommodityParameterPagenatedList(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {

		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return agriQualityCommodityParameterService.getQualityCommodityParameterPagenatedList(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseMessage> addStressStage(@RequestBody AgriQualityCommodityParameter agriRecommendation){
		return new ResponseEntity<ResponseMessage>(agriQualityCommodityParameterService.addQualityCommodityParameter(agriRecommendation), HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseMessage> updateQualityCommodityParameter(@RequestBody AgriQualityCommodityParameter agriStressStage,@PathVariable(required = true) Integer id){
		return new ResponseEntity<ResponseMessage>(agriQualityCommodityParameterService.updateQualityCommodityParameter(agriStressStage,id), HttpStatus.OK);
	}
	
	@GetMapping("/parameter-list/{commodityId}")
	public List<AgriQualityParameterVo> getParameterListByCommodityId(@PathVariable(required = true) Integer commodityId) {
		return agriQualityCommodityParameterService.getParameterListByCommodityId(commodityId);
	}
	
}
