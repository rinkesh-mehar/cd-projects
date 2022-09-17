package in.cropdata.cdtmasterdata.dto;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class CropCalenderDto {

	private GeneralDto generalDto;

	private FiltersDto filtersDto;

	private Map<Integer, LinkedHashMap<String, Float>> regionalWeatherMap;

	private LinkedList<PhenophaseDto> phophaseList;

	private LinkedList<FavourableWeatherDto> favourableWeatherList;
	
	private LinkedList<CondusiveWeatherDto> conduciveWeatherList;

	public GeneralDto getGeneralDto() {
		return generalDto;
	}

	public void setGeneralDto(GeneralDto generalDto) {
		this.generalDto = generalDto;
	}

	public FiltersDto getFiltersDto() {
		return filtersDto;
	}

	public void setFiltersDto(FiltersDto filtersDto) {
		this.filtersDto = filtersDto;
	}

	public LinkedList<PhenophaseDto> getPhophaseList() {
		return phophaseList;
	}

	public void setPhophaseList(LinkedList<PhenophaseDto> phophaseList) {
		this.phophaseList = phophaseList;
	}

	public Map<Integer, LinkedHashMap<String, Float>> getRegionalWeatherMap() {
		return regionalWeatherMap;
	}

	public void setRegionalWeatherMap(Map<Integer, LinkedHashMap<String, Float>> regionalWeatherMap) {
		this.regionalWeatherMap = regionalWeatherMap;
	}

	public LinkedList<FavourableWeatherDto> getFavourableWeatherList() {
		return favourableWeatherList;
	}

	public void setFavourableWeatherList(LinkedList<FavourableWeatherDto> favourableWeatherList) {
		this.favourableWeatherList = favourableWeatherList;
	}

	public LinkedList<CondusiveWeatherDto> getConduciveWeatherList() {
		return conduciveWeatherList;
	}

	public void setConduciveWeatherList(LinkedList<CondusiveWeatherDto> conduciveWeatherList) {
		this.conduciveWeatherList = conduciveWeatherList;
	}

}
