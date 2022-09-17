package in.cropdata.portal.controller;

import in.cropdata.portal.worldData.model.Cities;
import in.cropdata.portal.worldData.model.Countries;
import in.cropdata.portal.worldData.model.States;
import in.cropdata.portal.worldData.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.controller
 * @date 13/12/21
 * @time 6:31 PM
 */
//@CrossOrigin("*")
@RequestMapping("/site")
@RestController
public class GeoController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoController.class);

    @Autowired
    private GeoService geoService;

    @GetMapping("/search/countries")
    public List<Countries> getCountries(@RequestParam("countries") String countries){
       return geoService.getCountriesList(countries);
    }

    @GetMapping("/states")
    public List<States> getStatesList(@RequestParam("countryCode") String countryCode){
        return geoService.getStatesList(countryCode);
    }

    @GetMapping("/search/state")
    public List<States> getSearchStateList(@RequestParam("searchState") String searchState, @RequestParam("countryCode") String countryCode){
        return geoService.searchStateList(searchState, countryCode);
    }

    @GetMapping("/cities")
    public List<Cities> getCitiesList(@RequestParam("regionCode") String regionCode, @RequestParam("countryCode") String countryCode){
        return geoService.getCitiesList(regionCode, countryCode);
    }

    @GetMapping("/search/cities")
    public List<Cities> getSearchCitiesList(@RequestParam("regionCode") String regionCode, @RequestParam("countryCode") String countryCode,
                                            @RequestParam("searchCities") String searchCities){
        return geoService.getSearchCities(regionCode, countryCode,searchCities);
    }
}
