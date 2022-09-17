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
import in.cropdata.cdtmasterdata.model.WeatherParams;
import in.cropdata.cdtmasterdata.service.WeatherParamsService;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralWeatherParameterDto;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;

@RestController
@RequestMapping("/general/weather-params")
public class WeatherParamsController {
	
	@Autowired
	private WeatherParamsService weatherParamsService;
		
	@GetMapping("/list")
	public List<WeatherParams> getAllWeatherParams(){
		return weatherParamsService.getAllWeatherParams();
	}//getAllWeatherParams
	
	@GetMapping("/paginatedList")
	public Page<GeneralWeatherParameterDto> getWeatherParamListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return weatherParamsService.getWeatherParamListByPagenation(page, size, searchText);
	}
	
	@PostMapping("/add")
	public ResponseMessage addWeatherParams(@RequestBody WeatherParams weatherParams) {
		return weatherParamsService.addWeatherParams(weatherParams);
	}//addWeatherParams
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateWeatherParamsById(@PathVariable int id,@RequestBody WeatherParams weatherParams) {
		return weatherParamsService.updateWeatherParamsById(id, weatherParams);
	}//updateWeatherParamsById
	
	@PutMapping("/{id}/reject")
	public ResponseMessage rejectById(@PathVariable int id) {
		return weatherParamsService.rejectById(id);
	}// finalApproveById
	
	@PutMapping("/{id}/primary-approve")
	public ResponseMessage primaryApproveById(@PathVariable int id) {
		return weatherParamsService.primaryApproveById(id);
	}// primaryApproveById

	@PutMapping("/{id}/final-approve")
	public ResponseMessage finalApproveById(@PathVariable int id) {
		return weatherParamsService.finalApproveById(id);
	}// finalApproveById

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteWeatherParamsById(@PathVariable int id) {
		return weatherParamsService.deleteWeatherParamsById(id);
	}//deleteWeatherParamsById
	
	@GetMapping("/{id}")
	public WeatherParams findWeatherParamsById(@PathVariable int id) {
		return weatherParamsService.findWeatherParamsById(id);
	}//findWeatherParamsById

}
