package in.cropdata.farmers.app.service;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.DTO.FarmerDetailsByCaseIdDTO;
import in.cropdata.farmers.app.DTO.RecommendationDetailsDTO;
import in.cropdata.farmers.app.DTO.RecommendationResponseDTO;
import in.cropdata.farmers.app.DTO.StressDetailsDTO;
import in.cropdata.farmers.app.constants.EntityConstants;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkCaseCropInfo;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.model.TaskStressDetails;
import in.cropdata.farmers.app.drk.repository.DrkCaseCropInfoRepository;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.drk.repository.TaskStressDetailsRepository;
import in.cropdata.farmers.app.drk.repository.VillageRepository;
import in.cropdata.farmers.app.gstmCaseMap.repository.CaseTileMappingRepository;
import in.cropdata.farmers.app.gstmTransitory.dto.CaseDetailsDTO;
import in.cropdata.farmers.app.gstmTransitory.entity.CaseDetailsEntity;
import in.cropdata.farmers.app.gstmTransitory.repository.CaseDetailRepository;
import in.cropdata.farmers.app.gstmTransitory.repository.CaseStressOccurrenceRepository;
import in.cropdata.farmers.app.gstmTransitory.repository.GroundTruthZL20Repository;
import in.cropdata.farmers.app.masters.dto.FieldActivityDTO;
import in.cropdata.farmers.app.masters.dto.StressControlRecommendationDTO;
import in.cropdata.farmers.app.masters.model.AgriPhenophase;
import in.cropdata.farmers.app.masters.model.ControlMeasures;
import in.cropdata.farmers.app.masters.model.StressControlRecommendation;
import in.cropdata.farmers.app.masters.model.DTO.MasterRecommendation;
import in.cropdata.farmers.app.masters.model.DTO.RecommendationDTO;
import in.cropdata.farmers.app.masters.repository.AgriPhenophaseRepository;
import in.cropdata.farmers.app.masters.repository.ControlMeasuresRepository;
import in.cropdata.farmers.app.masters.repository.FieldActivityRepository;
import in.cropdata.farmers.app.masters.repository.FlipbookSymptomRepository;
import in.cropdata.farmers.app.masters.repository.GeoVillageRepository;
import in.cropdata.farmers.app.masters.repository.StressControlRecommendationRepository;
import in.cropdata.farmers.app.repository.DAO.RecommendationListDao;
import in.cropdata.farmers.app.repository.DAO.StressListDao;
import in.cropdata.farmers.app.utils.GenerateFarmerAppID;
import in.cropdata.farmers.app.utils.GenerateKeyUtils;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 20/03/2021 - 4:43 PM
 */
@Service
public class StressControlRecoService {
    private static final Logger logger = LoggerFactory.getLogger(StressControlRecoService.class);

    @Autowired
    private CaseDetailRepository caseDetailRepository;
//    @Autowired
//    private FarmerRepository farmerRepository;
    @Autowired
    private FieldActivityRepository fieldActivityRepository;
    @Autowired
    private FlipbookSymptomRepository flipbookSymptomRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CaseStressOccurrenceRepository caseStressOccurrenceRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private StressControlRecommendationRepository stressControlRecommendationRepository;
    @Autowired
    private CaseTileMappingRepository caseTileMappingRepository;
    @Autowired
    private GroundTruthZL20Repository groundTruthZL20Repository;
    @Autowired
    private AgriPhenophaseRepository agriPhenophaseRepository;
    @Autowired
    private GenerateFarmerAppID generateFarmerAppID;
    @Autowired
    private TaskStressDetailsRepository taskStressDetailsRepository;
    @Autowired
    private VillageRepository villageRepository;
    @Autowired
    private ControlMeasuresRepository controlMeasuresRepository;
    @Autowired
    private DrkCaseCropInfoRepository drkCaseCropInfoRepository;
    @Autowired
    private DrkFarmerRepository drkFarmerRepository;
    @Autowired
    private GeoVillageRepository geoVillageRepository;
//    @Autowired
//    @Qualifier(value = "masterDataSimpleJdbcCallTemplate")
//    private SimpleJdbcCall simpleJdbcCall;
    
