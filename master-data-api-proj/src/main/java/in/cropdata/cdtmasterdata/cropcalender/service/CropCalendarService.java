package in.cropdata.cdtmasterdata.cropcalender.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.dto.CondusiveSpecificationDto;
import in.cropdata.cdtmasterdata.dto.CondusiveWeatherDto;
import in.cropdata.cdtmasterdata.dto.CropCalenderDto;
import in.cropdata.cdtmasterdata.dto.FavourableWeatherDto;
import in.cropdata.cdtmasterdata.dto.FiltersDto;
import in.cropdata.cdtmasterdata.dto.GeneralDto;
import in.cropdata.cdtmasterdata.dto.MeterologicalMonths;
import in.cropdata.cdtmasterdata.dto.MonthsDto;
import in.cropdata.cdtmasterdata.dto.PhenophaseDto;
import in.cropdata.cdtmasterdata.dto.favourableSpecificationDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriPhenophaseDurationInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.CondusiveWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalSeasonInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalVarietyInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalWeatherInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.WeatherParamLabelInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.RegionalVarietyRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;

@Service
public class CropCalendarService {

    @Autowired
    private RegionalVarietyRepository regionVarietyRepository;

    @Autowired
    private FileManagerUtil fileManagerUtil;

    @SuppressWarnings("unused")
    public CropCalenderDto getCropCalendarList(int commodityId, int varietyId, int stateCode, int seasonId) {

	try {

	    List<RegionalVarietyInfDto> dtoList = regionVarietyRepository.getSowingAndHarvestStartList(commodityId, varietyId, stateCode);

	    if (dtoList != null && dtoList.size() > 0) {

		CropCalenderDto cropCalenderDto = new CropCalenderDto();

		RegionalVarietyInfDto dto = dtoList.get(0);

		if (dto != null) {

		    int startSowingWeek = dto.getSowingWeekStart();
		    int startHarvestingWeek = dto.getHarvestWeekStart();
		    // String regionName = dto.getRegion();

		    GeneralDto generalDto = new GeneralDto();
		    generalDto.setStartWeek(dto.getSowingWeekStart());
		    generalDto.setEndWeek(dto.getHarvestWeekStart());

		    List<Integer> weekList = new ArrayList<>();
		    List<RegionalWeatherInfDto> list = new ArrayList<>();

		    if (startSowingWeek > startHarvestingWeek) {
			int j = 52;
			for (int i = startSowingWeek; i <= j; i++) {

			    weekList.add(i);

			    if (i == 52) {
				i = 0;
				j = startHarvestingWeek;
			    }
			}

			list = regionVarietyRepository.getRegionalWeatherList(weekList, stateCode);

			List<RegionalWeatherInfDto> list1 = new ArrayList<>();

			if (list != null && list.size() > 0 && !list.isEmpty()) {

			    list = list.stream().sorted(Comparator.comparing(RegionalWeatherInfDto::getWeekNumber)).collect(Collectors.toList());

			    for (RegionalWeatherInfDto d : list) {

				if (d.getWeekNumber() >= startSowingWeek && d.getWeekNumber() <= 52) {

				    list1.add(d);

				} else if (d.getWeekNumber() <= startHarvestingWeek) {
				    list1.add(d);
				}
			    }
			}

			list = list1;

		    } else {

			for (int i = startSowingWeek; i <= startHarvestingWeek; i++) {
			    weekList.add(i);
			}

			list = regionVarietyRepository.getRegionalWeatherList(weekList, stateCode);

			if (list != null && list.size() > 0 && !list.isEmpty()) {

			    list = list.stream().sorted(Comparator.comparing(RegionalWeatherInfDto::getWeekNumber)).collect(Collectors.toList());
			}
		    }

		    // Meteorological Months Section Start

		    List<MonthsDto> listForMonth = new ArrayList<>();

		    Map<Integer, String> monthsMap = MeterologicalMonths.getMonthsFromWeek();

		    for (Integer i : weekList) {

			if (monthsMap.containsKey(i)) {

			    MonthsDto mDto = new MonthsDto();

			    mDto.setMonthName(monthsMap.get(i));
			    mDto.setWeekNumber(i);

			    listForMonth.add(mDto);

			}
		    }

		    LinkedHashMap<String, List<Integer>> map = new LinkedHashMap<>();
		    List<Integer> weekListForMonth = null;
		    String lastValue = null;

		    for (MonthsDto mDto : listForMonth) {

			if (map.containsKey(mDto.getMonthName())) {

			    weekListForMonth.add(mDto.getWeekNumber());

			} else {
			    weekListForMonth = new ArrayList<>();

			    weekListForMonth.add(mDto.getWeekNumber());

			    if (lastValue == null || !lastValue.equals(mDto.getMonthName())) {

				map.put(mDto.getMonthName(), weekListForMonth);

			    }
			}

			lastValue = mDto.getMonthName();
		    }

		    generalDto.setMonths(map);

		    // Meteorological Months Section End

		    // Conducive Weather Section Start

		    List<CondusiveWeatherInfDto> condusiveWeatherList = regionVarietyRepository.getCondusiveWeatherList(commodityId, varietyId, stateCode, seasonId);

		    LinkedList<CondusiveWeatherDto> ConWeatherList = new LinkedList<>();
		    Set<String> primaryWeatherParamSet = new LinkedHashSet<>();
		    Set<String> secondaryWeatherParamSet = new LinkedHashSet<>();
		    CondusiveWeatherDto condDto = null;

		    for (CondusiveWeatherInfDto cDto : condusiveWeatherList) {

			if (cDto != null) {

			    if (cDto.getPrimaryWeatherParamName() != null || cDto.getSecondaryWeatherParamName() != null) {

				List<String> weatherPParamNameList = new LinkedList<>();
				List<String> weatherSParamNameList = new LinkedList<>();
				List<String> weatherFParamNameList = new LinkedList<>();
				List<String> AveragePParamList = new LinkedList<>();
				List<String> AverageSParamList = new LinkedList<>();
				List<String> AverageFParamList = new LinkedList<>();
				List<String> LowerPParamList = new LinkedList<>();
				List<String> LowerSParamList = new LinkedList<>();
				List<String> LowerFParamList = new LinkedList<>();
				List<String> UpperPParamList = new LinkedList<>();
				List<String> UpperSParamList = new LinkedList<>();
				List<String> UpperFParamList = new LinkedList<>();
				condDto = new CondusiveWeatherDto();

				int condusiveStartWeek = startSowingWeek + (cDto.getCondusiveStartWeek() / 7);
				int condusiveEndWeek = startSowingWeek + (cDto.getCondusiveEndWeek() / 7);

				if (condusiveStartWeek > 52) {
				    condusiveStartWeek = condusiveStartWeek - 52;
				}
				if (condusiveEndWeek > 52) {
				    condusiveEndWeek = condusiveEndWeek - 52;
				}
				if (!weekList.contains(condusiveEndWeek) && condusiveEndWeek > weekList.get(weekList.size() - 1)) {
				    condusiveEndWeek = weekList.get(weekList.size() - 1);
				}
				if (!weekList.contains(condusiveStartWeek) && condusiveStartWeek < weekList.get(0)) {
				    condusiveStartWeek = weekList.get(0);
				}

				List<Integer> conduciveSpanList = new ArrayList<Integer>();

				if (condusiveStartWeek > condusiveEndWeek) {
				    int j = 52;
				    for (int i = condusiveStartWeek; i <= j; i++) {

					conduciveSpanList.add(i);

					if (i == 52) {
					    i = 0;
					    j = condusiveEndWeek;
					}
				    }
				    condDto.setSpan(conduciveSpanList.size());
				} else {
				    condDto.setSpan(condusiveEndWeek - condusiveStartWeek + 1);
				}

				CondusiveWeatherDto condusiveDto;
				String[] primaryWeatherParamNameArr = {};
				String[] secondaryWeatherParamNameArr = {};
				String[] primarySpecificationAverageArr = {};
				String[] primarySpecificationLowerArr = {};
				String[] primarySpecificationUpperArr = {};
				String[] secondarySpecificationAverageArr = {};
				String[] secondarySpecificationLowerArr = {};
				String[] secondarySpecificationUpperArr = {};

				if (cDto.getPrimaryWeatherParamName() != null) {
				    primaryWeatherParamNameArr = cDto.getPrimaryWeatherParamName().split(",");

				    if (cDto.getPrimarySpecificationAverage() != null) {
					primarySpecificationAverageArr = cDto.getPrimarySpecificationAverage().split(",");

					AveragePParamList = Arrays.asList(primarySpecificationAverageArr);
					AverageFParamList.addAll(AveragePParamList);
				    }
				    if (cDto.getPrimarySpecificationLower() != null) {
					primarySpecificationLowerArr = cDto.getPrimarySpecificationLower().split(",");

					LowerPParamList = Arrays.asList(primarySpecificationLowerArr);
					LowerFParamList.addAll(LowerPParamList);
				    }
				    if (cDto.getPrimarySpecificationUpper() != null) {
					primarySpecificationUpperArr = cDto.getPrimarySpecificationUpper().split(",");

					UpperPParamList = Arrays.asList(primarySpecificationUpperArr);
					UpperFParamList.addAll(UpperPParamList);
				    }

				    weatherPParamNameList = Arrays.asList(primaryWeatherParamNameArr);
				    weatherFParamNameList.addAll(weatherPParamNameList);

				}
				if (cDto.getSecondaryWeatherParamName() != null) {
				    secondaryWeatherParamNameArr = cDto.getSecondaryWeatherParamName().split(",");

				    if (cDto.getSecondarySpecificationAverage() != null) {
					secondarySpecificationAverageArr = cDto.getSecondarySpecificationAverage().split(",");

					AverageSParamList = Arrays.asList(secondarySpecificationAverageArr);
					AverageFParamList.addAll(AverageSParamList);
				    }
				    if (cDto.getSecondarySpecificationLower() != null) {
					secondarySpecificationLowerArr = cDto.getSecondarySpecificationLower().split(",");

					LowerSParamList = Arrays.asList(secondarySpecificationLowerArr);
					LowerFParamList.addAll(LowerSParamList);
				    }
				    if (cDto.getSecondarySpecificationUpper() != null) {
					secondarySpecificationUpperArr = cDto.getSecondarySpecificationUpper().split(",");

					UpperSParamList = Arrays.asList(secondarySpecificationUpperArr);
					UpperFParamList.addAll(UpperSParamList);
				    }

				    weatherSParamNameList = Arrays.asList(secondaryWeatherParamNameArr);
				    weatherFParamNameList.addAll(weatherSParamNameList);
				}

				LinkedHashMap<String, CondusiveSpecificationDto> conduciveWeatherMap = new LinkedHashMap<>();

				for (int i = 0; i < weatherFParamNameList.size(); i++) {

				    CondusiveSpecificationDto conSpecDto = new CondusiveSpecificationDto();
				    conSpecDto.setSpecificationAverage(AverageFParamList.get(i));
				    conSpecDto.setSpecificationLower(LowerFParamList.get(i));
				    conSpecDto.setSpecificationUpper(UpperFParamList.get(i));

				    conduciveWeatherMap.put(weatherFParamNameList.get(i), conSpecDto);

				}
				if (!conduciveWeatherMap.isEmpty()) {

				    condDto.setBioticStress(cDto.getBioticStress());
				    condDto.setStartWeek(condusiveStartWeek);
				    condDto.setEndWeek(condusiveEndWeek);
				    condDto.setConduciveWeatherMap(conduciveWeatherMap);

				    ConWeatherList.add(condDto);
				}

				for (int i = 0; i < primaryWeatherParamNameArr.length; i++) {

				    if (primaryWeatherParamNameArr.length > 0 && primaryWeatherParamNameArr[i] != null) {
					primaryWeatherParamSet.add(primaryWeatherParamNameArr[i]);
				    }
				}
				for (int i = 0; i < secondaryWeatherParamNameArr.length; i++) {
				    if (secondaryWeatherParamNameArr.length > 0 && secondaryWeatherParamNameArr[i] != null) {
					secondaryWeatherParamSet.add(secondaryWeatherParamNameArr[i]);
				    }

				}
			    }
			}

		    }

		    // Conducive Weather Section End

		    List<AgriPhenophaseDurationInfDto> listForFavWeather = regionVarietyRepository.getPhenophaseList(commodityId, seasonId, varietyId, stateCode);

		    Set<String> favWeatherSet = new HashSet<String>();

		    for (AgriPhenophaseDurationInfDto agriDurationDto : listForFavWeather) {

			favWeatherSet.add(agriDurationDto.getFavourableWeatherParamName());

		    }

		    Set<String> mergeWeatherParameterSet = new HashSet<>();

		    mergeWeatherParameterSet.addAll(favWeatherSet);
		    mergeWeatherParameterSet.addAll(primaryWeatherParamSet);
		    mergeWeatherParameterSet.addAll(secondaryWeatherParamSet);

		    List<WeatherParamLabelInfDto> weatherParamLabelList = new LinkedList<>();

		    if (mergeWeatherParameterSet.size() > 0 && !mergeWeatherParameterSet.isEmpty()) {

			weatherParamLabelList = regionVarietyRepository.getWeatherParam(mergeWeatherParameterSet);

		    } else {
			throw new DoesNotExistException("Crop-Calender: " + " " + "weatherParamLabelList is empty in getCropCalendarList");
		    }

		    LinkedHashMap<String, String> weatherParamLabMap = new LinkedHashMap<String, String>();

		    for (WeatherParamLabelInfDto wpDto : weatherParamLabelList) {

			weatherParamLabMap.put(wpDto.getWeatherParamName(), wpDto.getWeatherParamLabel());

		    }

		    generalDto.setWeatherParams(weatherParamLabMap);

		    cropCalenderDto.setGeneralDto(generalDto);// general part ended

		    List<RegionalWeatherInfDto> regionalWeatherList = regionVarietyRepository.getRegionalWeatherModifiedList(weekListForMonth, stateCode);

		    List<AgriPhenophaseDurationInfDto> phenophaseList = regionVarietyRepository.getPhenophaseModifiedList(commodityId, seasonId, varietyId, stateCode);

		    // Filters Part

		    FiltersDto filterDto = new FiltersDto();

		    if (phenophaseList.size() > 0 && !phenophaseList.isEmpty()) {

			filterDto.setCommodity(phenophaseList.get(0).getCommodity());
			filterDto.setSeason(phenophaseList.get(0).getSeason());
			filterDto.setState(phenophaseList.get(0).getState());
			filterDto.setVariety(phenophaseList.get(0).getVariety());
		    }
		    // filterDto.setRegion(regionName);

		    cropCalenderDto.setFiltersDto(filterDto);

		    // Filters Part End

		    // Regional Weather Part Start

		    Map<Integer, LinkedHashMap<String, Float>> regionalWeatherMap = new LinkedHashMap<>();
		    RegionalWeatherDto rDto = null;
		    Set<String> mergeregionalParameterSet = new HashSet<>();

		    for (RegionalWeatherInfDto rwDto : list) {

			String[] weatherParamNameArr = {};
			String[] weatherParamValueArr = {};

			if (rwDto.getWeatherParamName() != null) {
			    weatherParamNameArr = rwDto.getWeatherParamName().split(",");
			}
			if (rwDto.getWeatherParamValue() != null) {
			    weatherParamValueArr = rwDto.getWeatherParamValue().split(",");
			}

			LinkedHashMap<String, Float> mapDto = new LinkedHashMap<>();
			for (int i = 0; i < weatherParamNameArr.length; i++) {

			    if (weatherParamNameArr[i] != null && weatherParamValueArr[i] != null) {
				mapDto.put(weatherParamNameArr[i], Float.parseFloat(weatherParamValueArr[i]));
				mergeregionalParameterSet.add(weatherParamNameArr[i]);
			    }

			}
			regionalWeatherMap.put(rwDto.getWeekNumber(), mapDto);
		    }
		    mergeWeatherParameterSet.addAll(mergeregionalParameterSet);
		    cropCalenderDto.setRegionalWeatherMap(regionalWeatherMap);

		    // Regional Weather Part End

		    // PHENOPHASE Section Start

		    PhenophaseDto phDto;

		    LinkedList<PhenophaseDto> phenophaseDtoList = new LinkedList<>();
		    int lastPhenophaseStartValue = 0;
		    int lastPhenophaseEndValue = 0;
		    String imageUrl = fileManagerUtil.fileManagerUrl;
		    for (AgriPhenophaseDurationInfDto phenophaseDto : phenophaseList) {

			int PhenophaseStartWeek = startSowingWeek + (phenophaseDto.getPhenophaseStart() / 7);
			int phenophaseEndWeek = startSowingWeek + (phenophaseDto.getPhenophaseEnd() / 7);

			if (PhenophaseStartWeek > 52) {
			    PhenophaseStartWeek = PhenophaseStartWeek - 52;
			}
			if (phenophaseEndWeek > 52) {
			    phenophaseEndWeek = phenophaseEndWeek - 52;
			}
			if (PhenophaseStartWeek == lastPhenophaseStartValue || PhenophaseStartWeek == lastPhenophaseEndValue) {
			    PhenophaseStartWeek = PhenophaseStartWeek + 1;
			}
			if (!weekList.contains(phenophaseEndWeek) && phenophaseEndWeek > weekList.get(weekList.size() - 1)) {
			    phenophaseEndWeek = weekList.get(weekList.size() - 1);
			}
			if (!weekList.contains(PhenophaseStartWeek) && PhenophaseStartWeek < weekList.get(0)) {
			    PhenophaseStartWeek = weekList.get(0);
			}

			lastPhenophaseStartValue = PhenophaseStartWeek;
			lastPhenophaseEndValue = phenophaseEndWeek;

			List<Integer> phenophaseSpanList = new ArrayList<Integer>();

			phDto = new PhenophaseDto();

			if (PhenophaseStartWeek > phenophaseEndWeek) {
			    int j = 52;
			    for (int i = PhenophaseStartWeek; i <= j; i++) {

				phenophaseSpanList.add(i);

				if (i == 52) {
				    i = 0;
				    j = phenophaseEndWeek;
				}
			    }
			    phDto.setSpan(phenophaseSpanList.size());
			} else {
			    phDto.setSpan(phenophaseEndWeek - PhenophaseStartWeek + 1);
			}

			phDto.setPhenophaseName(phenophaseDto.getPhenophase());
			phDto.setPhenophaseStart(PhenophaseStartWeek);
			phDto.setPhenophaseEnd(phenophaseEndWeek);
			phDto.setImageUrl(phenophaseDto.getImageURL());

			phenophaseDtoList.add(phDto);

		    }

		    cropCalenderDto.setPhophaseList(phenophaseDtoList);

		    // PHENOPHASE Section End

		    // Favorable Weather Section Start

		    List<AgriPhenophaseDurationInfDto> favourableWeatherList = regionVarietyRepository.getFavourableWeatherList(commodityId, seasonId, varietyId, stateCode);

		    LinkedList<FavourableWeatherDto> favWeatherList = new LinkedList<FavourableWeatherDto>();

		    int lastFavorableStartValue = 0;
		    int lastFavorableEndValue = 0;
		    for (AgriPhenophaseDurationInfDto favDto : favourableWeatherList) {

			int favoorableStartWeek = startSowingWeek + (favDto.getPhenophaseStart() / 7);
			int favourableEndWeek = startSowingWeek + (favDto.getPhenophaseEnd() / 7);

			if (favoorableStartWeek > 52) {
			    favoorableStartWeek = favoorableStartWeek - 52;
			}
			if (favourableEndWeek > 52) {
			    favourableEndWeek = favourableEndWeek - 52;
			}
			if (favoorableStartWeek == lastFavorableStartValue || favoorableStartWeek == lastFavorableEndValue) {
			    favoorableStartWeek = favoorableStartWeek + 1;
			}
			if (!weekList.contains(favourableEndWeek) && favourableEndWeek > weekList.get(weekList.size() - 1)) {
			    favourableEndWeek = weekList.get(weekList.size() - 1);
			}
			if (!weekList.contains(favoorableStartWeek) && favoorableStartWeek < weekList.get(0)) {
			    favoorableStartWeek = weekList.get(0);
			}
			lastFavorableStartValue = favoorableStartWeek;
			lastFavorableEndValue = favourableEndWeek;

			List<Integer> favourableSpanList = new ArrayList<Integer>();

			FavourableWeatherDto favwDto = new FavourableWeatherDto();

			if (favoorableStartWeek > favourableEndWeek) {
			    int j = 52;
			    for (int i = favoorableStartWeek; i <= j; i++) {

				favourableSpanList.add(i);

				if (i == 52) {
				    i = 0;
				    j = favourableEndWeek;
				}
			    }
			    favwDto.setSpan(favourableSpanList.size());
			} else {
			    favwDto.setSpan(favourableEndWeek - favoorableStartWeek + 1);
			}

			String[] favourableWeatherParamNameArr = {};
			String[] specificationAverageArr = {};
			String[] specificationLowerArr = {};
			String[] specificationUpper = {};

			if (favDto.getFavourableWeatherParamName() != null) {
			    favourableWeatherParamNameArr = favDto.getFavourableWeatherParamName().split(",");
			}
			if (favDto.getSpecificationAverage() != null) {
			    specificationAverageArr = favDto.getSpecificationAverage().split(",");
			}
			if (favDto.getSpecificationLower() != null) {
			    specificationLowerArr = favDto.getSpecificationLower().split(",");
			}
			if (favDto.getSpecificationUpper() != null) {
			    specificationUpper = favDto.getSpecificationUpper().split(",");
			}

			LinkedHashMap<String, favourableSpecificationDto> favourableWeatherMap = new LinkedHashMap<>();

			for (int i = 0; i < favourableWeatherParamNameArr.length; i++) {

			    favourableSpecificationDto facSDto = new favourableSpecificationDto();

			    if (specificationAverageArr[i] != null) {
				facSDto.setSpecificationAverage(Float.parseFloat(specificationAverageArr[i]));
			    }
			    if (specificationLowerArr[i] != null) {
				facSDto.setSpecificationLower(Float.parseFloat(specificationLowerArr[i]));
			    }
			    if (specificationUpper[i] != null) {
				facSDto.setSpecificationUpper(Float.parseFloat(specificationUpper[i]));
			    }

			    favourableWeatherMap.put(favourableWeatherParamNameArr[i], facSDto);
			}
			if (!favourableWeatherMap.isEmpty()) {

			    favwDto.setFavourableWeatherMap(favourableWeatherMap);
			    favwDto.setStartWeek(favoorableStartWeek);
			    favwDto.setEndWeek(favourableEndWeek);

			    favWeatherList.add(favwDto);
			}
		    }

		    cropCalenderDto.setFavourableWeatherList(favWeatherList);

		    // Favorable Weather Section End

		    cropCalenderDto.setConduciveWeatherList(ConWeatherList);

		}
		return cropCalenderDto;
	    } else {
//				throw new DoesNotExistException(
//						"Crop-Calender-Week : " + APIConstants.RESPONSE_NO_RECORD_FOUND + "getCropCalendarList");

		return new CropCalenderDto();
	    }

	} catch (Exception e) {
	    throw e;
	}
    }

