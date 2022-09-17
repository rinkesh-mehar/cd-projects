package in.cropdata.portal.worldData.service;

import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.worldData.model.Cities;
import in.cropdata.portal.worldData.model.Countries;
import in.cropdata.portal.worldData.model.States;
import in.cropdata.portal.worldData.repository.CitiesRepository;
import in.cropdata.portal.worldData.repository.CountriesRepository;
import in.cropdata.portal.worldData.repository.StatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.service
 * @date 13/12/21
 * @time 6:09 PM
 */
@Service
public class GeoService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoService.class);

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    public List<Countries> getCountriesList(String countriesName){

            String searchText = countriesName.concat("%");
            List<Countries> getList = countriesRepository.countriesList(searchText);
            if(getList.isEmpty()){
                throw new DoesNotExistException("Data not found");
            }else{
                return getList;
            }
    }

    public List<States> getStatesList(String countryCode){
        if (countryCode != null && !countryCode.equals("")){
            List<States> stateList =statesRepository.getAllByCountryOrderByName(countryCode);
            if (stateList.isEmpty()){
                throw new DoesNotExistException("Data not found");
            }else {
                return stateList;
            }
        } else {
            throw new DoesNotExistException("Please pass countryCode");
        }
    }

    public List<States> searchStateList(String searchString, String countryCode){

        if (countryCode != null && !countryCode.equals(""))
        {
            List<States> getSearchStateList = statesRepository.statesList(searchString.concat("%"), countryCode);
            if (getSearchStateList.isEmpty())
            {
                throw new DoesNotExistException("Data not found");
            } else
            {
                return getSearchStateList;
            }
        } else {
            throw new DoesNotExistException("Please pass countryCode");
        }
    }

    public List<Cities> getCitiesList(String regionCode, String countryCode){
        if (regionCode != null && !regionCode.equals("") && countryCode != null && !countryCode.equals("")){

            List<Cities> getCities = citiesRepository.getCities(regionCode, countryCode);
            if (getCities.isEmpty()){
                throw new DoesNotExistException("Data not found");
            }else {
                return getCities;
            }
        } else {
            throw new DoesNotExistException("Missing parameters");
        }

    }

    public List<Cities> getSearchCities(String regionCode, String countryCode, String searchText){

        if (regionCode != null && !regionCode.equals("") && countryCode != null && !countryCode.equals("")){
            List<Cities> getSearchCities = citiesRepository.getSearchCities(regionCode, countryCode, searchText.concat("%"));
            if (getSearchCities.isEmpty()){
                throw new DoesNotExistException("Data not found");
            } else {
                return getSearchCities;
            }
        } else {
            throw new DoesNotExistException("Missing parameters");
        }
    }
}
