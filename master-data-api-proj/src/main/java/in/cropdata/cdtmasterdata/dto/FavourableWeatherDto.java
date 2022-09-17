package in.cropdata.cdtmasterdata.dto;

import java.util.LinkedHashMap;

public class FavourableWeatherDto {

	private Integer startWeek;
	private Integer endWeek;

	private Integer span;

	LinkedHashMap<String, favourableSpecificationDto> favourableWeatherMap;

	public Integer getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	public Integer getSpan() {
		return span;
	}

	public void setSpan(Integer span) {
		this.span = span;
	}

	public LinkedHashMap<String, favourableSpecificationDto> getFavourableWeatherMap() {
		return favourableWeatherMap;
	}

	public void setFavourableWeatherMap(LinkedHashMap<String, favourableSpecificationDto> favourableWeatherMap) {
		this.favourableWeatherMap = favourableWeatherMap;
	}

	public Integer getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

}
