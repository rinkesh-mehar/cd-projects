package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.model.WeatherBasedTravelTime;
import in.cropdata.cdtmasterdata.repository.WeatherBasedTravelTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.service
 * @date 09/11/20
 * @time 6:25 PM
 */
@Service
public class WeatherBasedTravelTimeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherBasedTravelTime.class);

	@Autowired
	private WeatherBasedTravelTimeRepository weatherBasedTravelTimeRepository;

	public List<WeatherBasedTravelTime> getListOfWeatherBasedTravelTime() {
		return weatherBasedTravelTimeRepository.findAll(Sort.by("name"));
	}

	public Page<WeatherBasedTravelTime> getListOfWeatherBasedTravelTimeWithPage(int page, int size, String searchText) {
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").descending());

			return weatherBasedTravelTimeRepository.getListOfWeatherBasedTravelTimeWithPage(sortedByIdAsc, searchText);
		} catch (Exception e) {
			LOGGER.error("Something is wrong {}", e.getMessage());
			throw e;
		}
	}

	public ResponseMessage addWeatherBasedTravelTime(WeatherBasedTravelTime weatherBasedTravelTime) {

		ResponseMessage message = new ResponseMessage();
		try {

			if (weatherBasedTravelTime != null) {

				WeatherBasedTravelTime travelTime = weatherBasedTravelTimeRepository.save(weatherBasedTravelTime);
				if (travelTime != null) {
					message.setMessage("Weather Based Travel Time Added Successfully");
					message.setSuccess(true);
				}
			} else {
				message.setMessage("Empty Input for Weather Based Travel Time");
				message.setSuccess(false);
			}

		} catch (Exception e) {
			message.setMessage("Error While Adding Weather Based Travel Time");
			message.setSuccess(false);
		}
		return message;
	}

	public WeatherBasedTravelTime getWeatherBasedTravelTime(Integer id) {

		Optional<WeatherBasedTravelTime> weatherBasedTravelTime = null;
		try {
			if (id != null && id > 0) {
				weatherBasedTravelTime = weatherBasedTravelTimeRepository.findById(id);
			}
			return weatherBasedTravelTime.get();
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage updateWeatherBasedTravelTime(Integer id, WeatherBasedTravelTime weatherBasedTravelTime) {

		ResponseMessage message = new ResponseMessage();
		try {

			if (id != null && weatherBasedTravelTime != null) {

				WeatherBasedTravelTime travelTime = weatherBasedTravelTimeRepository.save(weatherBasedTravelTime);
				if (travelTime != null) {
					message.setMessage("Weather Based Travel Time Updated Successfully");
					message.setSuccess(true);
				}
			} else {
				message.setMessage("Empty Input for Weather Based Travel Time");
				message.setSuccess(false);
			}

		} catch (Exception e) {
			message.setMessage("Error While Updating Weather Based Travel Time");
			message.setSuccess(false);
		}
		return message;
	}
}
