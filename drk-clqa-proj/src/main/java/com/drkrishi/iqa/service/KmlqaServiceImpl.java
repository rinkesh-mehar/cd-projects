package com.drkrishi.iqa.service;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.drkrishi.iqa.dao.IqaDao;
import com.drkrishi.iqa.entity.*;
import com.drkrishi.iqa.utility.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.drkrishi.iqa.dao.KmlqaDao;
import com.drkrishi.iqa.model.Coordinate;
import com.drkrishi.iqa.model.KmlDetailsModel;
import com.drkrishi.iqa.model.KmlqaTaskListModel;
import com.drkrishi.iqa.model.ResponseMessage;
import com.drkrishi.iqa.utility.ErrorMessage;

import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;

@Service
public class KmlqaServiceImpl implements KmlqaService {

	private static final Logger LOGGER = LogManager.getLogger(KmlqaServiceImpl.class);

	@Autowired
	KmlqaDao kmlqaDao;

	@Autowired
	FileManagerUtil fileManager;

	@Value("${kml.path}")
	private String kmlPath;

	@Autowired
	private FileUtility fileUtility;

	@Autowired
	private IqaDao iqaDao;

	String path = "";
	String tempPath = "";
	String basePathForDoc = "";

	ErrorMessage errorMessage = ErrorMessage.GENERIC;
//	String path = "/home/rakesh/Desktop";

