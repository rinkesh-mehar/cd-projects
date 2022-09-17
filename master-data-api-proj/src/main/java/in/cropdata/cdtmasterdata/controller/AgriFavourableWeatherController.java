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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFavourableWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalLanguageDtoInf;
import in.cropdata.cdtmasterdata.model.AgriFavourableWeather;
import in.cropdata.cdtmasterdata.service.AgriFavourableWeatherService;

@RestController
@RequestMapping("/agri/favourable-weather")
public class AgriFavourableWeatherController {
	
	@Autowired
	private  AgriFavourableWeatherService agriFavourableWeatherService;

	@GetMapping("/list")
	public List<AgriFavourableWeather> getAllAgriFavourableWeather(){
		return agriFavourableWeatherService.getAllAgriAgriFavourableWeather();
	}//getAllAgriFavourableWeather

	@GetMapping()
	public Page<AgriFavourableWeatherInfDto> getAllFavourableWeatherPaginated(@RequestParam("page") int page,
																			  @RequestParam("size") int size,
																			  @RequestParam(required = false,defaultValue = "0") String missing,
																			  @RequestParam("isValid") int isValid,
																			  @RequestParam(required = false, defaultValue = "") String searchText)
	{
		return agriFavourableWeatherService.getAllFavourableWeatherPaginated(page, size,missing,searchText, isValid);
	}// getAllFavourableWeatherPaginated
	
	@PostMapping("/add")
	public ResponseMessage addAgriFavourableWeather(@RequestBody AgriFavourableWeather agriFavourableWeather) {
		return agriFavourableWeatherService.addAgriFavourableWeather(agriFavourableWeather);
	}//addAgriFavourableWeather	
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateAgriFavourableWeatherById(@PathVariable int id,@RequestBody AgriFavourableWeather agriFavourableWeather) {
		return agriFavourableWeatherService.updateAgriFavourableWeatherById(id, agriFavourableWeather);
	}//updateAgriFavourableWeatherById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return agriFavourableWeatherService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return agriFavourableWeatherService.finalApproveById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return agriFavourableWeatherService.rejectById(id);
	}

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteAgriFavourableWeatherById(@PathVariable int id) {
		return agriFavourableWeatherService.deleteAgriFavourableWeatherById(id);
	}//deleteAgriFavourableWeatherById
	
	@GetMapping("/{id}")
	public AgriFavourableWeather findAgriFavourableWeatherById(@PathVariable int id) {
		return agriFavourableWeatherService.findAgriFavourableWeatherById(id);
	}//findAgriFavourableWeatherById
	
	@PostMapping("/move-to-master/{id}")
	public ResponseMessage moveToMaster(@PathVariable Integer id) {
		return agriFavourableWeatherService.moveToMaster(id);
	}
	
	
}
