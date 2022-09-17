package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCondusiveWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriCondusiveWeather;
import in.cropdata.cdtmasterdata.service.AgriCondusiveWeatherService;

@RestController
@RequestMapping("/agri/conducive-weather")
public class AgriCondusiveWeatherController {
	
	@Autowired
	private AgriCondusiveWeatherService agriCondusiveWeatherService;
	
	@GetMapping("/list")
	public List<AgriCondusiveWeather> getAllAgriCondusiveWeather(){
		return agriCondusiveWeatherService.getAllAgriAgriCondusiveWeather();
	}//getAllAgriCondusiveWeather

	@GetMapping()
	public Page<AgriCondusiveWeatherInfDto> getAllAgriCondusiveWeatherPaginated(@RequestParam("page") int page,
																				@RequestParam("size") int size,
																				@RequestParam(required = false,defaultValue = "0") String missing,
																				@RequestParam("isValid") int isValid,
																				@RequestParam(required = false, defaultValue = "") String searchText)
	{
		return agriCondusiveWeatherService.getAllAgriCondusiveWeatherPaginated(page, size, missing,searchText, isValid);
	}// getAllAgriCondusiveWeatherPaginated
	
	@PostMapping("/add")
	public ResponseMessage addAgriCondusiveWeather(@RequestBody AgriCondusiveWeather agriCondusiveWeather) {
		return agriCondusiveWeatherService.addAgriCondusiveWeather(agriCondusiveWeather);
	}//addAgriCondusiveWeather
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriCondusiveWeatherById(@PathVariable int id,@RequestBody AgriCondusiveWeather agriCondusiveWeather) {
		return agriCondusiveWeatherService.updateAgriCondusiveWeatherById(id, agriCondusiveWeather);
	}//updateAgriCondusiveWeatherById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriCondusiveWeatherService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriCondusiveWeatherService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriCondusiveWeatherService.rejectById(id);
	}// finalApproveById

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriCondusiveWeatherById(@PathVariable int id) {
		return agriCondusiveWeatherService.deleteAgriCondusiveWeatherById(id);
	}//deleteAgriCondusiveWeatherById
	
	@GetMapping("/{id}")
	public AgriCondusiveWeather findAgriCondusiveWeatherById(@PathVariable int id) {
		return agriCondusiveWeatherService.findAgriCondusiveWeatherById(id);
	}//findAgriCondusiveWeatherById
	
	@GetMapping("/{commodityId}/{stressTypeId}")
	public List<AgriDistrictCommodityStressInfDto> getCommodityIdAndStressTypeId(@PathVariable int commodityId, @PathVariable int stressTypeId){
		return agriCondusiveWeatherService.getByCommodityIdAndStressTypeId(commodityId, stressTypeId);
	}//getCommodityIdAndStressTypeId
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriCondusiveWeatherService.moveToMaster(id);
	}
	
	@GetMapping("/stress-list/{commodityId}/{stressTypeId}")
	public List<AgriDistrictCommodityStressInfDto> getStressByCommodityIdAndStressTypeId(@PathVariable int commodityId, @PathVariable int stressTypeId){
		return agriCondusiveWeatherService.getStressByCommodityIdAndStressTypeId(commodityId, stressTypeId);
	}


}//AgriCondusiveWeatherController