    public List<GeoStateInfDto> getAvailableStateList() {
	try {

	    List<GeoStateInfDto> getAvailableStateList = regionVarietyRepository.getAvailableStateList();

	    return getAvailableStateList;

	} catch (Exception e) {
	    throw e;
	}
    }

    public List<GeoStateInfDto> getAvailableSeasonList(Integer stateCode, Integer commodityId, Integer varietyId) {
	try {

	    List<GeoStateInfDto> getAvailableSeasonList = regionVarietyRepository.getAvailableSeasonList(stateCode, commodityId, varietyId);

	    return getAvailableSeasonList;

	} catch (Exception e) {
	    throw e;
	}
    }

    public List<GeoStateInfDto> getCommodityByState(Integer stateCode) {
	try {

	    List<GeoStateInfDto> getCommodityByState = regionVarietyRepository.getCommodityByState(stateCode);

	    return getCommodityByState;

	} catch (Exception e) {
	    throw e;
	}
    }

    public List<GeoStateInfDto> getVarietyByStateAndCommodity(Integer stateCode, Integer commodityId) {
	try {

	    List<GeoStateInfDto> getVarietyByStateAndCommodity = regionVarietyRepository.getVarietyByStateAndCommodity(stateCode, commodityId);

	    return getVarietyByStateAndCommodity;

	} catch (Exception e) {
	    throw e;
	}
    }

}
