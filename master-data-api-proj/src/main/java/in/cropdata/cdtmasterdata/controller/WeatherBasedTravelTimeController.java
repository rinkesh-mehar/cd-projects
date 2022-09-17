package in.cropdata.cdtmasterdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.WeatherBasedTravelTime;
import in.cropdata.cdtmasterdata.service.WeatherBasedTravelTimeService;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.controller
 * @date 09/11/20
 * @time 6:27 PM
 */
@RestController
@RequestMapping("/weather-based-travel-time")
public class WeatherBasedTravelTimeController {
	@Autowired
	private WeatherBasedTravelTimeService weatherBasedTravelTimeService;

	@GetMapping("/list")
	List<WeatherBasedTravelTime> getListOfWeatherBasedTravelTime() {
		return weatherBasedTravelTimeService.getListOfWeatherBasedTravelTime();
	}

	@GetMapping()
	public Page<WeatherBasedTravelTime> getRegionalMonthWBTravelTimeWithPage(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false, defaultValue = "") String searchText) {
		return weatherBasedTravelTimeService.getListOfWeatherBasedTravelTimeWithPage(page, size, searchText);
	}

	@PostMapping("/add")
	public ResponseMessage addWeatherBasedTravelTime(@RequestBody WeatherBasedTravelTime weatherBasedTravelTime) {

		return weatherBasedTravelTimeService.addWeatherBasedTravelTime(weatherBasedTravelTime);
	}

	@PutMapping("/{id}/update")
	public ResponseMessage updateWeatherBasedTravelTime(@PathVariable Integer id,
			@RequestBody WeatherBasedTravelTime weatherBasedTravelTime) {

		return weatherBasedTravelTimeService.updateWeatherBasedTravelTime(id, weatherBasedTravelTime);
	}

	@GetMapping("/{id}")
	WeatherBasedTravelTime getWeatherBasedTravelTime(@PathVariable Integer id) {
		return weatherBasedTravelTimeService.getWeatherBasedTravelTime(id);
	}

}
