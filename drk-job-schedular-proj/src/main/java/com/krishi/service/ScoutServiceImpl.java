package com.krishi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.constants.TaskTypeConstants;
import com.krishi.entity.*;
import com.krishi.model.*;
import com.krishi.model.Error;
import com.krishi.properties.AppProperties;
import com.krishi.repository.*;
import com.krishi.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

@Service
@Component
@Transactional
public class ScoutServiceImpl implements ScoutService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoutServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RightsRepository rightRepo;

    @Autowired
    private TaskGeneratorImpl taskGenerator;

    @Autowired
    private TaskSpotStressRepository taskSpotStressRepository;

    @Autowired
    private StressSymptomsRepository stressSymptomsRepository;

    @Autowired
    private TaskStressSpotSymptomRepository taskStressSpotSymptomRepository;

    @Autowired
    private TaskStressSpotSymptomImagesRepository taskstressSpotSymptomImagesRepository;

    @Autowired
    private TaskSpotRepository taskSpotRepository;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private FarmerRepository farmerRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private VillageRepository villageRepository;
    
    @Autowired
    private CaseCropRepository caseCropRepository;
    
    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private FileUtility fileUtility;

    @Autowired
    private StaticDataRepository staticDataRepository;

    @Override
    public Response saveScoutOnDB(String dayEndSynch, Integer userId){
        Response response = new Response();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String jsonName = String.valueOf(userId).concat("_").concat(String.valueOf(timestamp.getTime()));
            String basePath = appProperties.getRightCertificateBasePath();
            FileUtility.checkDirExists(basePath);
            objectMapper.writeValue(new File( basePath + "/" + jsonName + ".json"), dayEndSynch.replace("\n", "").replace("\\", File.separator));

           /* String jsonUrl = fileUtility.uploadFileToBlob( appProperties.getUserDirPath()+ "scout_data/", new File(basePath + "/" + jsonName + ".json"));
            fileUtility.checkAndDeleteExistingFile(new File(basePath + "/" + jsonName + ".json"));
            LOGGER.info("fiel url is --> {}", jsonUrl);*/
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SyncDataModel  syncDataModel =	objectMapper.readValue(dayEndSynch, SyncDataModel.class);

            this.saveDataModel(syncDataModel);
        } catch(Exception e) {
            e.printStackTrace();
            Error error = new Error();
            error.setErrorMesg("An Error Occurred, Please Try Again Later.");
            response.setError(error);
            return response;
        }
        response.setSuccess("Data Synced Successfully.");
        return response;
    }

    public void saveDataModel(SyncDataModel dataModel) {
        try {

            save(dataModel.getCaseCoordinates());
//            save(dataModel.getCaseCropInfo());
            save(this.updateUserDocUrl(dataModel.getCaseCropInfo()));
//            save(dataModel.getCaseKml());
            save(this.updateUserDocUrl(dataModel.getCaseKml()));
            save(dataModel.getCasefieldDetails());
            save(dataModel.getFarmCase());
//            save(this.updateUserDocUrl(dataModel.getFarmer()));
			checkexistingFarmer(dataModel.getFarmer(), dataModel.getFarmerCropInfo(), dataModel.getGovtOfficial());
            save(this.updateUserDocUrl(dataModel.getFarmerBankAccount()));
            save(dataModel.getFarmerCroping());
//            save(dataModel.getFarmerFarm());
            save(this.updateUserDocUrl(dataModel.getFarmerFarm()));
            save(dataModel.getFarmerGenInfo());
//            save(dataModel.getFarmerKyc());
            save(this.updateUserDocUrl(dataModel.getFarmerKyc()));
            save(dataModel.getFarmerLandInfo());
            save(dataModel.getFarmerLoanInfo());
            save(dataModel.getFarmerMachinery());
            save(dataModel.getFarmerPolyhouse());
            save(dataModel.getFarmerinsuranceInfo());
//            save(dataModel.getHealthPhotos());
            save(dataModel.getTaskSpot());
            save(this.updateUserDocUrl(dataModel.getHealthPhotos()));
            save(dataModel.getTaskSpotHealth());
            saveRight(this.generateRightsUrl(dataModel.getRights()));
            save(this.updateUserDocUrl(dataModel.getStressTaskDetails()));
            save(dataModel.getTask());
            save(dataModel.getTaskHistory());
            save(dataModel.getTaskTransaction());
            save(dataModel.getGovtOfficial());
            save(dataModel.getVillage());
            save(dataModel.getVip());
            save(dataModel.getPoi());
            save(dataModel.getVillageTask());
            save(dataModel.getVillageAdditionalInfo());
//            save(dataModel.getTaskAerialPhotos());
            save(this.updateUserDocUrl(dataModel.getTaskAerialPhotos()));
            save(dataModel.getTaskRecommendation());
            /** added farmer crop info task sync processor - CDT-Ujwal - Start */
//            save(dataModel.getFarmerCropInfo());
            /**save subTask details in table*/
            saveSubTask(dataModel.getTask());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*insert or update the json data in the database*/
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> void save(List<T> list) {
        if (list != null && list.size() > 0) {
            if(list.get(0).getClass().equals(PrsTask.class)) {
                for(T t: list) {
                    Object existRecored = em.find(PrsTask.class, ((PrsTask)t).getTaskId());
                    if(existRecored != null) {
                        em.merge(t);
                    } else {
                        em.persist(t);
                    }
                    em.flush();
                }
            } else {
                for(T t: list) {
                    Object existRecored = em.find(list.get(0).getClass(), ((EntityModel)t).getId());
                    if(existRecored != null) {
                        em.merge(t);
                    } else {
                        em.persist(t);
                    }
                    em.flush();
                }
            }
            em.clear();
        }

    }

    /*Update the right record in the database*/
    private void saveRight(List<Rights> list) {
        if(list!=null && list.size()>0) {
            for (Rights right : list) {
                Rights existingRight = rightRepo.findTop1ByIdOrderByVersionNumberDesc(right.getId());
                if (existingRight!=null) {

                    right.setVersionNumber(existingRight.getVersionNumber()+1);
                    rightRepo.saveAndFlush(right);


                }else {
                	CaseCropInfo foundByCaseId = this.caseCropRepository.findByCaseId(right.getCaseId());
                	Optional<Variety> foundById = this.varietyRepository.findById(foundByCaseId.getVarietyId());
                	if(foundById.get().getParentId()!=0) {
                		right.setVarietyId(foundById.get().getParentId());
                	}else {
                		right.setVarietyId(foundByCaseId.getVarietyId());
                	}
                    rightRepo.saveAndFlush(right);
                }
            }
        }
    }

    private <T> TaskStressSpotSymptoms taskSpotStress(Integer symptomId, String spotId) {
        List<TaskStressSpotSymptoms> allSavedTaskStressSpotSymptoms = new ArrayList<>();
//        if (list != null && list.size() > 0) {
//            for (T t: list){
                TaskSpotStress toSaveTaskSpotStress = new TaskSpotStress();
//                TaskStressDetails taskStressDetails = (TaskStressDetails)t;
                Optional<StressSymptoms> stressSymptoms = stressSymptomsRepository.findById(symptomId);
                if (stressSymptoms.isPresent()) {

                    Optional<TaskSpotStress> fetchedTaskSpotStress = taskSpotStressRepository.findByTaskSpotIdAndStressId(spotId, stressSymptoms.get().getStressId());

                    if (fetchedTaskSpotStress.isEmpty()) {
                        TaskStressSpotSymptoms taskStressSpotSymptoms = new TaskStressSpotSymptoms();
                        taskStressSpotSymptoms.setId(taskGenerator.generateKey("TASK_STRESS_SPOT_SYMPTOMS"));
                        taskStressSpotSymptoms.setSymptomId(symptomId);

                        TaskStressSpotSymptoms savedTaskStressSymptom = taskStressSpotSymptomRepository.save(taskStressSpotSymptoms);

                        toSaveTaskSpotStress.setId(taskGenerator.generateKey("TASK_SPOT_STRESS"));
                        toSaveTaskSpotStress.setStressId(stressSymptoms.get().getStressId());
                        toSaveTaskSpotStress.setTaskSpotId(spotId);

                        TaskSpotStress savedTaskSpotStress = taskSpotStressRepository.save(toSaveTaskSpotStress);

                        taskStressSpotSymptoms.setTaskSpotStressSymptomId(savedTaskSpotStress.getId());
                        taskStressSpotSymptoms.setId(savedTaskStressSymptom.getId());
//                        allSavedTaskStressSpotSymptoms.add(taskStressSpotSymptomRepository.save(taskStressSpotSymptoms));
                        return taskStressSpotSymptomRepository.save(taskStressSpotSymptoms);
                    } else {
                        Optional<TaskStressSpotSymptoms> fetchedTaskStressSpotSymptom = taskStressSpotSymptomRepository.findByTaskSpotStressSymptomIdAndSymptomId(fetchedTaskSpotStress.get().getId(), symptomId);
                        if (fetchedTaskStressSpotSymptom.isPresent())
                            return fetchedTaskStressSpotSymptom.get();
                    }

                }
//                savedTaskSpotStress.add(taskSpotStressRepository.saveAndFlush(toSaveTaskSpotStress));
//            }
//            saveTaskSpotStressSymptom(savedTaskSpotStress, list);
//        }
//        return allSavedTaskStressSpotSymptoms;
        return null;
    }


    /**save sub task details*/
    private void saveSubTask(List<Task> taskList){
        try {
            SubTask subTaskData;
            if (taskList != null){
                for (Task task : taskList){
                    // update taskTypeId 6 to 10 - 28/09/21
                    if (Objects.equals(task.getTaskTypeId(), 6)){

                        subTaskData = new SubTask();
                        subTaskData.setId(this.generateKey(task.getAssigneeId(), "SUBTASK"));
                        subTaskData.setTaskId(task.getId());
                        subTaskData.setBankIsVerified(1);
                        subTaskData.setImageIsVerified(0);
                        subTaskData.setKmlIsVerified(0);
                        subTaskData.setKycIsVerified(0);
                        subTaskRepository.saveAndFlush(subTaskData);
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("save subtask error ---> {}", e.getMessage());
        }
    }

    /** It is check farmer are already registered -CDT-Ujwal*/
	private void checkexistingFarmer(List<Farmer> farmerList, List<FarmerCropInfo> farmerCropInfoList, List<GovtOfficial> govtOfficialList) {
	    try {
            if (farmerList != null) {
                for (Farmer farmer : farmerList) {
                    String regionId = villageRepository.findRegionById(farmer.getVillageId());
                    String farmerId = farmer.getFarmerId();
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(appProperties.getExistFarmerApi())
                            .queryParam("primaryMobileNumber", farmer.getPrimaryMobNumber())
                            .queryParam("regionId", regionId)
                            .queryParam("apiKey",appProperties.getCdtApiKey());

//                    ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
//                            String.class);
                    ObjectMapper mapper =new ObjectMapper();
                    Response response = null;
//                    Farmer existFarmer = farmerRepo.findByPrimaryMobile(farmer.getPrimaryMobNumber());

                    if (response != null && response.getData() != null && !response.getData().equals("")) {
                        if (!response.getData().equals(farmerId)){
                        farmer.setFarmerId(response.getData());

                        if (farmerCropInfoList != null) {

                            for (FarmerCropInfo farmerCropInfo : farmerCropInfoList) {

                                if (farmerCropInfo.getFarmerId().equals(farmerId)) {

                                    farmerCropInfo.setFarmerId(response.getData());
                                    farmerCropInfo.setVillageId(farmer.getVillageId());
                                    farmerCropInfo.setAczId(villageRepository.getVillage(farmer.getVillageId()));
                                }
                            }

                        }
                    }
                    } else {
                        for (FarmerCropInfo farmerCropInfo : farmerCropInfoList) {

                            if (farmerCropInfo.getFarmerId().equals(farmerId)) {

                                farmerCropInfo.setVillageId(farmer.getVillageId());

                                farmerCropInfo.setAczId(villageRepository.getVillage(farmer.getVillageId()));
                            }
                        }
                    }
                }

                save(this.updateUserDocUrl(farmerList));
                save(farmerCropInfoList);
            }
        } catch (Exception e){
            e.printStackTrace();
		    LOGGER.error(e.getMessage());
        }
	}

    /**
     * It is use to generate ABS url
     */
    public <T> List<Object> updateUserDocUrl(List<T> list){
        List<Object> list1 = new ArrayList<>();
        if (list != null && list.size() > 0){
            if(list.get(0).getClass().equals(Farmer.class)){
                for (T t: list) {
                    Farmer farmer = (Farmer) t;
                    if (farmer.getLandOwnershipImages() != null && !farmer.getLandOwnershipImages().equalsIgnoreCase("")){
                        if (farmer.getLandOwnershipImages().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] farmerLandDocs = farmer.getLandOwnershipImages().split(",");
                            for (String farmerLandDoc : farmerLandDocs){
                                joiner.add(appProperties.getMediaUrl() +"farmer_land_docs/"
                                        + farmerLandDoc.trim());
                            }
                            farmer.setLandOwnershipImages(joiner.toString());
                        }
                        else {
                            farmer.setLandOwnershipImages(appProperties.getMediaUrl() + "farmer_land_docs/"
                                    + farmer.getLandOwnershipImages().trim());
                        }
                      /*  farmer.setLandOwnershipImages(appProperties.getMediaUrl() +"farmer_land_docs/"
                        + farmer.getLandOwnershipImages());*/
                    }
                    list1.add(farmer);
                }
            } else if (list.get(0).getClass().equals(CaseCropInfo.class)){
                for (T t: list){
                    CaseCropInfo caseCropInfo = (CaseCropInfo)t;
                    // update seller given qty divided by 10 -- 11/10/21
                    if (caseCropInfo.getSellerGivenQtyTon() != null)
                    {
                        caseCropInfo.setSellerGivenQtyTon(caseCropInfo.getSellerGivenQtyTon() / 10);
                    }
                    if (caseCropInfo.getWarehouseReceiptPhoto() != null && !caseCropInfo.getWarehouseReceiptPhoto().equalsIgnoreCase("")){
                        if (caseCropInfo.getWarehouseReceiptPhoto().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] warehousePhotos = caseCropInfo.getWarehouseReceiptPhoto().split(",");
                            for (String warehousePhoto : warehousePhotos){
                                joiner.add(appProperties.getMediaUrl() +"warehouse_photos/"
                                        + warehousePhoto.trim());
                            }
                            caseCropInfo.setWarehouseReceiptPhoto(joiner.toString());
                        }
                        else {
                            caseCropInfo.setWarehouseReceiptPhoto(appProperties.getMediaUrl() + "warehouse_photos/"
                                    + caseCropInfo.getWarehouseReceiptPhoto().trim());
                        }
                    }
                    list1.add(caseCropInfo);
                }
            } else if (list.get(0).getClass().equals(CaseKml.class)){
                for (T t : list){
                    CaseKml caseKml = (CaseKml) t;
                    if (caseKml.getKmlFilePath() != null && !caseKml.getKmlFilePath().equalsIgnoreCase("")){
                        caseKml.setKmlFilePath(appProperties.getMediaUrl() +"kml/"
                        + caseKml.getKmlFilePath().trim());

                    }
                    list1.add(caseKml);
                }
            } else if (list.get(0).getClass().equals(FarmerFarm.class)){
                for (T t : list){
                    FarmerFarm farmerFarm = (FarmerFarm) t;
                    if (farmerFarm.getLeasedLandDocuments() != null && !farmerFarm.getLeasedLandDocuments().equalsIgnoreCase("")){
                        if (farmerFarm.getLeasedLandDocuments().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] LeasedLandDocuments = farmerFarm.getLeasedLandDocuments().split(",");
                            for (String documents : LeasedLandDocuments){
                                joiner.add(appProperties.getMediaUrl() +"farmer_docs/"
                                        + documents.trim());
                            }
                            farmerFarm.setLeasedLandDocuments(joiner.toString());
                        } else {

                        farmerFarm.setLeasedLandDocuments(appProperties.getMediaUrl() +"farmer_docs/"
                                + farmerFarm.getLeasedLandDocuments().trim());
                        }

                    } if (farmerFarm.getOwnLandDocuments() != null && !farmerFarm.getOwnLandDocuments().equalsIgnoreCase("")){
                        if (farmerFarm.getOwnLandDocuments().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] ownLandDocuments = farmerFarm.getOwnLandDocuments().split(",");
                            for (String documents : ownLandDocuments){
                                joiner.add(appProperties.getMediaUrl() +"farmer_docs/"
                                        + documents.trim());
                            }
                            farmerFarm.setOwnLandDocuments(joiner.toString());
                        } else {
                            farmerFarm.setOwnLandDocuments(appProperties.getMediaUrl() + "farmer_docs/"
                                    + farmerFarm.getOwnLandDocuments().trim());
                        }
                    }
                    list1.add(farmerFarm);
                }
            } else if (list.get(0).getClass().equals(FarmerKyc.class)){
                for (T t : list){
                    FarmerKyc farmerKyc = (FarmerKyc) t;
                    if (farmerKyc.getFarmerPhoto() != null && !farmerKyc.getFarmerPhoto().equalsIgnoreCase("")){
                        if (farmerKyc.getFarmerPhoto().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] farmerPhotos = farmerKyc.getFarmerPhoto().split(",");
                            for (String photo: farmerPhotos) {
                                joiner.add(appProperties.getMediaUrl() +"farmer_kyc/" + photo.trim());
                            }
                            farmerKyc.setFarmerPhoto(joiner.toString());
                        } else {
                            farmerKyc.setFarmerPhoto(appProperties.getMediaUrl() + "farmer_kyc/"
                                    + farmerKyc.getFarmerPhoto().trim());
                        }


                    } if (farmerKyc.getDocPhoto() != null && !farmerKyc.getDocPhoto().equalsIgnoreCase("")){
                        if (farmerKyc.getDocPhoto().contains(",")){
                            StringJoiner joiner = new StringJoiner(", ");
                            String[] docPhotos = farmerKyc.getDocPhoto().split(",");
                            for (String photo: docPhotos) {
                                joiner.add(appProperties.getMediaUrl() +"farmer_kyc/" + photo.trim());
                            }
                            farmerKyc.setDocPhoto(joiner.toString());
                        } else {
                            farmerKyc.setDocPhoto(appProperties.getMediaUrl() + "farmer_kyc/"
                                    + farmerKyc.getDocPhoto().trim());
                        }
                    }
                    list1.add(farmerKyc);
                }
            } else if (list.get(0).getClass().equals(TaskHealthPhoto.class)){
                for (T t: list){
                    TaskHealthPhoto taskHealthPhoto = (TaskHealthPhoto)t;
                    TaskSpotHealthImage taskSpotHealthImage = new TaskSpotHealthImage();
                    taskSpotHealthImage.setTaskSpotId(taskHealthPhoto.getSpotId());

                    if (taskHealthPhoto.getFrontPhoto() != null && !taskHealthPhoto.getFrontPhoto().equalsIgnoreCase("")) {
                        taskSpotHealthImage.setId(taskGenerator.generateKey("TASK_SPOT_HEALTH_IMAGES"));
                        taskSpotHealthImage.setSide("F");
                        taskSpotHealthImage.setImageUrl(appProperties.getMediaUrl() +"health/"
                                + taskHealthPhoto.getFrontPhoto().strip());
                        list1.add(taskSpotHealthImage);

                    } if (taskHealthPhoto.getLeftPhoto() != null && !taskHealthPhoto.getLeftPhoto().equalsIgnoreCase("")) {
                        try {
                            TaskSpotHealthImage taskSpotHealthImage1 = taskSpotHealthImage.clone();
                            taskSpotHealthImage1.setId(taskGenerator.generateKey("TASK_SPOT_HEALTH_IMAGES"));
                            taskSpotHealthImage1.setSide("L");
                            taskSpotHealthImage1.setImageUrl(appProperties.getMediaUrl() + "health/" +
                                    taskHealthPhoto.getLeftPhoto().strip());
                            list1.add(taskSpotHealthImage1);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    } if (taskHealthPhoto.getRightPhoto() != null && !taskHealthPhoto.getRightPhoto().equalsIgnoreCase("")) {
                        try {
                            TaskSpotHealthImage taskSpotHealthImage1 = taskSpotHealthImage.clone();
                            taskSpotHealthImage1.setId(taskGenerator.generateKey("TASK_SPOT_HEALTH_IMAGES"));
                            taskSpotHealthImage1.setSide("R");
                            taskSpotHealthImage1.setImageUrl(appProperties.getMediaUrl() + "health/"
                                    + taskHealthPhoto.getRightPhoto().strip());
                            list1.add(taskSpotHealthImage1);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            } else if (list.get(0).getClass().equals(TaskAerialPhotos.class)){
                for (T t: list){
                    TaskAerialPhotos taskAerialPhotos = (TaskAerialPhotos)t;
                    if (taskAerialPhotos.getPhotos() != null && !taskAerialPhotos.getPhotos().equalsIgnoreCase("")){
                        taskAerialPhotos.setPhotos(appProperties.getMediaUrl() +"aerial/"
                                + taskAerialPhotos.getPhotos().trim());

                    }
                    list1.add(taskAerialPhotos);
                }
            }
            else if (list.get(0).getClass().equals(TaskStressDetails.class)){
                for (T t: list){
                    TaskStressDetails taskStressPhotos = (TaskStressDetails)t;

                    TaskStressSpotSymptoms savedTaskStressSpotSymptoms = taskSpotStress(taskStressPhotos.getSymptomId(), taskStressPhotos.getSpotId());

                    Optional<TaskSpot> taskSpot = taskSpotRepository.findById(taskStressPhotos.getSpotId());

                    if (taskStressPhotos.getFrontPhoto() != null && !taskStressPhotos.getFrontPhoto().equalsIgnoreCase("")){

                        TaskStressSpotSymptomImages taskStressSpotSymptomImages = new TaskStressSpotSymptomImages();
                        taskStressSpotSymptomImages.setId(taskGenerator.generateKey("TASK_STRESS_SPOT_SYMPTOM_IMAGES"));
                        taskStressSpotSymptomImages.setIncidence(taskStressPhotos.getFrontIncident());
                        taskStressSpotSymptomImages.setSeverity(taskStressPhotos.getFrontSeverity());
                        taskSpot.ifPresent(spot -> taskStressSpotSymptomImages.setBenchmark(spot.getIsBenchmark()));

                        if (savedTaskStressSpotSymptoms != null){
                            Optional<TaskStressSpotSymptomImages> fetchedTaskStressSpotSymptomImages = taskstressSpotSymptomImagesRepository.findByTaskSpotStressSymptomIdAndSide(savedTaskStressSpotSymptoms.getId(), "F");
                            fetchedTaskStressSpotSymptomImages.ifPresent(stressSpotSymptomImages -> taskStressSpotSymptomImages.setId(stressSpotSymptomImages.getId()));
                            taskStressSpotSymptomImages.setTaskSpotStressSymptomId(savedTaskStressSpotSymptoms.getId());
                        }

                        taskStressSpotSymptomImages.setSide("F");
                        taskStressSpotSymptomImages.setImageUrl(appProperties.getMediaUrl() +"stress/"
                                + taskStressPhotos.getFrontPhoto().strip());
//                        taskstressSpotSymptomImagesRepository.save(taskStressSpotSymptomImages);
                        list1.add(taskStressSpotSymptomImages);
                    } if (taskStressPhotos.getLeftPhoto() != null && !taskStressPhotos.getLeftPhoto().equalsIgnoreCase("")){

                        TaskStressSpotSymptomImages taskStressSpotSymptomImages = new TaskStressSpotSymptomImages();
                        taskStressSpotSymptomImages.setId(taskGenerator.generateKey("TASK_STRESS_SPOT_SYMPTOM_IMAGES"));
                        taskStressSpotSymptomImages.setIncidence(taskStressPhotos.getFrontIncident());
                        taskStressSpotSymptomImages.setSeverity(taskStressPhotos.getFrontSeverity());
                        taskSpot.ifPresent(spot -> taskStressSpotSymptomImages.setBenchmark(spot.getIsBenchmark()));

                        if (savedTaskStressSpotSymptoms != null){
                            Optional<TaskStressSpotSymptomImages> fetchedTaskStressSpotSymptomImages = taskstressSpotSymptomImagesRepository.findByTaskSpotStressSymptomIdAndSide(savedTaskStressSpotSymptoms.getId(), "L");
                            fetchedTaskStressSpotSymptomImages.ifPresent(stressSpotSymptomImages -> taskStressSpotSymptomImages.setId(stressSpotSymptomImages.getId()));
                            taskStressSpotSymptomImages.setTaskSpotStressSymptomId(savedTaskStressSpotSymptoms.getId());
                        }

                        taskStressSpotSymptomImages.setSide("L");
                        taskStressSpotSymptomImages.setImageUrl(appProperties.getMediaUrl() +"stress/"
                                + taskStressPhotos.getLeftPhoto().strip());
                        list1.add(taskStressSpotSymptomImages);
                    } if (taskStressPhotos.getRightPhoto() != null && !taskStressPhotos.getRightPhoto().equalsIgnoreCase("")){

                        TaskStressSpotSymptomImages taskStressSpotSymptomImages = new TaskStressSpotSymptomImages();
                        taskStressSpotSymptomImages.setId(taskGenerator.generateKey("TASK_STRESS_SPOT_SYMPTOM_IMAGES"));
                        taskStressSpotSymptomImages.setIncidence(taskStressPhotos.getFrontIncident());
                        taskStressSpotSymptomImages.setSeverity(taskStressPhotos.getFrontSeverity());
                        taskSpot.ifPresent(spot -> taskStressSpotSymptomImages.setBenchmark(spot.getIsBenchmark()));

                        if (savedTaskStressSpotSymptoms != null){
                            Optional<TaskStressSpotSymptomImages> fetchedTaskStressSpotSymptomImages = taskstressSpotSymptomImagesRepository.findByTaskSpotStressSymptomIdAndSide(savedTaskStressSpotSymptoms.getId(), "R");
                            fetchedTaskStressSpotSymptomImages.ifPresent(stressSpotSymptomImages -> taskStressSpotSymptomImages.setId(stressSpotSymptomImages.getId()));
                            taskStressSpotSymptomImages.setTaskSpotStressSymptomId(savedTaskStressSpotSymptoms.getId());
                        }

                        taskStressSpotSymptomImages.setSide("R");
                        taskStressSpotSymptomImages.setImageUrl(appProperties.getMediaUrl() +"stress/"
                                + taskStressPhotos.getRightPhoto().strip());
                        list1.add(taskStressSpotSymptomImages);
                    }
                }
            }
            else if (list.get(0).getClass().equals(FarmerBankAccount.class)){
                for (T t: list){
                    FarmerBankAccount farmerBankAccount = (FarmerBankAccount)t;
                    if (farmerBankAccount.getPassbookImageUrl() != null && !farmerBankAccount.getPassbookImageUrl().equalsIgnoreCase("")){
                        farmerBankAccount.setPassbookImageUrl(appProperties.getMediaUrl() +"farmer_bank/"
                                + farmerBankAccount.getPassbookImageUrl().trim());
                    } if (farmerBankAccount.getCancelledChequeUrl() != null && !farmerBankAccount.getCancelledChequeUrl().equalsIgnoreCase("")){
                        farmerBankAccount.setCancelledChequeUrl(appProperties.getMediaUrl() +"farmer_bank/"
                                + farmerBankAccount.getCancelledChequeUrl().trim());
                    }
                    list1.add(farmerBankAccount);
                }
            }
        }
        
        return list1;
    }

    List<Rights> generateRightsUrl(List<Rights> rightsList){
        if (rightsList != null) {
            for (Rights rights : rightsList) {
                if (rights.getRightSignUrl() != null && !rights.getRightSignUrl().equalsIgnoreCase("")) {
                    rights.setRightSignUrl(appProperties.getMediaUrl() + "rights/"
                            + rights.getRightSignUrl().trim());

                }
            }
        }
            return rightsList;

    }
    /**
     * update farmerCropInfoId based on entity Id
     * */
    /*List<Task> updateTask(List<Task> taskList){

        if(taskList !=null) {
            Map<String, Task> filterTaskList = new HashMap<>();
            try {
                for (Task task : taskList) {
                    if (task.getFarmerCropInfoId() != null && !(task.getFarmerCropInfoId().equals("0"))) {
                        filterTaskList.put(task.getEntityId(), task);
                        task.setFarmerCropInfoId(task.getFarmerCropInfoId());
                    }
                }
                for (Task task : taskList) {
                    if (filterTaskList.containsKey(task.getEntityId())) {
                        Task filterTask = filterTaskList.get(task.getEntityId());
                        if (filterTask != null && task.getFarmerCropInfoId() != null && task.getFarmerCropInfoId().equals("0")) {
                            task.setFarmerCropInfoId(filterTask.getFarmerCropInfoId());
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return taskList;
    }*/
    private String generateKey(int userId, String entityName) {
        Properties properties = new Properties();
        try {
            InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("entity-code.properties");
            properties.load(resourceStream);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int fixLenght = Integer.parseInt(properties.getProperty("FIX_LENGHT"));
        String entityValue = properties.getProperty(entityName);
        String id = String.valueOf(userId);
        int prefixZero = fixLenght - id.length();
        StringBuffer sb = new StringBuffer(entityValue);
        for( int i=0; i<prefixZero; i++) {
            sb.append("0");
        }
        sb.append(id);
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }

}
