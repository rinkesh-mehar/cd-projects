/**
 * 
 */
package in.cropdata.farmers.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import in.cropdata.farmers.app.drk.model.TrackingQueue;
import in.cropdata.farmers.app.service.TrackingQueueService;

/**
 * @author Vivek Gajbhiye
 *
 */
@RestController
@RequestMapping("/track")
public class TrackingQueueController {
	
	@Autowired
	TrackingQueueService trackingQueueService;
	
	@PostMapping("/save_current_farmer_location")
	public ResponseEntity<?> saveFarmerCurrentLocation(@RequestBody TrackingQueue trackingQueue,HttpServletRequest request) throws IOException{
		return trackingQueueService.saveCurrentFarmerLocation(trackingQueue, request);
	}
	

}
