package com.drkrishi.iqa.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.*;

import com.drkrishi.iqa.dao.repository.SpotHealthImageRepository;
import com.drkrishi.iqa.dao.repository.TaskSpotRepository;
import com.drkrishi.iqa.entity.*;
import com.drkrishi.iqa.model.*;
import com.drkrishi.iqa.utility.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.drkrishi.iqa.dao.IqaDao;
import com.drkrishi.iqa.utility.ErrorMessage;

@Service
public class IqaServiceImpl implements IqaService {

    private static final Logger LOGGER = LogManager.getLogger(IqaServiceImpl.class);

    @Autowired
    IqaDao iqaDao;

    @Autowired
    private TaskSpotRepository taskSpotRepository;

    @Autowired
    private SpotHealthImageRepository spotHealthImageRepository;

    @Autowired
    private FileUtility fileUtility;

    @Value("${kml.path}")
    private String filePath;

    ErrorMessage errorMessage = ErrorMessage.GENERIC;

    public ResponseMessage getIqaTaskList(int userId) {

        //StaticData staticData = iqaDao.getStaticData();
//		List<StaticData> staticData = iqaDao.getStaticData(List.of("qaTaskCutoff"));
//		String qaTaskCutoffStr = staticData.stream().map(s -> s.getDataValue()).findFirst()
//				.orElseThrow(() -> new RuntimeException("qaTaskCutoff not found"));
//		int qaTaskCutoff = Integer.parseInt(qaTaskCutoffStr);
//		
//		
        List<IqaTaskListModel> iqaTaskLists = new ArrayList<>();
//		List<String> assignedTaskLists = new ArrayList<>();
//		List<Object> taskCount = iqaDao.checkTaskAssigned(userId);
//		if (taskCount.size() > 0 && Long.parseLong(taskCount.get(0).toString()) > 0) {
//			LOGGER.info("Task assigned for the day.");
//		} else {
////			List<Object> qaUserCount = iqaDao.getQaUserCount();
//			List<Object[]> groupBenchmarkedImages = iqaDao.getGroupBenchmarkedImageByCommodityStateRegion();
//			if (groupBenchmarkedImages.size() > 0) {
//
////				int taskAssignPerQa = (int) Math
////						.ceil(groupBenchmarkedImages.size() / Float.parseFloat(qaUserCount.get(0).toString()));
//				
//				
//				int taskAssignPerQa = qaTaskCutoff < groupBenchmarkedImages.size() ? qaTaskCutoff : groupBenchmarkedImages.size();
//				for (int i = 0; i < taskAssignPerQa; i++) {
//					Object[] groupBenchmarkedImage = groupBenchmarkedImages.get(i);
//					List<ViewBenchmarkedImage> benchmarkedImagesForTasks = iqaDao.getGroupedBenchmarkedImageDetails(
//							groupBenchmarkedImage[0].toString(), groupBenchmarkedImage[1].toString(),
//							groupBenchmarkedImage[2].toString());
//					for (ViewBenchmarkedImage benchmarkedImagesForTask : benchmarkedImagesForTasks) {
//						assignedTaskLists.add(benchmarkedImagesForTask.getBenchmarkedImageId());
//					}
//				}
//
//				if(assignedTaskLists.size() > 0) {
//					int assignedTaskCount = iqaDao.assignTask(assignedTaskLists, userId);
//					if (assignedTaskCount > 0) {
//						LOGGER.info("done, total task assiged " + assignedTaskCount + " to userId: " + userId);
//					}
//				} else {
//					LOGGER.info("No task");
//				}
//
//			} else {
//				LOGGER.info(" no qa user or no task");
//			}
//		}
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<Object[]> assignedTasks = iqaDao.getAllIqaTask();
            if (assignedTasks.size() > 0) {
                for (Object[] assignedTask : assignedTasks) {
                    IqaTaskListModel iqaTaskListModel = new IqaTaskListModel();
                    iqaTaskListModel.setQaId(userId);
                    iqaTaskListModel.setCommodityName(assignedTask[0].toString());
                    iqaTaskListModel.setStateName(assignedTask[1].toString());
                    iqaTaskListModel.setRegionName(assignedTask[2].toString());
                    iqaTaskListModel.setCommodityId((Integer) assignedTask[3]);
                    iqaTaskListModel.setStateId((Integer) assignedTask[4]);
                    iqaTaskListModel.setRegionId((Integer) assignedTask[5]);
                    iqaTaskLists.add(iqaTaskListModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }

        responseMessage.setStatusCode("success");
        responseMessage.setData(iqaTaskLists);
        return responseMessage;
    }

    public ResponseMessage getTaskSpotIds(IqaTaskListModel iqaTaskListModel) {
        ResponseMessage responseMessage = new ResponseMessage();
        BenchmarkedImageDetailsModel benchmarkedImageDetailsModel = new BenchmarkedImageDetailsModel();

        try {
            List<String> listOfSpot;
            List<TaskSpotModel> spotModelList = new ArrayList<>();
            List<Object[]> spotCountOfTask = taskSpotRepository.getTaskSpotByByRegion(iqaTaskListModel.getStateId(), iqaTaskListModel.getRegionId(), iqaTaskListModel.getCommodityId());
            if (spotCountOfTask != null && spotCountOfTask.size() > 0) {
                for (Object[] taskDetails : spotCountOfTask) {
                    TaskSpotModel taskSpotModel = new TaskSpotModel();
                    taskSpotModel.setCount(Integer.valueOf(taskDetails[1].toString()));
                    taskSpotModel.setTaskId(taskDetails[2].toString());

                    listOfSpot = List.of(taskDetails[0].toString().split(","));

                    taskSpotModel.setSpotIds(listOfSpot);

                    spotModelList.add(taskSpotModel);

                    benchmarkedImageDetailsModel.setCommodity(iqaTaskListModel.getCommodityName());
                    benchmarkedImageDetailsModel.setState(iqaTaskListModel.getStateName());
                    benchmarkedImageDetailsModel.setRegion(iqaTaskListModel.getRegionName());
//				benchmarkedImageDetailsModel.setSpotStressModels(spotStressModelList);
                    benchmarkedImageDetailsModel.setTaskSpotModels(spotModelList);
                }
                responseMessage.setStatusCode("success");
                responseMessage.setData(benchmarkedImageDetailsModel);
                return responseMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setData(benchmarkedImageDetailsModel);
        return responseMessage;

    }

    public ResponseMessage getSpotStressDetails(String spotId, Integer commodityId) {
        ResponseMessage responseMessage = new ResponseMessage();
//		BenchmarkedImageDetailsModel benchmarkedImageDetailsModel = new BenchmarkedImageDetailsModel();
        List<StressModel> stressModelList = new ArrayList<>();
        SpotStressModel spotStressModel = new SpotStressModel();
        try {
            List<Object[]> stressDetails = iqaDao.getStressDetails(spotId);
            for (Object[] stressDetail : stressDetails) {
                StressModel stressModel = new StressModel();
                stressModel.setStressId((Integer) stressDetail[0]);
                stressModel.setStressName(stressDetail[1].toString());
//							* get stress images details based on spotId, stressId and commodityId

                List<Object[]> stressImageDetails = iqaDao.getStressImageDetails(
                        spotId, (Integer) stressDetail[0], commodityId);

                if (stressImageDetails != null && stressImageDetails.size() > 0) {
                    List<BenchmarkedImageModel> benchmarkedImageList = new ArrayList<>();
                    for (Object[] stressImages : stressImageDetails) {
                        BenchmarkedImageModel benchmarkedImageModel = new BenchmarkedImageModel();
                        benchmarkedImageModel.setId(stressImages[0].toString());
                        benchmarkedImageModel.setImageUrl(stressImages[1].toString());
                        benchmarkedImageModel.setSide(stressImages[2].toString());
                        benchmarkedImageModel.setSymptom(stressImages[4].toString());
                        benchmarkedImageList.add(benchmarkedImageModel);
                    }
                    stressModel.setStressImageModelList(benchmarkedImageList);
                } else {
                    stressModel.setStressImageModelList(null);
                }
                if (stressModel.getStressImageModelList() != null) {

                    stressModelList.add(stressModel);
                }
            }

            spotStressModel.setStressModelList(stressModelList);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setData(spotStressModel);
        return responseMessage;
    }

    /**
     * it is used to get details of stress images
     */
    public ResponseMessage getStressImageDetails(IqaTaskListModel iqaTaskListModel, boolean status) {

        ResponseMessage responseMessage = new ResponseMessage();
        BenchmarkedImageDetailsModel benchmarkedImageDetailsModel = new BenchmarkedImageDetailsModel();
        try {
            List<SpotStressModel> spotStressModelList = new ArrayList<>();
            List<SpotHealthModel> spotHealthModelList = new ArrayList<>();
            List<String> listOfSpot;
            List<TaskSpotModel> spotModelList = new ArrayList<>();
            /** find taskIds based on stateId, regionId and commodityId */
            List<Object[]> spotCountOfTask = taskSpotRepository.getTaskSpotByByRegion(iqaTaskListModel.getStateId(), iqaTaskListModel.getRegionId(), iqaTaskListModel.getCommodityId());
            if (spotCountOfTask != null && spotCountOfTask.size() > 0) {
                for (Object[] taskDetails : spotCountOfTask) {
                    TaskSpotModel taskSpotModel = new TaskSpotModel();
                    taskSpotModel.setCount(Integer.valueOf(taskDetails[1].toString()));
                    taskSpotModel.setTaskId(taskDetails[2].toString());

                    listOfSpot = List.of(taskDetails[0].toString().split(","));


//			List<String> listOfSpot = iqaDao.getSportIds(iqaTaskListModel.getStateId(), iqaTaskListModel.getRegionId(), iqaTaskListModel.getCommodityId(), true);
                    List<SpotModel> spotList = new ArrayList<>();
                    SpotModel spotModel;
                    for (String spotId : listOfSpot) {
                        spotModel = new SpotModel();
                        spotModel.setSpotId(spotId);
                        spotModel.setIsHealthImage(spotHealthImageRepository.checkHealthImages(spotId));
                        SpotStressModel spotStressModel = new SpotStressModel();
                        /** get stress list for a spotId*/
                        List<Object[]> stressDetails = iqaDao.getStressDetails(spotId);
                        SpotHealthModel spotHealthModel = new SpotHealthModel();
                        spotHealthModel.setSpotId(spotId);
                        List<StressModel> stressModelList = null;

                        //TODO : check stress
						/** check status - true the fetch stress details*/
                        if (status) {
                            if (stressDetails != null) {
                                stressModelList = new ArrayList<>();
                                spotStressModel.setSpotId(spotId);
                                for (Object[] stressDetail : stressDetails) {
                                    StressModel stressModel = new StressModel();
                                    stressModel.setStressId((Integer) stressDetail[0]);
                                    stressModel.setStressName(stressDetail[1].toString());
                                    /** get stress images details based on spotId, stressId and commodityId*/

                                    List<Object[]> stressImageDetails = iqaDao.getStressImageDetails(spotId, (Integer) stressDetail[0], iqaTaskListModel.getCommodityId());

                                    if (stressImageDetails != null && stressImageDetails.size() > 0) {
                                        List<BenchmarkedImageModel> benchmarkedImageList = new ArrayList<>();
                                        for (Object[] stressImages : stressImageDetails) {
                                            BenchmarkedImageModel benchmarkedImageModel = new BenchmarkedImageModel();
                                            benchmarkedImageModel.setId(stressImages[0].toString());
                                            benchmarkedImageModel.setImageUrl(stressImages[1].toString());
                                            benchmarkedImageModel.setSide(stressImages[2].toString());
                                            benchmarkedImageModel.setSymptom(stressImages[4].toString());
                                            benchmarkedImageList.add(benchmarkedImageModel);
                                        }
                                        stressModel.setStressImageModelList(benchmarkedImageList);
                                    } else {
                                        stressModel.setStressImageModelList(null);
                                    }
                                    if (stressModel.getStressImageModelList() != null) {

                                        stressModelList.add(stressModel);
                                    }
                                }
                            }

                            spotHealthModel.setHealthImageModelList(null);
                        } else {

                            spotHealthModel.setHealthImageModelList(this.getSpotHealthDetails(spotId));
                        }

                        if (spotStressModel.getStressModelList() != null && !(spotStressModel.getStressModelList().isEmpty())) {
                            spotStressModelList.add(spotStressModel);
                        }

                        if (spotModel.getIsHealthImage() == 1 || !(Objects.requireNonNull(stressModelList).isEmpty())) {
                            spotModel.setStressModelList(stressModelList);
                            spotList.add(spotModel);
                        }
                        spotHealthModelList.add(spotHealthModel);
                    }
                    taskSpotModel.setDisable(!(spotList.isEmpty()));
                    taskSpotModel.setSpotModels(spotList);
                    spotModelList.add(taskSpotModel);
                }
            }

            benchmarkedImageDetailsModel.setCommodity(iqaTaskListModel.getCommodityName());
            benchmarkedImageDetailsModel.setState(iqaTaskListModel.getStateName());
            benchmarkedImageDetailsModel.setRegion(iqaTaskListModel.getRegionName());
            benchmarkedImageDetailsModel.setTaskSpotModels(spotModelList);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setData(benchmarkedImageDetailsModel);
        return responseMessage;
    }

    private List<BenchmarkedImageModel> getSpotHealthDetails(String spotId) {
//		List<SpotHealthModel> spotHealthModelList = new ArrayList<>();
        List<BenchmarkedImageModel> healthImageList = new ArrayList<>();
        if (spotId != null) {
//			for (String spotId: spotIds){
				/*SpotHealthModel spotHealthMode = new SpotHealthModel();
				spotHealthMode.setSpotId(spotId);*/
            List<Object[]> healthImages = iqaDao.getHealthImageDetails(spotId);
            if (healthImages != null) {

                for (Object[] image : healthImages) {
                    BenchmarkedImageModel healthImage = new BenchmarkedImageModel();
                    healthImage.setId(image[0].toString());
                    healthImage.setSide(image[1].toString());
                    healthImage.setImageUrl(image[2].toString());
                    healthImageList.add(healthImage);
                }
//					spotHealthMode.setHealthImageModelList(healthImageList);
            }
//				spotHealthModelList.add(spotHealthMode);
        }
//		}
        return healthImageList;
    }

    /**
     * It is used to get details of health image details
     */
    @Override
    public ResponseMessage getHealthImageDetails(String spotId) {
        ResponseMessage responseMessage = new ResponseMessage();
        BenchmarkedImageDetailsModel benchmarkedImageDetailsModel = new BenchmarkedImageDetailsModel();
        SpotHealthModel spotHealthMode = new SpotHealthModel();
        try {
            /** get list of spot based on state,region and commodity*/
//			List<String> spotIds = iqaDao.getSportIds(iqaTaskListModel.getStateId(), iqaTaskListModel.getRegionId(), iqaTaskListModel.getCommodityId(), false);
//			if (spotIds != null){
//				for (String spotId: spotIds) {
//					SpotHealthModel spotHealthModels = new SpotHealthModel();
            spotHealthMode.setSpotId(spotId);
            /** get health image list based in spotId*/
            List<Object[]> healthImages = iqaDao.getHealthImageDetails(spotId);
            if (healthImages != null) {
                List<BenchmarkedImageModel> healthImageList = new ArrayList<>();
                for (Object[] image : healthImages) {
                    BenchmarkedImageModel healthImage = new BenchmarkedImageModel();
                    healthImage.setId(image[0].toString());
                    healthImage.setSide(image[1].toString());
                    healthImage.setImageUrl(image[2].toString());
                    healthImageList.add(healthImage);
                }
                spotHealthMode.setHealthImageModelList(healthImageList);
            }

//					spotHealthModelList.add(spotHealthModels);
//				}
//			}

//			benchmarkedImageDetailsModel.setCommodity(iqaTaskListModel.getCommodityName());
//			benchmarkedImageDetailsModel.setState(iqaTaskListModel.getStateName());
//			benchmarkedImageDetailsModel.setRegion(iqaTaskListModel.getRegionName());
//			benchmarkedImageDetailsModel.setSpotHealthModels(spotHealthMode);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setData(spotHealthMode);
        return responseMessage;
    }

    public ResponseMessage uploadEditedBenchmarkedImage(String benchmarkedImageName, String benchmarkedImage, String imageId, String status, Integer userId) throws Exception {

        ResponseMessage responseMessage = new ResponseMessage();

        if (!benchmarkedImage.isEmpty()) {
            String imageUrl = null;
            LOGGER.info("check this image is stress => {}", imageId.substring(0, 3));
            String[] splitedBenchmarkedImage = benchmarkedImage.split(",");

            if (benchmarkedImageName.toLowerCase().contains(".png") || benchmarkedImageName.toLowerCase().contains(".jpg") || benchmarkedImageName.toLowerCase().contains(".jpeg")) {
                byte[] decodedImage = Base64Utils.decodeFromString(splitedBenchmarkedImage[1]);
                File path = null;
                if (imageId.startsWith("137")) {
                    path = new File(filePath, benchmarkedImageName);
                } else {
                    path = new File(filePath, benchmarkedImageName);
                }
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
                    out.write(decodedImage);

                    if (imageId.startsWith("137")) {
                        imageUrl = fileUtility.uploadFileToBlob("stress", path.getAbsoluteFile());
						/*if (Files.deleteIfExists(Paths.get(path.getAbsolutePath()))){
							LOGGER.info("successfully deleted given file {}", path.getName());
						} else {
							LOGGER.info("Failed to delete the file");
						}*/
                        iqaDao.updateSpotStressSymptomImages(imageUrl, imageId, status);
                    } else {
                        imageUrl = fileUtility.uploadFileToBlob("health", path.getAbsoluteFile());
                        iqaDao.updateSpotHealthImages(imageUrl, imageId, status);
                    }

					/*List<Task> oldTasks = iqaDao.getTask(imageId.trim());
					boolean isVerified = false;
					if(oldTasks.size() > 0) {
						Task oldTask = oldTasks.get(0);
						TaskHistory taskHistory =  makeTaskHistory(oldTask, userId);
						taskHistory.setAssigneeId(userId);
						oldTask.setAssigneeId(userId);
						SubTask existSubTask = iqaDao.getSubTask(oldTask.getId());
						if (existSubTask != null){
							if ((existSubTask.getBankIsVerified() == 1) && (existSubTask.getKmlIsVerified() == 1) &&
									(existSubTask.getKycIsVerified()==1)){
								isVerified = true;
							}
							iqaDao.uploadEditedBenchmarkedImage(benchmarkedImageName, imageId, comment, oldTask, taskHistory, existSubTask,isVerified);
						}
					}*/
                } catch (IOException e) {
                    e.printStackTrace();
                    responseMessage.setStatusCode("error");
                    responseMessage.setMessage("image upload failed");
                    return responseMessage;
                } finally {
                    try {
                        Files.deleteIfExists(Paths.get(path.getAbsolutePath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        responseMessage.setStatusCode("error");
                        responseMessage.setMessage("image upload failed");
                        return responseMessage;
                    }
                }
            } else {
                LOGGER.warn("image not found");
                responseMessage.setStatusCode("error");
                responseMessage.setMessage("image not found");
                return responseMessage;
            }

        } else {
            LOGGER.warn("File format not supported.");
            responseMessage.setStatusCode("error");
            responseMessage.setMessage("File format not supported");
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setMessage("Image has been approved");
        return responseMessage;
    }

    public ResponseMessage imageRejection(BenchmarkedImageRejectionModel imageRejectionModel) {

        ResponseMessage responseMessage = new ResponseMessage();
        try {
            LOGGER.info("check this image is  => {}", imageRejectionModel.getBenchmarkedImageId().substring(0, 3));
            if (imageRejectionModel.getBenchmarkedImageId().startsWith("137")) {
                iqaDao.updateSpotStressSymptomImages(null, imageRejectionModel.getBenchmarkedImageId(), imageRejectionModel.getStatus());
            } else {
                iqaDao.updateSpotHealthImages(null, imageRejectionModel.getBenchmarkedImageId(), imageRejectionModel.getStatus());
            }
			/*List<Task> oldTasks = iqaDao.getTask(benchmarkedImageRejectionModel.getBenchmarkedImageId().trim());
			if (oldTasks.size() > 0) {
				Task oldTask = oldTasks.get(0);
				TaskHistory taskHistory = makeTaskHistory(oldTask, benchmarkedImageRejectionModel.getUserId());
				oldTask.setAssigneeId(benchmarkedImageRejectionModel.getUserId());
				taskHistory.setAssigneeId(benchmarkedImageRejectionModel.getUserId());
				iqaDao.benchmarkedImageRejection(benchmarkedImageRejectionModel, oldTask, taskHistory);
			}*/
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }

        responseMessage.setStatusCode("success");
        responseMessage.setMessage("Image has been rejected");
        return responseMessage;
    }

    private TaskHistory makeTaskHistory(Task oldTask, int userId) {

        String generatedId = generateKey(userId, "TASK_HISTORY");
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(generatedId);
        taskHistory.setTaskId(oldTask.getId());
        taskHistory.setTaskDate(oldTask.getTaskDate());
        taskHistory.setStartTime(oldTask.getTaskTime());
        taskHistory.setEndTime(new Time(System.currentTimeMillis()));
        taskHistory.setTaskTypeId(oldTask.getTaskTypeId());
        taskHistory.setAssigneeId(oldTask.getAssigneeId());
        taskHistory.setStatus(oldTask.getStatus());
        taskHistory.setEntityTypeId(oldTask.getEntityTypeId());
        taskHistory.setEntityId(oldTask.getEntityId());
        return taskHistory;
    }

    private String generateKey(int userId, String entityName) {

        FileReader reader = null;
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

        for (int i = 0; i < prefixZero; i++) {
            sb.append("0");
        }
        sb.append(id);
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }

    public ResponseMessage completedTask(String taskId, Integer userId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<Task> oldTasks = iqaDao.getTask(taskId);
            boolean isVerified = false;
            if (oldTasks.size() > 0) {
                Task oldTask = oldTasks.get(0);
                TaskHistory taskHistory = makeTaskHistory(oldTask, userId);
                taskHistory.setAssigneeId(userId);
//                oldTask.setAssigneeId(userId);
                List<SubTask> existSubTask = iqaDao.getSubTask(oldTask.getId());
                if (existSubTask.get(0) != null) {
                    if ((existSubTask.get(0).isBankIsVerified() ==1) && (existSubTask.get(0).isKmlIsVerified()== 1) &&
                            (existSubTask.get(0).isKycIsVerified()==1)) {
                        isVerified = true;
                    }
//				TODO: Temporally commented
                    iqaDao.uploadEditedBenchmarkedImage("task", oldTask, taskHistory, existSubTask.get(0), isVerified);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatusCode("error");
            responseMessage.setMessage(errorMessage.getMessage());
            return responseMessage;
        }
        responseMessage.setStatusCode("success");
        responseMessage.setMessage("Task has been Completed");
        return responseMessage;
    }
}
