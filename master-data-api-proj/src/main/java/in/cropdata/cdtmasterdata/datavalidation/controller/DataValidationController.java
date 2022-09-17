package in.cropdata.cdtmasterdata.datavalidation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.datavalidation.service.DataValidationService;

@RestController
@RequestMapping("/data-validation")
public class DataValidationController {
	
	@Autowired
	private DataValidationService dataValidationService;

	@GetMapping("/master-data")
	public Map<String, Object> getDataValidation(){
		return dataValidationService.getDataValidation();
	}//getDataValidation
	
}//DataValidationController
