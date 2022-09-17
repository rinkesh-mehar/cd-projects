/**
 * 
 */
package in.cropdata.farmers.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.farmers.app.service.FarmerStressAdvisoryService;

/**
 * @author cropdata-Aniket Naik
 *
 */

@RestController
@RequestMapping(value = "/farmer")
public class FarmerStressAdvisoryNotifyController {
	
	@Autowired
	private FarmerStressAdvisoryService farmerStressAdvisoryService;

	
	  @GetMapping("/notifyStress") 
	  public Map<String,Object> sendStressAdvisory(@RequestParam("deviceToken") 
	                                                                    String deviceToken){
		  
		  return farmerStressAdvisoryService.notifyStress(deviceToken);
	  }
	
	
	
}