	@Override
	public ResponseMessage getKmlqaList(int userId) {
//		kmlqaDao.getKmlqaList(userId);

//		int qaTaskCutoff = Integer.parseInt(kmlqaDao.getStaticDataByKey("qaTaskCutoff").getDataValue());
//		List<StaticData> staticData = kmlqaDao.getStaticDataByKey(List.of("qaTaskCutoff"));
//
//		Optional<String> qaTask = staticData.stream().filter(s -> s.getDataKey().equals("qaTaskCutoff"))
//				.map(s -> s.getDataValue()).findFirst();
//		int qaTaskCutoff = Integer.parseInt(qaTask.orElse("").toString());

		List<KmlqaTaskListModel> kmlqaTaskListModels = new ArrayList<>();

//		List<Object> checkTaskAssignedCounts = kmlqaDao.checkTaskAssigned(userId);
//		if (checkTaskAssignedCounts.size() > 0 && ((long) checkTaskAssignedCounts.get(0) > 0)) {
//			LOGGER.info("Task already assigned.");
//			// Task already assigned
//		} else {
//
//			List<Object> kmlqaTaskCounts = kmlqaDao.getKmlqaTaskCount();
////			List<Object> kmlUserCounts = kmlqaDao.getKmlUserCount();
//			if (kmlqaTaskCounts.size() > 0 && Long.parseLong(kmlqaTaskCounts.get(0).toString()) > 0) {
//
////				long kmlUserCount = (long) kmlUserCounts.get(0);
//				long kmlqaTaskCount = Long.parseLong(kmlqaTaskCounts.get(0).toString());
////				int assignTaskPerKml = (int) Math.ceil(kmlqaTaskCount / kmlUserCount);
//
//				long assignTaskPerKml = qaTaskCutoff < kmlqaTaskCount ? qaTaskCutoff : kmlqaTaskCount;
//
//				List<String> assignedTask = new ArrayList<>();
//				List<ViewKmlqaTask> agriotaCustTasks = kmlqaDao.getAgriotaCust();
//
//				if (agriotaCustTasks.size() > 0) {
//					if (agriotaCustTasks.size() <= assignTaskPerKml) {
//						for (ViewKmlqaTask agriotaCustTask : agriotaCustTasks) {
//							assignedTask.add(agriotaCustTask.getTaskId());
//							assignTaskPerKml--;
//						}
//					} else {
//						for (int i = 0; i < assignTaskPerKml; i++) {
//							assignedTask.add(agriotaCustTasks.get(i).getTaskId());
//						}
//						assignTaskPerKml = 0;
//					}
//				}
//
//				if (assignTaskPerKml > 0) {
//					List<ViewKmlqaTask> drkCustTasks = kmlqaDao.getDrkCust();
//					if (drkCustTasks.size() > 0) {
//						if (drkCustTasks.size() <= assignTaskPerKml) {
//							for (ViewKmlqaTask drkCustTask : drkCustTasks) {
//								assignedTask.add(drkCustTask.getTaskId());
//								assignTaskPerKml--;
//							}
//						} else {
//							for (int i = 0; i < assignTaskPerKml; i++) {
//								assignedTask.add(drkCustTasks.get(i).getTaskId());
//							}
//							assignTaskPerKml = 0;
//						}
//					}
//				}
//
//				if (assignTaskPerKml > 0) {
//					List<ViewKmlqaTask> willingnessForCdtTasks = kmlqaDao.getWillingnessForCdt();
//					if (willingnessForCdtTasks.size() > 0) {
//						if (willingnessForCdtTasks.size() <= assignTaskPerKml) {
//							for (ViewKmlqaTask willingnessForCdtTask : willingnessForCdtTasks) {
//								assignedTask.add(willingnessForCdtTask.getTaskId());
//								assignTaskPerKml--;
//							}
//						} else {
//							for (int i = 0; i < assignTaskPerKml; i++) {
//								assignedTask.add(willingnessForCdtTasks.get(i).getTaskId());
//							}
//							assignTaskPerKml = 0;
//						}
//					}
//				}
//
//				if(assignedTask.size() > 0) {
//					int assignedTaskCount = kmlqaDao.assignTask(assignedTask, userId);
//					if (assignedTaskCount > 0) {
//						LOGGER.info("Task assigned to user.");
//					}
//				} else {
//					LOGGER.info("No task.");
//				}
//
//			} else {
//				LOGGER.info("No KML task (or) no KML user.");
//				// no KML task (or) no KML user
//			}
//		}
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ViewKmlqaTask> kmlqaAssignedTasks = kmlqaDao.getAllTask();
			for (ViewKmlqaTask kmlqaAssignedTask : kmlqaAssignedTasks) {
				KmlqaTaskListModel kmlqaTaskListModel = new KmlqaTaskListModel();
				kmlqaTaskListModel.setCommodity(kmlqaAssignedTask.getCommodityName());
				kmlqaTaskListModel.setFarmerName(kmlqaAssignedTask.getFarmerName());
				kmlqaTaskListModel.setMobileNumber(kmlqaAssignedTask.getPrimaryMobileNumber());
				kmlqaTaskListModel.setRegion(kmlqaAssignedTask.getRegionName());
				kmlqaTaskListModel.setState(kmlqaAssignedTask.getStateName());
				kmlqaTaskListModel.setTaskId(kmlqaAssignedTask.getTaskId());
				kmlqaTaskListModel.setVariety(kmlqaAssignedTask.getVarietyName());
				kmlqaTaskListModel.setVillage(kmlqaAssignedTask.getVillageName());
				kmlqaTaskListModels.add(kmlqaTaskListModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setStatusCode("error");
			responseMessage.setMessage(errorMessage.getMessage());
			return responseMessage;
		}
		responseMessage.setStatusCode("success");
		responseMessage.setData(kmlqaTaskListModels);
		return responseMessage;
	}

	public ResponseMessage uploadKmlFile(MultipartFile kmlFile, String taskId) {
		ResponseMessage responseMessage = new ResponseMessage();
		Coordinate coordinate = new Coordinate();
		KmlDetailsModel kmlDetailsModel = new KmlDetailsModel();
		List<Object> files = kmlqaDao.getFileDetails(taskId);
		String filename = "";
		String actFileName = "";
		if (files.size() > 0) {
			filename = files.get(0).toString();
			String seprateName =filename.replace("/", " ");
			String[] parts = seprateName.split(" ");
			actFileName = parts[parts.length - 1];
		}
		/*if (!(actFileName.equals(kmlFile.getOriginalFilename()))){
			LOGGER.warn("Please Upload Same File Name");
			responseMessage.setStatusCode("error");
			responseMessage.setMessage("Please Upload Same File Name");
			return responseMessage;
		}*/

		try (InputStream inputStream = kmlFile.getInputStream()) {
			if (kmlFile.getSize() < ((1024 * 1024) * 2) && kmlFile.getOriginalFilename().contains(".kml")) {
				String seprateName =filename.replace("/", " ");
				String[] parts = seprateName.split(" ");
				String folderName = parts[parts.length - 1];
				File file = new File(kmlPath + folderName);
				if (file.exists()) {
					Files.delete(Paths.get(kmlPath + folderName));
				}
				Files.copy(inputStream, Paths.get(kmlPath + folderName));
				inputStream.close();
				coordinate = parseKmlFile(coordinate, new File(kmlPath+ folderName));
				if(coordinate.getMessage() != null) {
					responseMessage.setStatusCode("error");
					responseMessage.setMessage(coordinate.getMessage());
					return responseMessage;
				}
				String cropName = "";
				Double cropArea = 0.0;
				Double correction = 0.0;
				List<ViewCropInformation> cropInformations = kmlqaDao.getCropDetails(taskId);
				if (cropInformations.size() > 0) {
					ViewCropInformation cropInformation = cropInformations.get(0);
					cropName = cropInformation.getCommodityName();
					try {
						cropArea = calulateCropArea(new File(kmlPath + "/" + folderName));
						Double oldArea = Double.parseDouble(String.format("%.3f", cropInformation.getCropArea()));

						correction = Math.abs((((cropArea - oldArea) / oldArea) * 100));

					} catch (ParserConfigurationException e) {
						responseMessage.setStatusCode("error");
						responseMessage.setMessage(errorMessage.getMessage());
						e.printStackTrace();
						return responseMessage;
					} catch (SAXException e) {
						responseMessage.setStatusCode("error");
						responseMessage.setMessage(errorMessage.getMessage());
						e.printStackTrace();
						return responseMessage;
					}
				}
				if (kmlFile != null) {
					String kmlUrl = fileUtility.uploadFileToBlob("kml", new File(kmlPath + folderName));
					if (!(kmlUrl.equals(""))) {
						kmlqaDao.updateKmlUrl(taskId, kmlUrl);
					}
				}
				Files.delete(Paths.get(kmlPath + file.getName()));
				// KmlDetailsModel kmlDetailsModel = new KmlDetailsModel();
				kmlDetailsModel.setCropName(cropName);
				kmlDetailsModel.setArea(cropArea);
				kmlDetailsModel.setCorrection(Double.parseDouble(String.format("%.2f", correction)));
				kmlDetailsModel.setFileName(kmlFile.getOriginalFilename());
				kmlDetailsModel.setCoordinates(coordinate);

				responseMessage.setStatusCode("success");
				responseMessage.setData(kmlDetailsModel);
			} else {
				LOGGER.warn("File size exceedd 2mb or file is not kml file.");
				responseMessage.setStatusCode("error");
				responseMessage.setMessage("File size exceedd 2mb or file is not kml file.");
			}

		} catch (IOException e) {
			e.printStackTrace();
			responseMessage.setStatusCode("error");
			responseMessage.setMessage("Failed to store file");
			return responseMessage;
		}
		responseMessage.setData(kmlDetailsModel);
		return responseMessage;
	}

	@Override
	public ResponseEntity downloadKmlFile(String filename) {

		File file = new File(basePathForDoc + "/" + filename);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = null;

		try {
			resource = new ByteArrayResource(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/plain"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(resource);
	}

	@Override
	public ResponseMessage fileDetails(String taskId) {
		ResponseMessage responseMessage = new ResponseMessage();
		KmlDetailsModel kmlDetailsModel = new KmlDetailsModel();
		try {
			if (!(Files.exists(Paths.get(kmlPath)))) {
				Files.createDirectories(Paths.get(kmlPath));
			}
			List<StaticData> staticData = kmlqaDao
					.getStaticDataByKey(List.of("docBasePath", "basePath", "kmlFilePath", "kmlFileTempPath"));

			Optional<String> docBasePath = staticData.stream().filter(s -> s.getDataKey().equals("docBasePath"))
					.map(s -> s.getDataValue()).findFirst();
			Optional<String> basePath = staticData.stream().filter(s -> s.getDataKey().equals("basePath"))
					.map(s -> s.getDataValue()).findFirst();
			Optional<String> kmlFilePath = staticData.stream().filter(s -> s.getDataKey().equals("kmlFilePath"))
					.map(s -> s.getDataValue()).findFirst();
			Optional<String> kmlFileTempPath = staticData.stream().filter(s -> s.getDataKey().equals("kmlFileTempPath"))
					.map(s -> s.getDataValue()).findFirst();

			path = basePath.orElse("") + kmlFilePath.orElse("");
			tempPath = docBasePath.orElse("") + kmlFileTempPath.orElse("");
			basePathForDoc = docBasePath.orElse("") + kmlFilePath.orElse("");

			String cropName = "";
			Double cropArea = 0.0;

			List<ViewCropInformation> cropInformations = kmlqaDao.getCropDetails(taskId);
			if (cropInformations.size() > 0) {
				ViewCropInformation cropInformation = cropInformations.get(0);
				cropName = cropInformation.getCommodityName();
				cropArea = Double.parseDouble(String.format("%.2f", cropInformation.getCropArea()));
			}

			List<Object> files = kmlqaDao.getFileDetails(taskId);
			String filename = "";
			Coordinate coordinate = new Coordinate();
			if (files.size() > 0) {
				filename = files.get(0).toString();
				URL website = new URL(filename);
				String seprateName =filename.replace("/", " ");
				String[] parts = seprateName.split(" ");
				String folderName = parts[parts.length - 1];
				LOGGER.info("file name is {}", folderName);
				try (InputStream in = website.openStream();
					 BufferedInputStream bis = new BufferedInputStream(in);
					 FileOutputStream fos = new FileOutputStream(kmlPath + folderName)){

					byte[] data = new byte[1024];
					int count;
					while ((count = bis.read(data, 0, 1024)) != -1) {
						fos.write(data, 0, count);
					}

					// cropArea = calulateCropArea(new File(path + "/" + filename));
//				coordinate = parseKmlFile(coordinate, new File(basePathForDoc + "/" + filename));
					coordinate = parseKmlFile(coordinate, new File(kmlPath + folderName));
//					Files.delete(Paths.get(kmlPath + folderName));
				}catch (FileNotFoundException e){
					e.getMessage();
				}
			}

			kmlDetailsModel.setCropName(cropName);
			kmlDetailsModel.setArea(cropArea);
			kmlDetailsModel.setCorrection(null);
			kmlDetailsModel.setFileName(filename);
			kmlDetailsModel.setCoordinates(coordinate);
		} catch (Exception e) {
			responseMessage.setStatusCode("error");
			responseMessage.setMessage(errorMessage.getMessage());
			return responseMessage;
		}

		responseMessage.setStatusCode("success");
		responseMessage.setMessage(null);
		responseMessage.setData(kmlDetailsModel);
		return responseMessage;
	}

	private Coordinate parseKmlFile(Coordinate coordinates, File file) {
		try {
			LOGGER.info("file path is {}", file.getAbsolutePath());
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(file.getAbsolutePath());
			document.getDocumentElement();

//			System.out.println("Root element :" + document.getElementsByTagName("coordinates").item(0).getTextContent());

			String splitCoordinates[] = document.getElementsByTagName("coordinates").item(0).getTextContent()
					.split("\\s|\\n");
			List<Double> xCoordinates = new ArrayList<>();
			List<Double> yCoordinates = new ArrayList<>();
			for (String data : splitCoordinates) {
				if (data.trim() != null && !data.trim().equals("")) {
					String splitData[] = data.split(",");
					xCoordinates.add(Double.parseDouble(splitData[0].trim()));
					yCoordinates.add(Double.parseDouble(splitData[1].trim()));
				}
			}
			if(xCoordinates.size() <=2 || yCoordinates.size() <= 2) {
				coordinates.setMessage("Require minimum 3 coordinates in file");
				return coordinates;
			}

			coordinates.setxMin(Collections.min(xCoordinates));
			coordinates.setxMax(Collections.max(xCoordinates));
			coordinates.setyMin(Collections.min(yCoordinates));
			coordinates.setyMax(Collections.max(yCoordinates));

		} catch (ParserConfigurationException e) {
			coordinates.setMessage(errorMessage.getMessage());
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			coordinates.setMessage(errorMessage.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			coordinates.setMessage("Invalid File, missing <coordinates> tag");
			return coordinates;
		}
		return coordinates;
	}

	public Double calulateCropArea(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = builder.parse(file);
		document.getDocumentElement();

		String splitCoordinates[] = document.getElementsByTagName("coordinates").item(0).getTextContent().split("\\s|\\n");
		//List<Point> coordinates = new ArrayList<>();
		PolygonArea p = new PolygonArea(Geodesic.WGS84, false);
		for (String data : splitCoordinates) {
			if (data.trim() != null && !data.trim().equals("")) {
				String splitData[] = data.split(",");
				p.AddPoint(Double.parseDouble(splitData[1].trim()),Double.parseDouble(splitData[0].trim()));

				//	Point point = new Point(Double.parseDouble(splitData[0].trim()),
				//		Double.parseDouble(splitData[1].trim()));
				//coordinates.add(point);
			}
		}

		//PointCollection polygonPoints = new PointCollection(coordinates);
		//Polygon polygon = new Polygon(polygonPoints);
		//Double area = GeometryEngine.area(polygon) / 4047; // divide by 4047 to convert into acres
		PolygonResult r = p.Compute();
		Double area= Math.abs(r.area)/4047;

		//  System.out.println(r.area);
		return Math.abs(Math.ceil(area * 1000) / 1000);

		// return 0.0f;
	}

	@Override
	public ResponseMessage submitDetails(String taskId, Double area, int userId) {
		ResponseMessage message = new ResponseMessage();
		Task task = kmlqaDao.getTaskById(taskId);
		boolean result = kmlqaDao.updateCaseCropArea(task.getEntityId(), area);
		String filename = "";
		if (result){
			completeTask(taskId, userId);
			deleteDir(new File(kmlPath));
			message.setMessage("KML details of the selected Farmer has been updated");
			message.setStatusCode("success");
		} else {
			message.setMessage("Invalid file");
			message.setStatusCode("error");
		}

		return message;
	}

	private void completeTask(String taskId, int userId) {
		boolean isVerified =false;
		Task oldTask = kmlqaDao.getTaskById(taskId);
		if (oldTask != null) {
			TaskHistory taskHistory = makeTaskHistory(oldTask, userId);
			taskHistory.setAssigneeId(userId);
//			oldTask.setAssigneeId(userId);
			List<SubTask> subTask = iqaDao.getSubTask(oldTask.getId());
			if (subTask.get(0) != null) {
				if ((subTask.get(0).isBankIsVerified() ==1) &&
						(subTask.get(0).isKycIsVerified()== 1) && (subTask.get(0).getRlmApprovalVerified() == 1) &&
				(subTask.get(0).getRltSampleVerified() == 1)) {
					isVerified = true;
				}
			}
			kmlqaDao.submitDetails(oldTask, taskHistory,subTask.get(0), isVerified);
		}
	}

	private TaskHistory makeTaskHistory(Task oldTask, int userId) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.setId(generateKey(userId, "TASK_HISTORY"));
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

		Properties properties = new Properties();
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("entity-code.properties");
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

	/** update case kml url - CDT-Ujwal - Start */

	private String updateCaseKmlUrl(String fileName) {
		FileUploadResponseDTO fileRepoDto = null;
		try {
			File file = new File(basePathForDoc, fileName);
			Path path = Paths.get(file.getAbsolutePath());
			if (file.exists()) {
				String pathKey = "case_kml";
				String contentType = "text/plain";
				byte[] content = null;
				Map<String, Object> pathKeyExist = this.fileManager.isPathKeyExist(pathKey);
				if (pathKeyExist.get("exist") != null) {
					Object exist = pathKeyExist.get("exist");
					if (exist.toString().equals("true")) {
						LOGGER.info("inside if condition result is true :: ");
						LOGGER.info("file absolutePath is : {}", file.getAbsolutePath());
						content = Files.readAllBytes(path);
						MultipartFile _kmlFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(),
								contentType, content);
						try {
							fileRepoDto = this.fileManager.uploadFile(pathKey, false, null, true, _kmlFile, null);
						} catch (ResourceAccessException e) {
							e.printStackTrace();
						}

					}
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileRepoDto.getPublicUrl();
	}
	/** update case kml url - CDT-Ujwal - End */

	private void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}
}
