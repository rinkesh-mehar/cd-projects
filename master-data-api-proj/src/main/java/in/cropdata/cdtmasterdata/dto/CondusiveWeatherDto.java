package in.cropdata.cdtmasterdata.dto;

import java.util.LinkedHashMap;

public class CondusiveWeatherDto {

	private String bioticStress;
	private Integer startWeek;
	private Integer endWeek;

	private Integer span;

	LinkedHashMap<String, CondusiveSpecificationDto> conduciveWeatherMap;

	public String getBioticStress() {
		return bioticStress;
	}

	public void setBioticStress(String bioticStress) {
		this.bioticStress = bioticStress;
	}

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

	public Integer getSpan() {
		return span;
	}

	public void setSpan(Integer span) {
		this.span = span;
	}

	public LinkedHashMap<String, CondusiveSpecificationDto> getConduciveWeatherMap() {
		return conduciveWeatherMap;
	}

	public void setConduciveWeatherMap(LinkedHashMap<String, CondusiveSpecificationDto> conduciveWeatherMap) {
		this.conduciveWeatherMap = conduciveWeatherMap;
	}

}
