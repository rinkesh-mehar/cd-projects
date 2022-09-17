package in.cropdata.cdtmasterdata.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneralDto {

	private Integer startWeek;
	private Integer endWeek;

	private Map<String, List<Integer>> months;

	private LinkedHashMap<String, String> weatherParams;

	public Integer getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}

	public Integer getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}

	public Map<String, List<Integer>> getMonths() {
		return months;
	}

	public void setMonths(Map<String, List<Integer>> months) {
		this.months = months;
	}

	public LinkedHashMap<String, String> getWeatherParams() {
		return weatherParams;
	}

	public void setWeatherParams(LinkedHashMap<String, String> weatherParams) {
		this.weatherParams = weatherParams;
	}

}