    @Autowired
    private StressListDao stressListDao;
    
    @Autowired
    private RecommendationListDao recommendationListDao;

    @Transactional
    public Map<String, Object> findCaseDetailsByCaseID(String authToken, String caseID) {

        Map<String, Object> responseMap = new HashMap<>();
//        Map<String, Object> outParam = new HashMap<>();
//        List<StressDetailsDTO> stressList = new ArrayList<>();
//        List<Integer> symptomIDs = new ArrayList<>();
//        String symptomIDsFromProc = null;

        try {
        	
            CaseDetailsDTO caseDetails = caseDetailRepository.findCaseDetailsByID(caseID);

            if (caseDetails == null) {
                responseMap.put("status", false);
                responseMap.put("message", "No Data Found For Case");
                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);

                return responseMap;
            }

            CaseDetailsEntity caseDetailsEntity = new CaseDetailsEntity();
            BeanUtils.copyProperties(caseDetails, caseDetailsEntity);

            logger.info("Case ID : " + caseID + "Zonal Variety : " + caseDetails.getZonalVarietyID() + " Sowing Date : " + caseDetails.getSowingDate());
            
            Integer phenophaseID = findPhenophase(caseDetails.getZonalVarietyID(),caseDetails.getSowingDate());

            if (phenophaseID == null) {
                responseMap.put("status", false);
                responseMap.put("message", "No Data Found For Phenophase");
                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);

                return responseMap;
            }

            List<FieldActivityDTO> fieldActivity = fieldActivityRepository.findFieldActivity(caseDetails.getZonalCommodityID(), phenophaseID);
         
            
            Map<String, Object> outParam = stressListDao.getStressList(caseID);
            		
            @SuppressWarnings("unchecked")
			List<StressDetailsDTO> stressList = (List<StressDetailsDTO>) outParam.get("stressListFromProc");
			
			List<StressDetailsDTO> sortededStressList = new ArrayList<>();
			if(stressList.size() > 0) {
				sortededStressList = stressList.stream().sorted(Comparator.comparing(StressControlRecoService::comparingByStressName)).collect(Collectors.toList());
			}

            caseDetailsEntity.setPhenophaseID(phenophaseID);
            Optional<AgriPhenophase> agriPhenophase = agriPhenophaseRepository.findById(phenophaseID);
            agriPhenophase.ifPresent(phenophase -> caseDetailsEntity.setPhenophaseName(phenophase.getName()));

            responseMap.put("spec", caseDetailsEntity);
            responseMap.put("general", fieldActivity != null ? fieldActivity : new ArrayList<>());
            responseMap.put("stress", sortededStressList != null ? sortededStressList : new ArrayList<>());
            responseMap.put("status", true);
            responseMap.put("message", "General Activity Delivered Successfully");

