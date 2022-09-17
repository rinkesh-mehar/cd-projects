package in.cropdata.farmers.app.controller;

import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.TaskStressDetails;
import in.cropdata.farmers.app.exception.InvalidApiKeyException;
import in.cropdata.farmers.app.exception.InvalidAppKeyException;
import in.cropdata.farmers.app.gstmTransitory.entity.CaseStressOccurrence;
import in.cropdata.farmers.app.gstmTransitory.entity.GroundTruthZL20;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.service.StressControlRecoService;
import in.cropdata.farmers.app.utils.FarmerAppUtils;
import in.cropdata.farmers.app.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 20/03/2021 - 4:40 PM
 */

@RestController
@RequestMapping(value = "/advisory")
public class StressControlRecoController {

    @Autowired
    StressControlRecoService stressControlRecoService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private FarmerAppUtils farmerAppUtils;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/type-save")
    public ResponseEntity<Object> saveAdvisoryType(HttpServletRequest request, @RequestHeader("appKey") String appKey,
                                                   @RequestParam("apiKey") String apiKey,
                                                   @RequestParam("caseID") String caseID,
                                                   @RequestParam("advisoryType") String advisoryType) {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
                    "Api Key is required to access this Resource");
        }

        if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
            if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
                return new ResponseEntity<>(stressControlRecoService.saveAdvisoryType(jwtTokenUtil.getAuthToken(request),
                        caseID, advisoryType), HttpStatus.OK);

            } else {
                throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
            }
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }

    }

    @GetMapping(value = "/get-stress-list")
    public ResponseEntity<Object> findCaseDetailsByCaseID(HttpServletRequest request, @RequestHeader("appKey") String appKey,
                                                          @RequestParam("apiKey") String apiKey, @RequestParam("caseID") String ID)
    {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
                    "Api Key is required to access this Resource");
        }

        if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
            if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
                return new ResponseEntity<>(stressControlRecoService.findCaseDetailsByCaseID(jwtTokenUtil.getAuthToken(request), ID),
                        HttpStatus.OK);

            } else {
                throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
            }
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }

    }

    @PostMapping(value = "/recommendation")
    public ResponseEntity<Object> getRecommendation(HttpServletRequest request, @RequestHeader("appKey") String appKey,
                                               @RequestParam("apiKey") String apiKey,
                                               @RequestBody TaskStressDetails taskStressDetails)
    {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
                    "Api Key is required to access this Resource");
        }

        if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
            if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
                return new ResponseEntity<>(stressControlRecoService.getRecommendation(jwtTokenUtil.getAuthToken(request), taskStressDetails),
                        HttpStatus.OK);

            } else {
                throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
            }
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }

    }
    
    @PostMapping(value = "/report-stress")
    public ResponseEntity<Object> reportStress(HttpServletRequest request, @RequestHeader("appKey") String appKey,
                                               @RequestParam("apiKey") String apiKey,
                                               @RequestBody TaskStressDetails taskStressDetails)
    {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
                    "Api Key is required to access this Resource");
        }

        if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
            if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
                return new ResponseEntity<>(stressControlRecoService.reportStress(jwtTokenUtil.getAuthToken(request), taskStressDetails),
                        HttpStatus.OK);

            } else {
                throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
            }
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }

    }

    @DeleteMapping(value = "/uncheck")
    public ResponseEntity<Object> deleteDataOfGTZL20(HttpServletRequest request, @RequestHeader("appKey") String appKey,
                                                     @RequestParam("apiKey") String apiKey,
                                                     @RequestParam("caseID") String caseID,
                                                     @RequestParam("symptomID") Integer symptomID,
                                                     @RequestParam("position") Integer position
    )
    {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
                    "Api Key is required to access this Resource");
        }

        if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
            if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
                return new ResponseEntity<>(stressControlRecoService.deleteDataOfGTZL20(request, caseID, symptomID, position),
                        HttpStatus.OK);

            } else {
                throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
            }
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }

    }

}