            if (caseDetails == null || fieldActivity == null || outParam.get("stressListFromProc") == null) {
//                responseMap.put("status", false);
//                responseMap.put("message", "No Data Found For Case Details or Field Activity or Flipbook Symptom");
                responseMap.put("message", "No Data Found For Case Details or Field Activity or Stress List");
//                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", false);
            responseMap.put("message", "Some error occurred, Please try again !!");
            responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
        }
        return responseMap;
    }

    public Integer findPhenophase(Integer zonalVarietyID, String sowingDate)
    {
        Integer phenophaseId = null;
        try {
//            String query = "select PhenophaseID from cdt_master_data.zonal_phenophase_duration apd \n" + 
//            		" where apd.ZonalVarietyID=" + zonalVarietyID + " and \n" + 
//            		"  datediff(curdate(), if(curdate() > STR_TO_DATE(concat(Year(curdate()), " + currentSowingWeek + " , ' Wednesday'), '%X %V %W'), \n" + 
//            				" STR_TO_DATE(concat(Year(curdate()), " + currentSowingWeek + " , ' Wednesday'), '%X %V %W'), \n" + 
//            						" STR_TO_DATE(concat(Year(curdate())-1," + currentSowingWeek + " , ' Wednesday'), '%X %V %W')))  between apd.StartDas and apd.EndDas";

        	String query = "select zp.PhenophaseID from cdt_master_data.zonal_phenophase_duration zp where zp.ZonalVarietyID = " + zonalVarietyID + " and \n" +
        	               "(DATEDIFF(curdate(),'" + sowingDate + "') between zp.StartDas and zp.EndDas)";
        	
            logger.info("query for finding phenophase is -> {}", query);

            phenophaseId = jdbcTemplate.queryForObject(query, Integer.class);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return phenophaseId;
    }

    public Map<String, Object> getRecommendation(String authToken, TaskStressDetails taskStressDetails) {

        Map<String, Object> responseMap = new HashMap<>();

        Optional<DrkFarmer> foundDrkFarmer = drkFarmerRepository.findByAuthToken(authToken);
        Optional<CaseCropInfoDTO> foundCaseCropInfo = null;
        if (foundDrkFarmer.isPresent()) {
             foundCaseCropInfo = drkCaseCropInfoRepository.findAllByCaseIDAndFarmerID(
                     taskStressDetails.getCaseID(), foundDrkFarmer.get().getId());
        }

        try {

            CaseDetailsEntity fetchedCaseDetails = caseDetailRepository.findByCaseID(taskStressDetails.getCaseID());

            if (fetchedCaseDetails == null) {
                logger.info("Case id is :- {}" , taskStressDetails.getCaseID());

                responseMap.put("status", false);
                responseMap.put("message", "Failed To Get Case Details");
                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
                return responseMap;
            }

            String tileID = caseTileMappingRepository.getTileID(taskStressDetails.getCaseID());
            logger.info("Tile id is -> {}", tileID);

            if (tileID == null) {
                responseMap.put("status", false);
                responseMap.put("message", "We Are Processing Your Valuable Data. Please Come Back After Sometime");
                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
                return responseMap;
            }

            List<Integer> SymptomIDList = new ArrayList<>();

            for (Map<String, String> symptomIDStressImage : taskStressDetails.getSymptomIDStressImage()) {
                TaskStressDetails taskStressDetail = new TaskStressDetails();
                SymptomIDList.add(Integer.parseInt(symptomIDStressImage.get("symptomID")));

                /**
                 * For finding stress ID by symptom ID
                 */
//                FlipbookSymptoms flipbookSymptoms = flipbookSymptomRepository.findAllById(Integer.parseInt(symptomIDStressImage.get("symptomID")));

                if (foundCaseCropInfo.get().getAdvisoryType().equalsIgnoreCase("Custom")) {

                    taskStressDetail.setId(generateFarmerAppID.generateKeysForApp(EntityConstants.TASK_STRESS_DETAIL));
                    taskStressDetail.setCaseID(taskStressDetails.getCaseID());
                    taskStressDetail.setSpotID(symptomIDStressImage.get("spotID"));
                    taskStressDetail.setTaskID(generateFarmerAppID.generateKeysForApp(EntityConstants.TASK_ID));
                    taskStressDetail.setSymptomID(Integer.parseInt(symptomIDStressImage.get("symptomID")));
                    taskStressDetail.setFrontPhoto(symptomIDStressImage.get("stressImage"));
                    taskStressDetail.setRegionID(geoVillageRepository.findRegionIDByVillage(fetchedCaseDetails.getVillageCode()));
                    taskStressDetail.setFrontSeverityValue(5);
                    taskStressDetailsRepository.save(taskStressDetail);
                } else if (foundCaseCropInfo.get().getAdvisoryType().equalsIgnoreCase("General")) {
                    taskStressDetail.setId(generateFarmerAppID.generateKeysForApp(EntityConstants.TASK_STRESS_DETAIL));
                    taskStressDetail.setCaseID(taskStressDetails.getCaseID());
                    taskStressDetail.setSpotID(tileID);
                    taskStressDetail.setTaskID(generateFarmerAppID.generateKeysForApp(EntityConstants.TASK_ID));
                    taskStressDetail.setSymptomID(Integer.parseInt(symptomIDStressImage.get("symptomID")));
                    taskStressDetail.setFrontSeverityValue(5);
                    taskStressDetail.setRegionID(geoVillageRepository.findRegionIDByVillage(fetchedCaseDetails.getVillageCode()));
                    taskStressDetailsRepository.save(taskStressDetail);
                } else {
                    responseMap.put("status", false);
                    responseMap.put("message", "Failed To Get Recommendation");
                    responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
                }

                logger.info("Task Stress Details Saved Successfully");
            }

            if (foundCaseCropInfo.get().getAdvisoryType().equalsIgnoreCase("Custom")) {
                responseMap.put("status", true);
                responseMap.put("message", "We Are Processing Your Customized Recommendation Please Wait Up To 24 Hours...");
            } else {
                responseMap= this.getRecommendationList(taskStressDetails.getCaseID(), SymptomIDList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", false);
            responseMap.put("message", "Failed To Get Recommendation");
            responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
        }

        return responseMap;
    }


    public Map<String, Object> getRecommendationList(String caseId, List<Integer> symptomIDs){

        logger.info("Case ID For Getting Recommendation {}", caseId);
        logger.info("Symptom IDs for Getting Recommendation {}", symptomIDs.toString());

        Map<String, Object> responseMap = new HashMap<>();
        try {


            List<ControlMeasures> controlMeasuresList = controlMeasuresRepository.findAll();
            CaseDetailsEntity caseDetailsEntity = new CaseDetailsEntity();
            MasterRecommendation masterRecommendation = null;
            List<MasterRecommendation> masterRecommendationList = new ArrayList<>();

            if (caseId != null) {
                caseDetailsEntity = caseDetailRepository.findByCaseID(caseId);
            }
            RecommendationDTO recommendationDTOS = new RecommendationDTO();
            List<StressControlRecommendationDTO> stressControlRecommendations = stressControlRecommendationRepository.listOfStressControlRecommendation(
                    caseDetailsEntity.getDistrictCode(), caseDetailsEntity.getCommodityID(), symptomIDs);
            List<String> strings = stressControlRecommendations.stream().map(StressControlRecommendationDTO::getStressControlMeasureName).distinct()
                    .collect(Collectors.toList());

            List<StressControlRecommendation> stressControlRecommendationList = null;
            for (ControlMeasures controlMeasures : controlMeasuresList) {

                if (strings.contains(controlMeasures.getName())) {
                    stressControlRecommendationList = new ArrayList<>();
                    masterRecommendation = new MasterRecommendation();

                    masterRecommendation.setControlMeasure(controlMeasures.getName());
                    StressControlRecommendation stressControlRecommendation1 = new StressControlRecommendation();
                    for (StressControlRecommendationDTO stressControlRecommendation : stressControlRecommendations) {
                        if (stressControlRecommendation.getRecommendation() != null) {
                            if (stressControlRecommendation.getStressControlMeasureName().equals(masterRecommendation.getControlMeasure())) {
//                                StressControlRecommendation stressControlRecommendation1 = new StressControlRecommendation();
                                stressControlRecommendation1.setId(stressControlRecommendation.getId());
                                stressControlRecommendation1.setRecommendation(stressControlRecommendation.getRecommendation());
                                stressControlRecommendationList.add(stressControlRecommendation1);

                            }
                        }
                    }
                    if (stressControlRecommendationList.size() > 0) {
                        List<StressControlRecommendation> filterStressControlRecommendationsList = stressControlRecommendationList.stream()
                                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(StressControlRecommendation::getRecommendation))),
                                        ArrayList::new));
                        masterRecommendation.setRecommendations(filterStressControlRecommendationsList);
                        masterRecommendationList.add(masterRecommendation);
                    }
                }
            }
            recommendationDTOS.setRecommendations(masterRecommendationList);
            logger.info("show JSON :- {}", masterRecommendationList);
            responseMap.put("recommendation", masterRecommendationList);
            responseMap.put("status", true);
            responseMap.put("message", "Recommendation Delivered successfully");

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", false);
            responseMap.put("message", "Failed To Fetch Recommendation");
            responseMap.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
        }

        return responseMap;
    }

    public Map<String, Object> deleteDataOfGTZL20(HttpServletRequest request, String caseId, Integer symptomID, Integer position) {
        Map<String, Object> responseMap = new HashMap<>();
        String farmerAuthToken = jwtTokenUtil.getAuthToken(request);

        Integer noOfDeletedRecorded = 0;

        logger.info("for delete symptom ID is {}", symptomID);
        logger.info("for delete case ID is {}", caseId);

        try {
            noOfDeletedRecorded = groundTruthZL20Repository.deleteByCaseIDAndSymptomID(caseId, symptomID, farmerAuthToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        responseMap.put("status", true);
        responseMap.put("position", position);
        responseMap.put("recordDeleted", noOfDeletedRecorded);
        responseMap.put("message", "Data With Respect to Symptom and Case ID Deleted Successfully");

        return responseMap;
    }

    public Map<String, Object> saveAdvisoryType(String authToken, String caseID, String advisoryType) {
        Map<String, Object> responseMap = new HashMap<>();

        if (drkCaseCropInfoRepository.getCountOfCaseByCaseIDAndFarmerID(
                jwtTokenUtil.getMobileFromToken(authToken), caseID) < 1) {
            responseMap.put("status", false);
            responseMap.put("message", "You Are Not Authorized To Save Advisory Type");
            responseMap.put("errorCode", ErrorConstants.UNAUTHORIZED);

            return responseMap;
        }

        Optional<DrkCaseCropInfo> foundCaseCropInfo = drkCaseCropInfoRepository.findAllByCaseID(caseID);

        if (caseID == null || advisoryType == null || foundCaseCropInfo.isEmpty()) {
            responseMap.put("status", false);
            responseMap.put("message", "Failed To Save Advisory Type");
            responseMap.put("errorCode", ErrorConstants.FAIL_TO_SAVE);

            return responseMap;
        }

        foundCaseCropInfo.get().setAdvisoryType(advisoryType);
        drkCaseCropInfoRepository.save(foundCaseCropInfo.get());

        responseMap.put("status", true);
        responseMap.put("message", "Advisory Type Saved Successfully");
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
	@Transactional
    public Map<String, Object> reportStress(String authToken, TaskStressDetails taskStressDetails) {

        Map<String, Object> responseMap = new HashMap<>();
        StringBuilder insertQuery = null;
        int insertCount = 0;
//        Map<String, Object> outParam = new HashMap<>();
//        List<StressDetailsDTO> stressList = new ArrayList<>();
//        List<RecommendationResponseDTO>  recommendationResponseDTOList = new ArrayList<>();
        List<Map<String, Object>>  recommendationResponse = new ArrayList<>();
        boolean flag = false;
        boolean flagForAlreadyExistStress = false;

        try {

        	FarmerDetailsByCaseIdDTO farmerDetailsByCaseIdDTO = stressControlRecommendationRepository.getFarmerDetailsByCaseId(taskStressDetails.getCaseID());

            if (farmerDetailsByCaseIdDTO == null) {
                logger.info("Case id is :- {}" , taskStressDetails.getCaseID());

                responseMap.put("status", false);
                responseMap.put("message", "Failed To Get Farmer Details");
                responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
                return responseMap;
            }
            
            insertQuery = new StringBuilder("insert into drkrishi_ts.task_stress_details\n" + 
            		"(id,task_id,symptom_id,left_incident,left_severity,front_incident,front_severity,right_incident,right_severity,village_id,farmer_id,gt_type_id,role_name,gt_level) values");
            
            for (Integer symptomID : taskStressDetails.getSymptomIDs()) {
            	
            	insertQuery = insertQuery.append("(" + GenerateKeyUtils.generateKey(EntityConstants.TASK_STRESS_DETAIL) + ",\n" + 
            			GenerateKeyUtils.generateKey(EntityConstants.TASK_ID) + "," + symptomID + "," + 1 + "," + 2  + "," + 1 + "," + 2  + "," + 1 + "," + 2 + "," + farmerDetailsByCaseIdDTO.getVillageId() + ",\n" + 
            			farmerDetailsByCaseIdDTO.getFarmerId() + "," + farmerDetailsByCaseIdDTO.getGtTypeId() + ",'" + farmerDetailsByCaseIdDTO.getRoleName() + "','" + farmerDetailsByCaseIdDTO.getGtLevel() + "'),");
            	
//                logger.info("Task Stress Details Saved Successfully");
            }

            final String finalQuery = String.valueOf(insertQuery.replace(insertQuery.lastIndexOf(","), insertQuery.lastIndexOf(",") + 1, ";"));
            
//            logger.info("finalQuery :- {}" , finalQuery);
            
            insertCount = jdbcTemplate.update(finalQuery);
            
//            logger.info("insertCount :- {}" , insertCount);

            if(insertCount > 0) {
            	
            	Map<String, Object> result = recommendationListDao.getRecommendationList(taskStressDetails.getCaseID());
    			
    			
    			List<RecommendationDetailsDTO> recommendationList = (List<RecommendationDetailsDTO>) result.get("recommendationListFromProc");
    			
    			logger.info("recommendationList {}", recommendationList);
    			
    			List<RecommendationDetailsDTO> sortededRecommendationList = new ArrayList<>();
    			
    			if(recommendationList.size() > 0) {
    				sortededRecommendationList = recommendationList.stream().sorted(Comparator.comparing(StressControlRecoService::comparingByControlMeasure).thenComparing(StressControlRecoService::comparingByStressNameForRecommendation)).collect(Collectors.toList());
    			}
    			
    			logger.info("sortededRecommendationList {}", sortededRecommendationList);
    			
    			for(RecommendationDetailsDTO recommendationDetailsDTO : sortededRecommendationList) {
    				
    				if(taskStressDetails.getSymptomIDs().contains(recommendationDetailsDTO.getSymptomID())) {
    				
    		        List<Map<String, Object>>  recommendationListMap = new ArrayList<>();
    		        List<Map<String, Object>>  stressListMap = new ArrayList<>();
    				LinkedHashMap<String, Object> controlMeasureWithRecommendation = new LinkedHashMap<>();
    				LinkedHashMap<String, Object> recommendation = new LinkedHashMap<>();
    				LinkedHashMap<String, Object> stress = new LinkedHashMap<>();
    				
    				if(recommendationResponse.size() == 0) {
    					controlMeasureWithRecommendation.put("controlMeasure", recommendationDetailsDTO.getControlMeasure());
    					
//    					stress.put("ID", recommendationDetailsDTO.getStressID());
    					stress.put("stressName", recommendationDetailsDTO.getStressName());
    					stressListMap.add(stress);
    					
    					recommendation.put("ID", recommendationDetailsDTO.getRecommendationID());
    					recommendation.put("recommendation", recommendationDetailsDTO.getRecommendation());
    					recommendation.put("stress", stressListMap);
    					
    					recommendationListMap.add(recommendation);
    					
    					controlMeasureWithRecommendation.put("recommendations", recommendationListMap);
    					
    					recommendationResponse.add(controlMeasureWithRecommendation);
    					
    				}else {
    					
    					for(Map<String, Object> controlMeasureWithRecom : recommendationResponse) {
    						
    						//Already exist check for control measure
    						if(controlMeasureWithRecom.containsValue(recommendationDetailsDTO.getControlMeasure())) {
    	    					
								List<Map<String, Object>>  recommendationsList = (List<Map<String, Object>>)controlMeasureWithRecom.get("recommendations");
								
								//Already exist check for recommendation
								for(Map<String, Object> recommendationForAlreadyExistCheck : recommendationsList) {
									
									if(recommendationForAlreadyExistCheck.containsValue(recommendationDetailsDTO.getRecommendation())) {
										
										List<Map<String, Object>>  stressList = (List<Map<String, Object>>)recommendationForAlreadyExistCheck.get("stress");
										
										//Already exist check for stress
										for(Map<String, Object> stressForAlreadyExistCheck : stressList) {
											
											if(stressForAlreadyExistCheck.containsValue(recommendationDetailsDTO.getStressName())) {
												flagForAlreadyExistStress = true;
												break;
											}		
											
										}
										
										if(flagForAlreadyExistStress) {
											flagForAlreadyExistStress = false;
											
										}else {
//											stress.put("ID", recommendationDetailsDTO.getStressID());
					    					stress.put("stressName", recommendationDetailsDTO.getStressName());
					    					stressList.add(stress);
										}
										
										flag = true;
										break;
									}
								}
								
								if(!flag) {
//									stress.put("ID", recommendationDetailsDTO.getStressID());
			    					stress.put("stressName", recommendationDetailsDTO.getStressName());
			    					stressListMap.add(stress);
			    					
			    					recommendation.put("ID", recommendationDetailsDTO.getRecommendationID());
			    					recommendation.put("recommendation", recommendationDetailsDTO.getRecommendation());
			    					recommendation.put("stress", stressListMap);
			    					recommendationsList.add(recommendation);
    	    					
			    					flag = true;
								}
    						}
    					}
    					
    					if(flag) {
    						
    					flag = false;
    					
    					}else {
    						controlMeasureWithRecommendation.put("controlMeasure", recommendationDetailsDTO.getControlMeasure());
    						
//    						stress.put("ID", recommendationDetailsDTO.getStressID());
	    					stress.put("stressName", recommendationDetailsDTO.getStressName());
	    					stressListMap.add(stress);
        					
        					recommendation.put("ID", recommendationDetailsDTO.getRecommendationID());
        					recommendation.put("recommendation", recommendationDetailsDTO.getRecommendation());
        					recommendation.put("stress", stressListMap);
        					
        					recommendationListMap.add(recommendation);
        					
        					controlMeasureWithRecommendation.put("recommendations", recommendationListMap);
        					
        					recommendationResponse.add(controlMeasureWithRecommendation);
    					}
    				}
    			  }
    			}
    			            	
            	responseMap.put("recommendationResponse", recommendationResponse);
            	responseMap.put("status", true);
                responseMap.put("message", "Recommendation Delivered Successfully");
            }else {
            	responseMap.put("status", false);
                responseMap.put("message", "Failed To Get Recommendation");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", false);
            responseMap.put("message", "Failed To Get Recommendation");
            responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
        }

        return responseMap;
        
    }
    
    private static String comparingByStressName(StressDetailsDTO stressDetailsDTO){
        return stressDetailsDTO.getStressName();
    }
    
    private static String comparingByControlMeasure(RecommendationDetailsDTO recommendationDetailsDTO){
        return recommendationDetailsDTO.getControlMeasure();
    }
    
    private static String comparingByStressNameForRecommendation(RecommendationDetailsDTO recommendationDetailsDTO){
        return recommendationDetailsDTO.getStressName();
    }
}
