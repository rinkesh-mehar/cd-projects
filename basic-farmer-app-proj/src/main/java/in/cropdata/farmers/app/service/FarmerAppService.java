package in.cropdata.farmers.app.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.DTO.FarmerAddressBookDTO;
import in.cropdata.farmers.app.DTO.FarmerLatLongDTO;
import in.cropdata.farmers.app.DTO.FarmerProfileDTO;
import in.cropdata.farmers.app.constants.EntityConstants;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkCaseCropInfo;
import in.cropdata.farmers.app.drk.model.DrkFarmCase;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.model.FarmerAddressBook;
import in.cropdata.farmers.app.drk.model.FarmerCaseKMLPoint;
import in.cropdata.farmers.app.drk.model.FarmerFarm;
import in.cropdata.farmers.app.drk.model.FarmerKYC;
import in.cropdata.farmers.app.drk.model.FarmerLocation;
import in.cropdata.farmers.app.drk.model.ImageStore;
import in.cropdata.farmers.app.drk.repository.DrkCaseCropInfoRepository;
import in.cropdata.farmers.app.drk.repository.DrkFarmCaseRepository;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.drk.repository.DrkRightRepository;
import in.cropdata.farmers.app.drk.repository.DrkrishiDaoRepository;
import in.cropdata.farmers.app.drk.repository.FarmerAddressBookRepository;
import in.cropdata.farmers.app.drk.repository.FarmerCaseKMLPointRepository;
import in.cropdata.farmers.app.drk.repository.FarmerFarmRepository;
import in.cropdata.farmers.app.drk.repository.FarmerKYCRepository;
import in.cropdata.farmers.app.drk.repository.FarmerLocationRepository;
import in.cropdata.farmers.app.drk.repository.ImageStoreRepository;
import in.cropdata.farmers.app.exception.CaseNotFoundException;
import in.cropdata.farmers.app.exception.DoesNotExistException;
import in.cropdata.farmers.app.gstm.repository.MarketPriceRepository;
import in.cropdata.farmers.app.gstmTransitory.entity.CaseDetailsEntity;
import in.cropdata.farmers.app.gstmTransitory.repository.CaseDetailRepository;
import in.cropdata.farmers.app.gstmTransitory.repository.PhenophaseGroupRepository;
import in.cropdata.farmers.app.masters.model.ZonalStandardQuantityChart;
import in.cropdata.farmers.app.masters.repository.MasterDaoRepository;
import in.cropdata.farmers.app.masters.repository.ZonalStandardQuantityChartRepository;
import in.cropdata.farmers.app.model.FarmCaseLocation;
import in.cropdata.farmers.app.model.FarmLocation;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.repository.FarmerAppRepository;
import in.cropdata.farmers.app.repository.DAO.FarmerAppDAO;
import in.cropdata.farmers.app.utils.FarmerAppUtils;
import in.cropdata.farmers.app.utils.GenerateFarmerAppID;
import in.cropdata.farmers.app.utils.JwtTokenUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

/**
 * @author RinkeshKM
 * @Date 30/01/21
 */

@Service
public class FarmerAppService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerAppService.class);

	@Autowired
	private FarmerKYCRepository farmerKYCRepository;

	@Autowired
	private DrkCaseCropInfoRepository caseCropRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private FileManagerUtil fileManagerUtil;
	@Autowired
	private FarmerFarmRepository farmerFarmRepository;
	@Autowired
	private ImageStoreRepository imageStoreRepository;
	@Autowired
	private MarketPriceRepository marketPriceRepository;
	@Autowired
	private FarmerCaseKMLPointRepository farmerCaseKMLPointRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private FarmerAppDAO farmerAppDAO;
	@Autowired
	private FileManagerUtil fileManager;
	@Autowired
	private CaseDetailRepository caseDetailsRepository;
	@Autowired
	private FarmerAppRepository farmerAppRepository;
	@Autowired
	private PhenophaseGroupRepository phenophaseGroupRepository;
	@Autowired
	private GenerateFarmerAppID generateFarmerAppID;
	@Autowired
	private DrkFarmCaseRepository drkFarmCaseRepository;
	@Autowired
	private DrkCaseCropInfoRepository drkCaseCropInfoRepository;
	@Autowired
	private DrkFarmerRepository drkFarmerRepository;
	@Autowired
	private DrkRightRepository drkRightRepository;
	@Autowired
	private FarmerAddressBookRepository drkFarmerAddressBookRepository;
	@Autowired
	private FarmerAppUtils farmerAppUtils;
	@Autowired
	private DrkrishiDaoRepository drkrishiDaoRepository;
	@Autowired
	private MasterDaoRepository masterDaoRepository;
	@Autowired
	private MasterDataService masterDataService;
	@Autowired
	private ZonalStandardQuantityChartRepository stdQtyChartRepository;
	@Autowired
	private FarmerLocationRepository farmerLocationRepository;
	@Autowired
	private AppProperties appProperties;

	public Map<String, Object> getKYCDocument(HttpServletRequest httpServletRequest) {

		try {
			Map<String, Object> response = new HashMap<>();
			List<FarmerKYC> fetchedFarmerKYCDoc = new ArrayList<>();
			String farmerAuthToken = jwtTokenUtil.getAuthToken(httpServletRequest);

//            Optional<Farmer> foundFarmerByAuthToken = farmerRepository.findByAuthToken(farmerAuthToken);
			Optional<DrkFarmer> foundDrkFarmer = drkFarmerRepository.findByAuthToken(farmerAuthToken);

			if (foundDrkFarmer.isPresent()) {
				fetchedFarmerKYCDoc = farmerKYCRepository.findAllByFarmerID(foundDrkFarmer.get().getId());

			} else {
				response.put("status", false);
				response.put("message", "Sorry, you are not authorized to use this resources");
				response.put("errorCode", ErrorConstants.UNAUTHORIZED);
				return response;
			}

			if (!fetchedFarmerKYCDoc.isEmpty()) {
				response.put("kycDocument", fetchedFarmerKYCDoc);
				response.put("status", true);
				response.put("message", "KYC document Delivered Successfully");

			} else {
				response.put("status", false);
				response.put("message", "KYC document not found");
				response.put("errorCode", ErrorConstants.KYC_NOT_FOUND);
			}
			return response;
		} catch (Exception e) {
			throw new DoesNotExistException("Document not present");
		}
	}

	public Map<String, Object> getCaseListing(String authToken) {
		Map<String, Object> response = new HashMap<>();
		List<Map<String, Object>> caseList;

		Optional<DrkFarmer> foundDrkFarmer = drkFarmerRepository.findByAuthToken(authToken);
		if (foundDrkFarmer.isPresent()) {
			DrkFarmer farmer = foundDrkFarmer.get();
			caseCropRepository.checkAndUpdateCaseStatus();
			try {
				caseList = caseCropRepository.getCaseListByFarmer(farmer.getId());
				if (caseList != null && caseList.size() > 0) {
					response.put("cases", caseList);
					response.put("status", true);
					response.put("message", "Case List Delivered Successfully");
				} else {
					response.put("status", false);
					response.put("message", "No Case Found");
					response.put("errorCode", ErrorConstants.CASE_NOT_FOUND);
				}
			} catch (Exception e) {
				LOGGER.info("Exception in get Case List!!");
				response.put("status", false);
				response.put("message", "Sorry, Server Error");
				response.put("errorCode", ErrorConstants.SERVER_ERROR);
			}

		} else {
			response.put("status", false);
			response.put("message", "Sorry, you are not authorized to use this resources");
			response.put("errorCode", ErrorConstants.UNAUTHORIZED);

		}
		return response;
	}

	public Map<String, Object> uploadImage(MultipartFile image) {
		System.err.println("IMAGE: " + image);
		FileUploadResponseDTO uploadResponse = null;

		Map<String, Object> responseMap = new HashMap<>();

		if (image != null) {
//			MultipartFile compressDocumentImage = compressionUtils.compressDocumentImage(image);
			String pathKey = "ts/basicFarmerAppImages/";
			String dirPath = "basicFarmerAppImages";
//			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
//			System.out.println("PATHKEY; " + pathKeyResponse.entrySet());
//			if (pathKeyResponse != null) {
//				boolean result = (boolean) pathKeyResponse.get("exist");
//				if (result) {
					uploadResponse = uploadFile(pathKey, image, image.getOriginalFilename());
//				} else {
////					String moduleName = "basic-farmer-app";
//					String moduleName = "ts";
//					DirectoryResponseDTO createDirResp = fileManagerUtil.createDirectory(moduleName, pathKey, dirPath,
//							null);
//					System.out.println("CreateDirResp: " + createDirResp);
//					if (createDirResp != null && createDirResp.isSuccess()) {
//						uploadResponse = uploadFile(pathKey, image, image.getOriginalFilename());
//					}
//				}
//			}
		} // image not null

		if (uploadResponse != null) {
			responseMap.put("status", true);
			responseMap.put("publicURL", uploadResponse.getPublicUrl());
			responseMap.put("imageID", uploadResponse.getId());
			responseMap.put("message", "File uploaded successfully");
		} else {
			responseMap.put("status", false);
			responseMap.put("message", "Fail to uploaded file");
			responseMap.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
		}
		return responseMap;
	}

	private FileUploadResponseDTO uploadFile(String pathKey, MultipartFile image, String fileName) {
//		resizeImage(image);
		FileUploadResponseDTO fileUploadResp = null;

		try {
			fileUploadResp = fileManagerUtil.uploadFile(pathKey, false, fileName, true, image, null);
			System.out.println("FileUploadResp: " + fileUploadResp);
			return fileUploadResp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}// uploadFile(String pathKey, MultipartFile image)

	/**
	 * @param ID
	 * @return
	 * @author Rinkesh KM
	 *         ===============================================================================
	 * @apiNote this API used to fetch all the details related to case by ID of
	 *          case. The condition for fetching the case are. If irrigation source
	 *          ID and ownership document type ID is zero then it won't consider in
	 *          the response.
	 *          ===============================================================================
	 */
	public Map<String, Object> getCaseById(String ID, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		List<FarmerLatLongDTO> farmerCaseKMLPointList = new ArrayList<>();
		try {

			Optional<DrkCaseCropInfo> foundCaseByID = drkCaseCropInfoRepository.findById(ID);
			Map<String, Object> fetchedCaseCropInfoByFinalQuery = new HashMap<>();
			Map<String, Object> rightDetails = new HashMap<>();

			if (foundCaseByID.isPresent()) {
				String selectIrrigationSource = "";
				String selectFarmerFarmOwnership = "";

				String irrigationSourceQuery = "";
				String farmerFarmOwnershipQuery = "";

				if (foundCaseByID.get().getIrrigationSourceID() != null) {
					if (!foundCaseByID.get().getIrrigationSourceID().equals(0)) {
						selectIrrigationSource = "cci.irrigation_source_id AS irrigationSourceID, aim.Name AS irrigationSourceName, ";
						irrigationSourceQuery = "         left join cdt_master_data.agri_source_of_water aim on (aim.ID = cci.irrigation_source_id)";
					}
				}

				String farmerID = drkCaseCropInfoRepository.findFarmerIdByCaseID(foundCaseByID.get().getID());
				

				Optional<CaseCropInfoDTO> foundCaseByFarmerIDAndCaseID = drkCaseCropInfoRepository
						.findCaseByFarmerIDAndCaseID(ID, farmerID);

				
				if (foundCaseByFarmerIDAndCaseID.isPresent()) {
//					if (!foundCaseByFarmerIDAndCaseID.get().getOwnershipTypeID().equals(0)) {
						selectFarmerFarmOwnership = ", act.Name AS cropTypeName, case\n"
								+ "            when ff.has_own_land = '0' and ff.has_leased_land = '0' then '0'\n"
								+ "            when ff.has_own_land = '1' and ff.has_leased_land = '1' then '1'\n"
								+ "            when ff.has_own_land = '0' and ff.has_leased_land = '1' then '2'\n"
								+ "            when ff.has_own_land = '1' and ff.has_leased_land = '0' then '1'\n"
								+ "        end as ownershipTypeID,\n" + "        case\n"
								+ "            when ff.has_own_land = '0' and ff.has_leased_land = '0' then 'Null' \n"
								+ "            when ff.has_own_land = '1' and ff.has_leased_land = '1' then 'Owned'\n"
								+ "            when ff.has_own_land = '0' and ff.has_leased_land = '1' then 'Leased'\n"
								+ "            when ff.has_own_land = '1' and ff.has_leased_land = '0' then 'Owned'\n"
								+ "        end as ownershipTypeName";
//					}
				}
				try {

					fetchedCaseCropInfoByFinalQuery = farmerAppDAO.findCaseById(selectIrrigationSource,
							selectFarmerFarmOwnership, irrigationSourceQuery/* , farmerFarmOwnershipQuery */, request,
							ID, foundCaseByID.get().getCaseID());

					LOGGER.info("Fetched Case Data Is -> {}", fetchedCaseCropInfoByFinalQuery);

				} catch (DataAccessException e) {
//					fetchedCaseCropInfoByFinalQuery = new HashMap<>();
					e.printStackTrace();
				}
			}

			if (!fetchedCaseCropInfoByFinalQuery.isEmpty()) {
				List<String> documentURLList = drkCaseCropInfoRepository
						.findDocumentURLByFarmerID(fetchedCaseCropInfoByFinalQuery.get("farmID"));

				DrkCaseCropInfo caseCropInfo = objectMapper.convertValue(fetchedCaseCropInfoByFinalQuery,
						DrkCaseCropInfo.class);
				
				if(documentURLList.size() == 1 && documentURLList.get(0).contains(",")) {
					String[] imageUrls = documentURLList.get(0).split(",");
					documentURLList.clear();
					for(int i = 0; i < imageUrls.length; i++) {
						documentURLList.add(imageUrls[i].trim());
					}
				}

				caseCropInfo.setOwnershipDocs(documentURLList);

//				List<CaseCropInfoDTO> caseCropInfoDTOList = farmerCaseKMLPointRepository.findAllByFarmIdAndCaseID(
//						String.valueOf(caseCropInfo.getFarmID()), String.valueOf(caseCropInfo.getCaseID()));
				
								
//				if (caseCropInfo.getCropTypeID() == 1 || caseCropInfo.getCropTypeID() == 2) {

					Map<String, String> kmlUrl = caseDetailsRepository.getKmlUrlByFarmIdAndCaseID(
							String.valueOf(caseCropInfo.getFarmID()), String.valueOf(caseCropInfo.getCaseID()));

					URL website = new URL(kmlUrl.get("KmlUrl"));
					String seprateName = kmlUrl.get("KmlUrl").replace("/", " ");
					String[] parts = seprateName.split(" ");
					String fileName = parts[parts.length - 1];
					LOGGER.info("file name is {}", fileName);

					if (!new File(appProperties.getKmlFilePath() + "//").exists()) {
						LOGGER.info("File is created {}", new File(appProperties.getKmlFilePath() + "//").mkdir());
					}

					try (InputStream in = website.openStream();
							BufferedInputStream bis = new BufferedInputStream(in);
							FileOutputStream fos = new FileOutputStream(
									appProperties.getKmlFilePath() + "//" + fileName)) {

						byte[] data = new byte[1024];
						int count;
						while ((count = bis.read(data, 0, 1024)) != -1) {
							fos.write(data, 0, count);
						}

//				
//				if (!(Files.exists(Paths.get("kml")))) {
//					Files.createDirectories(Paths.get("kml"));
//				}

						farmerCaseKMLPointList = this
								.parseKmlFile(new File(appProperties.getKmlFilePath() + "//" + fileName));
						Files.delete(Paths.get(appProperties.getKmlFilePath() + "//" + fileName));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
//				List<FarmerCaseKMLPoint> farmerCaseKMLPointList = new ArrayList<>();
//
//				for (CaseCropInfoDTO caseCropInfoDTO : caseCropInfoDTOList) {
//
//					FarmerCaseKMLPoint farmerCaseKMLPoint = new FarmerCaseKMLPoint();
//					farmerCaseKMLPoint.setLatitude(caseCropInfoDTO.getLatitude());
//					farmerCaseKMLPoint.setLongitude(caseCropInfoDTO.getLongitude());
//
//					farmerCaseKMLPointList.add(farmerCaseKMLPoint);
//				}

//				}
				caseCropInfo.setCaseKmlLatLongs(farmerCaseKMLPointList);
				
				try {
				
				rightDetails = farmerAppDAO.fingRightsDetails(foundCaseByID.get().getCaseID());
				
				} catch (DataAccessException e) {

					e.printStackTrace();
				}
				
				if(!rightDetails.isEmpty()) {
					
					caseCropInfo.setIsDrKrishiCase(true);
					caseCropInfo.setRightDetails(rightDetails);
					
				}else {
					caseCropInfo.setIsDrKrishiCase(false);
					caseCropInfo.setRightDetails(new HashMap<>());
				}
				
				

				response.put("caseDetails", caseCropInfo);
				response.put("status", true);
				response.put("message", "Case fetched successfully by ID");
			} else {
				throw new CaseNotFoundException(ErrorConstants.CASE_NOT_FOUND, "Case not found", false);
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CaseNotFoundException(ErrorConstants.CASE_NOT_FOUND, "Case not found", false);
		}

	}
	
	private List<FarmerLatLongDTO> parseKmlFile(File file) {
		List<FarmerLatLongDTO> farmerCaseKMLPointList = new ArrayList<>();
		try {
			LOGGER.info("file path is {}", file.getAbsolutePath());
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(file.getPath());
			document.getDocumentElement();

//			System.out.println("Root element :" + document.getElementsByTagName("coordinates").item(0).getTextContent());

			String splitCoordinates[] = document.getElementsByTagName("coordinates").item(0).getTextContent()
					.split("\\s|\\n");

			for (String data : splitCoordinates) {
				if (data.trim() != null && !data.trim().equals("")) {
					String splitData[] = data.split(",");
					
					FarmerLatLongDTO farmerCaseKMLPoint = new FarmerLatLongDTO();
					farmerCaseKMLPoint.setLatitude(Double.parseDouble(splitData[1].trim()));
					farmerCaseKMLPoint.setLongitude(Double.parseDouble(splitData[0].trim()));

					farmerCaseKMLPointList.add(farmerCaseKMLPoint);
				}
			}
			LOGGER.info("farmerCaseKMLPointList : " + farmerCaseKMLPointList);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return farmerCaseKMLPointList;
	}

	@Transactional
	public Map<String, Object> addCase(String authToken, DrkCaseCropInfo caseCropInfo) {

		String kurl = null;
		Map<String, Object> sowingMap = null;
		Map<String, Object> response = new HashMap<>();
		try {

			Optional<DrkFarmer> foundFarmerByAuthToken = drkFarmerRepository.findByAuthToken(authToken);
			if (foundFarmerByAuthToken.isPresent()) {
				String farmrid = foundFarmerByAuthToken.get().getId();
				/** Adding Details to farm farm */
				FarmerFarm fFarm = new FarmerFarm();
				String generatedFarmerFarmID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARMER_FARM);
				while (true) {
					Optional<FarmerFarm> drkFoundFarmerFarmID = farmerFarmRepository.findById(generatedFarmerFarmID);
					if (drkFoundFarmerFarmID.isPresent()) {
						generatedFarmerFarmID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARMER_FARM);
					} else {
						break;
					}
				}
				fFarm.setId(generatedFarmerFarmID);
				fFarm.setFarmerID(foundFarmerByAuthToken.get().getId());
				fFarm.setHasOwnLand(caseCropInfo.getOwnershipTypeID() == 1 ? 1 : 0);
				fFarm.setHasLeasedLand(caseCropInfo.getOwnershipTypeID() == 2 ? 1 : 0);
				FarmerFarm savedFarmerFarm = farmerFarmRepository.save(fFarm);

				/** Adding Details to image store */
				if (caseCropInfo.getOwnershipDocs() != null && caseCropInfo.getOwnershipDocs().size() > 0) {
					List<ImageStore> imageStoreList = new ArrayList<>();
					for (int i = 0; i < caseCropInfo.getOwnershipDocs().size(); i++) {
						ImageStore imageStore = new ImageStore();
						imageStore.setMetaID(1);
						imageStore.setSourceID(savedFarmerFarm.getId());
						imageStore.setFarmerID(farmrid);
						imageStore.setImageUrl(caseCropInfo.getOwnershipDocs().get(i));
						imageStoreList.add(imageStore);
					}
					imageStoreRepository.saveAll(imageStoreList);
				}
				
				/** Adding Details to Farm Case */
				DrkFarmCase drkFarmCase = new DrkFarmCase();
				drkFarmCase.setFarmId(savedFarmerFarm.getId());

				String generatedFarmCaseID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARM_CASE);
				while (true) {
					Optional<DrkFarmCase> drkFoundFarmCaseID = drkFarmCaseRepository.findById(generatedFarmCaseID);
					if (drkFoundFarmCaseID.isPresent()) {
						generatedFarmCaseID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARM_CASE);
					} else {
						break;
					}
				}
				drkFarmCase.setId(generatedFarmCaseID);
				drkFarmCase.setCropType(caseCropInfo.getCropTypeID());
				DrkFarmCase savedFarmCase = drkFarmCaseRepository.save(drkFarmCase);

				/** Adding Details To Case Crop Info */
				String generatedCaseCropInfoID = generateFarmerAppID.generateKeysForApp(EntityConstants.CASE_CROP_INFO);
				while (true) {
					Optional<DrkCaseCropInfo> drkFoundFarmCaseID = drkCaseCropInfoRepository
							.findById(generatedCaseCropInfoID);
					if (drkFoundFarmCaseID.isPresent()) {
						generatedCaseCropInfoID = generateFarmerAppID
								.generateKeysForApp(EntityConstants.CASE_CROP_INFO);
					} else {
						break;
					}
				}
				caseCropInfo.setID(generatedCaseCropInfoID);
				caseCropInfo.setCaseID(String.valueOf(savedFarmCase.getId()));
//				caseCropInfo.setCaseStatusID(3);
				if (caseCropInfo.getCropTypeID() == 1 || caseCropInfo.getCropTypeID() == 2 || caseCropInfo.getCropTypeID() == 3) {
					sowingMap = drkrishiDaoRepository.getCurrentSowingWeekAndYear(caseCropInfo.getSowingDate());
				}
				if (sowingMap != null) {
					caseCropInfo.setSowingWeek(Integer.valueOf(sowingMap.get("currentSowingWeek").toString()));
					caseCropInfo.setSowingYear(Integer.valueOf(sowingMap.get("currentSowingYear").toString()));
					caseCropInfo.setFarmerGivenSowingWeek(Integer.valueOf(sowingMap.get("currentSowingWeek").toString()));
					caseCropInfo.setFarmerGivenSowingYear(Integer.valueOf(sowingMap.get("currentSowingYear").toString()));
				} else {
					caseCropInfo.setSowingWeek(0); // these are not null fields in Table
					caseCropInfo.setSowingYear(0);
				}
				DrkCaseCropInfo savedCaseCropInfo = drkCaseCropInfoRepository.save(caseCropInfo);

				/** Adding Details to Farmer Case Kml Points */
//				FarmerCaseKMLPoint saveCaseKMLPoint = null;
				if (caseCropInfo.getCaseKmlLatLongs() != null) {

					List<FarmerLatLongDTO> farmerCaseKMLPointList = caseCropInfo.getCaseKmlLatLongs();
//					Optional<FarmerFarm> farmerFarm = Optional.empty();
					kurl = this.createKMLWithUrl(farmerCaseKMLPointList, savedFarmCase.getId());

//					for (FarmerCaseKMLPoint farmerCaseKMLPoint : farmerCaseKMLPointList) {
//						FarmerCaseKMLPoint farmerCaseKML = new FarmerCaseKMLPoint();
//
//						farmerCaseKML.setLatitude(farmerCaseKMLPoint.getLatitude());
//						farmerCaseKML.setLongitude(farmerCaseKMLPoint.getLongitude());
//						farmerCaseKML.setCaseID(String.valueOf(savedFarmCase.getId()));
//						if (savedFarmerFarm != null) {
//							farmerCaseKML.setFarmId(String.valueOf(savedFarmerFarm.getId()));
//						} else {
//							farmerFarm.ifPresent(farm -> farmerCaseKML.setFarmId(String.valueOf(farm.getId())));
//						}
//						saveCaseKMLPoint = farmerCaseKMLPointRepository.save(farmerCaseKML);
//					}
				}

				Integer commodityID = masterDaoRepository.getCommodityIdByVarietyId(savedCaseCropInfo.getVarietyID());
				FarmerAddressBookDTO farmerGeoDetails = drkFarmerAddressBookRepository.getFarmerGeoDetails(savedFarmerFarm.getFarmerID());
				
				/** Adding Details to Case Details */
				try {
					CaseDetailsEntity caseDetails = new CaseDetailsEntity();
					FarmLocation farmLocation = null;
					Integer aczId = 0;
					Integer zonalCommodityID =0;
					Integer zonalVarietyID =0;
					
//					Map<String, Object> latLongByCaseId = farmerAppRepository.getLatLongByCaseId(savedFarmCase.getId());
					Map<String, Object> latLongByCaseId = new HashMap<>();
					
					latLongByCaseId.put("Latitude", caseCropInfo.getCaseKmlLatLongs().get(0).getLatitude());
					latLongByCaseId.put("Longitude", caseCropInfo.getCaseKmlLatLongs().get(0).getLongitude());
					
					if (latLongByCaseId != null) {
						farmLocation = farmerAppUtils.getDataByCordinates(latLongByCaseId);
					}

//					CaseDetailsEntity caseDetailsEntity = farmerAppRepository.getCaseDetails(savedFarmCase.getId());

					if (farmLocation != null && farmLocation.getStatus().equals("true")) {
						List<FarmCaseLocation> _data = farmLocation.getData();
						for (FarmCaseLocation data : _data) {
							aczId = data.getAczID();
							caseDetails.setRegionID(Objects.isNull(data.getRegionID()) ? 0 : data.getRegionID());
							caseDetails.setDistrictCode(
									Objects.isNull(data.getDistrictCode()) ? 0 : data.getDistrictCode());
							caseDetails.setStateCode(Objects.isNull(data.getStateCode()) ? 0 : data.getStateCode());
							caseDetails.setSubRegionID(
									Objects.isNull(data.getSubRegionID()) ? "0" : data.getSubRegionID());
							caseDetails
									.setVillageCode(Objects.isNull(data.getVillageCode()) ? 0 : data.getVillageCode());
						}
					} else {
//						caseDetails.setRegionID(
//								Objects.isNull(caseDetailsEntity.getRegionID()) ? 0 : caseDetailsEntity.getRegionID());
						caseDetails.setRegionID(
								Objects.isNull(farmerGeoDetails.getRegionID()) ? 0 : farmerGeoDetails.getRegionID());
//						caseDetails.setDistrictCode(Objects.isNull(caseDetailsEntity.getDistrictCode()) ? 0
//								: caseDetailsEntity.getDistrictCode());
						caseDetails.setDistrictCode(Objects.isNull(farmerGeoDetails.getDistrictCode()) ? 0
								: farmerGeoDetails.getDistrictCode());
//						caseDetails.setStateCode(Objects.isNull(caseDetailsEntity.getStateCode()) ? 0
//								: caseDetailsEntity.getStateCode());
						caseDetails.setStateCode(Objects.isNull(farmerGeoDetails.getStateCode()) ? 0
								: farmerGeoDetails.getStateCode());
//						caseDetails.setSubRegionID(Objects.isNull(caseDetailsEntity.getSubRegionID()) ? "0"
//								: caseDetailsEntity.getSubRegionID());
						caseDetails.setSubRegionID(Objects.isNull(farmerGeoDetails.getSubRegionID()) ? "0"
								: farmerGeoDetails.getSubRegionID());
//						caseDetails.setVillageCode(Objects.isNull(caseDetailsEntity.getVillageCode()) ? 0
//								: caseDetailsEntity.getVillageCode());
						caseDetails.setVillageCode(Objects.isNull(farmerGeoDetails.getVillageCode()) ? 0
								: farmerGeoDetails.getVillageCode());
					}
//					caseDetails.setFarmerID(
//							Objects.isNull(caseDetailsEntity.getFarmerID()) ? "0" : caseDetailsEntity.getFarmerID());
					caseDetails.setFarmerID(
							Objects.isNull(savedFarmerFarm.getFarmerID()) ? "0" : savedFarmerFarm.getFarmerID());
//					caseDetails.setFarmID(
//							Objects.isNull(caseDetailsEntity.getFarmID()) ? "0" : caseDetailsEntity.getFarmID());
					caseDetails.setFarmID(
							Objects.isNull(savedFarmCase.getFarmId() ) ? "0" : savedFarmCase.getFarmId());
//					caseDetails.setCaseID(caseDetailsEntity.getCaseID());
					caseDetails.setCaseID(savedFarmCase.getId());
//					caseDetails.setCommodityID(Objects.isNull(caseDetailsEntity.getCommodityID()) ? 0
//							: caseDetailsEntity.getCommodityID());
					caseDetails.setCommodityID(Objects.isNull(commodityID) ? 0
							: commodityID);
//					caseDetails.setVarietyID(
//							Objects.isNull(caseDetailsEntity.getVarietyID()) ? 0 : caseDetailsEntity.getVarietyID());
					caseDetails.setVarietyID(
							Objects.isNull(savedCaseCropInfo.getVarietyID()) ? 0 : savedCaseCropInfo.getVarietyID());
//					caseDetails.setSowingWeek(
//							Objects.isNull(caseDetailsEntity.getSowingWeek()) ? 0 : caseDetailsEntity.getSowingWeek());
					caseDetails.setSowingWeek(
							Objects.isNull(savedCaseCropInfo.getSowingWeek()) ? 0 : savedCaseCropInfo.getSowingWeek());

//					caseDetails.setCropTypeID(
//							Objects.isNull(caseDetailsEntity.getCropTypeID()) ? 0 : caseDetailsEntity.getCropTypeID());
					caseDetails.setCropTypeID(
							Objects.isNull(savedFarmCase.getCropType()) ? 0 : savedFarmCase.getCropType());
					
					
					if(caseCropInfo.getCropTypeID() == 1 || caseCropInfo.getCropTypeID() == 2) {
//						zonalCommodityID = farmerAppRepository.getZonalCommodityIDWithSowingWeek(aczId,
//								caseDetailsEntity.getCommodityID(), caseDetailsEntity.getSowingWeek());
//						zonalCommodityID = farmerAppRepository.getZonalCommodityIDWithSowingWeek(aczId,
//								caseDetailsEntity.getCommodityID(), savedCaseCropInfo.getSowingWeek());
						zonalCommodityID = farmerAppRepository.getZonalCommodityIDWithSowingWeek(aczId,
								commodityID, savedCaseCropInfo.getSowingWeek());
//						zonalVarietyID = farmerAppRepository.getZonalVarietyIDWithSowingWeek(aczId, zonalCommodityID,
//								caseDetailsEntity.getVarietyID(), caseDetailsEntity.getSowingWeek());
						zonalVarietyID = farmerAppRepository.getZonalVarietyIDWithSowingWeek(aczId, zonalCommodityID,
								savedCaseCropInfo.getVarietyID(), savedCaseCropInfo.getSowingWeek());
						Integer currentPhenophase = masterDaoRepository.getCurrentPhenophase(zonalVarietyID,
								caseCropInfo.getSowingDate());
						caseDetails.setCurrentPhenophaseID(currentPhenophase);
						
					}else {
//						zonalCommodityID = farmerAppRepository.getZonalCommodityIDWithoutSowingWeek (aczId,caseDetailsEntity.getCommodityID());
						zonalCommodityID = farmerAppRepository.getZonalCommodityIDWithoutSowingWeek (aczId,commodityID);
//						zonalVarietyID = farmerAppRepository.getZonalVarietyIDWithoutSowingWeek(aczId, zonalCommodityID,caseDetailsEntity.getVarietyID());
						zonalVarietyID = farmerAppRepository.getZonalVarietyIDWithoutSowingWeek(aczId, zonalCommodityID,savedCaseCropInfo.getVarietyID());
					}
					
					caseDetails.setCroppingArea(String.valueOf(savedCaseCropInfo.getCropArea()));
					caseDetails.setCurrentQuantity(savedCaseCropInfo.getFarmerGivenYield());
					caseDetails.setIrrigationSourceID(savedCaseCropInfo.getIrrigationSourceID());
					caseDetails.setKmlUrl(kurl);
					caseDetails.setZonalCommodityID(zonalCommodityID);
					caseDetails.setZonalVarietyID(zonalVarietyID);
					caseDetails.setHarvestWeek(savedCaseCropInfo.getHarvestWeek());
					caseDetails.setHarvestYear(savedCaseCropInfo.getHarvestYear());

					LOGGER.info("CaseID {}", caseDetails.getCaseID());
					LOGGER.info("sowin Date {}", savedCaseCropInfo.getSowingDate());
					LOGGER.info("ZonalVarietyID {}", zonalVarietyID);
					LOGGER.info("StateCode {}", caseDetails.getStateCode());
					
					if (sowingMap != null) {
						caseDetails
								.setCurrentSowingYear(Integer.valueOf(sowingMap.get("currentSowingYear").toString()));
						caseDetails
								.setCurrentSowingWeek(Integer.valueOf(sowingMap.get("currentSowingWeek").toString()));
						caseDetails.setFarmerGivenSowingWeek(
								Integer.valueOf(sowingMap.get("currentSowingWeek").toString()));
						caseDetails.setFarmerGivenSowingYear(
								Integer.valueOf(sowingMap.get("currentSowingYear").toString()));

					} else {
						caseDetails.setCurrentSowingYear(0);
						caseDetails.setCurrentSowingWeek(0);
						 caseDetails.setFarmerGivenSowingWeek( 0);
						caseDetails.setFarmerGivenSowingYear(0);
					}

					/** Farmer given sowing week */
					savedCaseCropInfo.setAczId(aczId);
					savedCaseCropInfo.setFarmerGivenSowingWeek(caseCropInfo.getSowingWeek());
					
					if (caseCropInfo.getCropTypeID() == 1 || caseCropInfo.getCropTypeID() == 2) {
						
						Integer currentDas = farmerAppRepository.getCurrentDas(caseCropInfo.getSowingDate());
						Integer endDas = farmerAppRepository.getEndDas(zonalVarietyID);
						
						
						if(endDas >= currentDas) {
							savedCaseCropInfo.setCaseStatusID(3);
							caseDetails.setExpired("No");
						}else {
							savedCaseCropInfo.setCaseStatusID(26);
							caseDetails.setExpired("Yes");
						}
						
					}else {
						savedCaseCropInfo.setCaseStatusID(3);
					}
					drkCaseCropInfoRepository.save(savedCaseCropInfo);
					Map<String,Date> correctedSowingDateMap = drkCaseCropInfoRepository.getCorrectedSowingDate(savedFarmCase.getId());
					Date correctedSowingDate = correctedSowingDateMap.get("CorrectedSowingDate");
//					caseDetails.setCorrectedSowingDate(caseDetailsEntity.getCorrectedSowingDate());
					caseDetails.setCorrectedSowingDate(correctedSowingDate);
					caseDetails.setCropAreaMismatched(
							caseCropInfo.getCropAreaMismatched().equals("true") ? "True" : "False");
					if (zonalVarietyID != 0) {
						Optional<ZonalStandardQuantityChart> foundStdChart = stdQtyChartRepository
								.findAllByZonalVarietyID(zonalVarietyID);
						if (foundStdChart.isPresent()) {
							ZonalStandardQuantityChart qtyChart = foundStdChart.get();
							caseDetails.setEstimatedQuantity(caseCropInfo.getFarmerGivenYield());
							caseDetails.setAllowableVarianceQtyPositive(
									(caseCropInfo.getFarmerGivenYield() * qtyChart.getStandardPositiveVariancePercent())
											/ 100);
							caseDetails.setAllowableVarianceQtyNegative(
									(caseCropInfo.getFarmerGivenYield() * qtyChart.getStandardNegativeVariancePercent())
											/ 100);
						}
					}
					caseDetailsRepository.save(caseDetails);
				} catch (Exception e) {
					LOGGER.info("Exception Occured while adding case_details gstm transitory!!");
					e.printStackTrace();
				}
				response.put("status", true);
				response.put("message", "Case Added successfully");
			} else {
				response.put("status", false);
				response.put("message", "Sorry, you are not authorized to use this resources");
				response.put("errorCode", ErrorConstants.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", false);
			response.put("message", "Sorry, failed to save case details");
			response.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
		}
		return response;
	}

	@Transactional
	public Map<String, Object> updateProfile(String authToken, FarmerKYC farmerKYCList) {
		Map<String, Object> response = new HashMap<>();

		if (farmerKYCList.getLiKYCDocs().size() != 2) {
			response.put("status", false);
			response.put("message", "Failed to save KYC Document, Please Provide Mandatory Two Document");
			response.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
			return response;
		}

		Optional<DrkFarmer> foundFarmerByAuthToken = drkFarmerRepository.findByAuthToken(authToken);
		try {
			if (foundFarmerByAuthToken.isPresent()) {

				String farmerID = foundFarmerByAuthToken.get().getId();
				FarmerKYC savedfarmerKyc= null;
//				List<FarmerKYC> savedFarmerKYCList = new ArrayList<>();
//				int farmerKYCSaveCount = 0;
//				int imageStoreSaveCount = 0;
//
//				for (String farmerKY : farmerKYCList.getLiKYCDocs()) {
//					FarmerKYC farmerKYC = new FarmerKYC();
//					farmerKYC.setID(generateFarmerAppID.generateKeysForApp(EntityConstants.FARMER_KYC));
//					farmerKYC.setFarmerID(foundFarmerByAuthToken.get().getId());
//
//					FarmerKYC savedFarmerKYC = farmerKYCRepository.saveAndFlush(farmerKYC);
//					LOGGER.info("Saved Farmer KYC is {}", savedFarmerKYC);
//
//					savedFarmerKYCList.add(savedFarmerKYC);
//					farmerKYCSaveCount++;
//
//				}
//
//				/** Saving image to image store */
//				for (String farmerKYCImg : farmerKYCList.getLiKYCDocs()) {
//
//					FarmerKYC farmerKYC = savedFarmerKYCList.get(imageStoreSaveCount);
//
//					LOGGER.info("Updating image store with Farmer ID {}",
//							savedFarmerKYCList.get(imageStoreSaveCount).getID());
//
//					Optional<ImageStore> fetchedImageSource = imageStoreRepository.findByMetaIDAndSourceID(2,
//							farmerKYC.getID());
//
//					ImageStore imageStore = new ImageStore();
//					fetchedImageSource.ifPresent(store -> imageStore.setID(store.getID()));
//					imageStore.setImageUrl(farmerKYCImg);
//					imageStore.setSourceID(String.valueOf(farmerKYC.getID()));
//					imageStore.setMetaID(2);
//
//					imageStoreRepository.saveAndFlush(imageStore);
//					imageStoreSaveCount++;
//				}

				

//				=================================
				
				String kycURls = StringUtils.join(farmerKYCList.getLiKYCDocs(), ",");
				
				List<FarmerKYC> findAllByFarmerID = farmerKYCRepository.findAllByFarmerID(farmerID);
				FarmerKYC farmerKyc = new FarmerKYC();
				
				if(!(findAllByFarmerID.size()>0)) {

					String generatedFarmerKycID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARMER_KYC);
					while (true) {
						Optional<FarmerKYC> foundFarmerKycID = farmerKYCRepository.findById(generatedFarmerKycID);
						if (foundFarmerKycID.isPresent()) {
							generatedFarmerKycID = generateFarmerAppID.generateKeysForApp(EntityConstants.FARMER_KYC);
						} else {
							break;
						}
					}
					farmerKyc.setID(generatedFarmerKycID);
					farmerKyc.setFarmerID(farmerID);
				
					savedfarmerKyc = farmerKYCRepository.save(farmerKyc);
				}else {
					savedfarmerKyc = findAllByFarmerID.get(0);
				}
				
				Optional<ImageStore> foundImageSource = imageStoreRepository.findByMetaIDAndFarmerID(2,farmerID);
				if(!foundImageSource.isPresent()) {
					ImageStore imgstr = new ImageStore();
					imgstr.setFarmerID(savedfarmerKyc.getFarmerID());
					imgstr.setMetaID(2);
					imgstr.setSourceID(savedfarmerKyc.getID());
					imgstr.setImageUrl(kycURls);
					
					imageStoreRepository.save(imgstr);
				}else {
					ImageStore imgstr = foundImageSource.get();
					imgstr.setFarmerID(savedfarmerKyc.getFarmerID());
					imgstr.setMetaID(2);
					imgstr.setSourceID(savedfarmerKyc.getID());
					imgstr.setImageUrl(kycURls);
					imageStoreRepository.save(imgstr);
				}
				
				drkFarmerRepository.updateFarmerLocationByFarmerID(farmerKYCList.getLatitude(),farmerKYCList.getLongitude(), foundFarmerByAuthToken.get().getId());
				farmerKYCRepository.updateFarmerKyc();
				
				/** 07-06-2021 add farmer *****/
				try {
					Integer addressId = null;
					Optional<DrkFarmer> farmer = drkFarmerRepository.findById(farmerID);
					if (farmer.isPresent()) {
						DrkFarmer drkFarmer = farmer.get();
						addressId = drkFarmer.getAddressId();
						drkFarmer.setId(drkFarmer.getId());
						drkFarmer.setFarmerName(drkFarmer.getFarmerName());
						drkFarmer.setPrimaryMobNumber(drkFarmer.getPrimaryMobNumber());
						drkFarmer.setLatitude(drkFarmer.getLatitude());
						drkFarmer.setLongitude(drkFarmer.getLongitude());
						drkFarmer.setCropArea(0.0);
						drkFarmer.setFarmSize(farmerKYCList.getFarmSize());
						drkFarmerRepository.save(drkFarmer);
					}
					
					if(!Objects.isNull(addressId)) {
						Optional<FarmerAddressBook> farmerAddressBook = drkFarmerAddressBookRepository.findById(addressId);
						if (farmerAddressBook.isPresent()) {
							FarmerAddressBook farmerAddrBook = farmerAddressBook.get();
							farmerAddrBook.setAddressLine1(farmerKYCList.getAddressLine1());
							farmerAddrBook.setPinCode(farmerKYCList.getPinCode());
							drkFarmerAddressBookRepository.save(farmerAddrBook);
						}
					}else {
						savedfarmerKyc.setPermanentAddress(farmerKYCList.getAddressLine1());
						farmerKYCRepository.save(savedfarmerKyc);
					}
				
					response.put("status", true);
					response.put("message", "KYC Document saved successfully");
				
				} catch (Exception e) {
					e.printStackTrace();
					response.put("status", false);
					response.put("message", "Failed to save KYC Document");
					response.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
				}

			} else {
				response.put("status", false);
				response.put("message", "Sorry, you are not authorized to use this resources");
				response.put("errorCode", ErrorConstants.UNAUTHORIZED);
			}

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			response.put("status", false);
			response.put("message", "Failed to save KYC Document");
			response.put("errorCode", ErrorConstants.FAIL_TO_SAVE);
		
		}
		return response;
	}

	public Map<String, Object> getMarketData(Integer commodityId, String authToken) {
		Map<String, Object> responseMap = new HashMap<>();
		List<Map<String, Object>> marketData = null;
		List<Object> marketDataList = new ArrayList<>();

		Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authToken);
		DrkFarmer farmer = null;
		if (farmerFound.isPresent()) {
			try {

				farmer = farmerFound.get();
				List<Map<String, Object>> neighbourDistrictList = masterDaoRepository
						.getNeighbourDistrictByCommodityID(farmer.getId(), commodityId);
				String neighbourDistricts = masterDataService.getDistinctNeighbourDistrict(neighbourDistrictList);

				if (neighbourDistricts != null) {
					String[] districtArr = neighbourDistricts.split(",");
					for (int i = 0; i < districtArr.length; i++) {
						String arrivalDate = this.marketPriceRepository.getlatestArrivalDate(Integer.valueOf(districtArr[i].trim()),
								commodityId);
						LOGGER.info("arrivalDate {}", arrivalDate);
						marketData = this.marketPriceRepository.getMarketData(Integer.valueOf(districtArr[i].strip()),
								commodityId, arrivalDate != null ? arrivalDate : "");
						if (marketData != null && !marketData.isEmpty()) {
							for (Map<String, Object> map : marketData) {
								marketDataList.add(map);
							}
						}
					}

				} else {
					responseMap.put("status", false);
					responseMap.put("message", "Market data not for neighbour districts");
				}

				if (!marketDataList.isEmpty()) {
					responseMap.put("status", true);
					responseMap.put("message", "Market data found");
					responseMap.put("marketData", marketDataList);
				} else {
					responseMap.put("status", false);
					responseMap.put("message",
							"Sorry, no market information is available for the crop selected by you for your location.");
					responseMap.put("errorCode", ErrorConstants.NO_DATA_FOUND);
					responseMap.put("marketData", new ArrayList<>());
				}

			} catch (Exception e) {
				e.printStackTrace();
				responseMap.put("status", false);
				responseMap.put("errorCode", ErrorConstants.SERVER_ERROR);
				responseMap.put("message", "Something Went wrong!");
			}
		} else {
			responseMap.put("status", false);
			responseMap.put("errorCode", ErrorConstants.UNAUTHORIZED);
			responseMap.put("message", "Unauthorized User");
		}
		return responseMap;
	}

	private String createKMLWithUrl(List<FarmerLatLongDTO> farmerCaseKMLPoints, String fileName)
			throws IOException, InterruptedException {
		if (farmerCaseKMLPoints.size() > 2) {
			LOGGER.info("***More than two kml points***");
			FileUploadResponseDTO fileRepoDto = null;

			StringBuffer latlongs = new StringBuffer();
			String kmlUrl = null;

			String kmlstart = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
					+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";

			String kmlelementOne = "<Document>\n" + "    <Style id=\"transBluePoly\">\n" + "      <LineStyle>\n"
					+ "        <width>1.5</width>\n" + "      </LineStyle>\n" + "      <PolyStyle>\n"
					+ "        <color>7dff0000</color>\n" + "      </PolyStyle>\n" + "    </Style>\n"
					+ "    <Placemark>\n" + "      <name>Building 41</name>\n"
					+ "      <styleUrl>#transBluePoly</styleUrl>\n" + "      <Polygon>\n"
					+ "        <extrude>1</extrude>\n" + "        <altitudeMode>relativeToGround</altitudeMode>\n"
					+ "        <outerBoundaryIs>\n" + "          <LinearRing>\n" + "            <coordinates> ";
			for (FarmerLatLongDTO farmerCaseKMLPoint : farmerCaseKMLPoints) {
				latlongs.append(farmerCaseKMLPoint.getLongitude()).append(",").append(farmerCaseKMLPoint.getLatitude())
						.append(",5").append("\n");
			}
			latlongs.append(farmerCaseKMLPoints.get(0).getLongitude()).append(",")
					.append(farmerCaseKMLPoints.get(0).getLatitude()).append(",5").append("\n");
			String kmlelementTwo = "</coordinates>\n" + "</LinearRing>\n" + "</outerBoundaryIs>\n" + "</Polygon>\n"
					+ "    </Placemark>\n" + "  </Document>";

			String kmlend = "</kml>";

			ArrayList<String> content = new ArrayList<>();
			content.add(0, kmlstart);
			content.add(1, kmlelementOne.concat(String.valueOf(latlongs)).concat(kmlelementTwo));
			content.add(2, kmlend);

			String kml = content.get(0) + content.get(1) + content.get(2);
//		File file = null;

			File testexists = new File(appProperties.getKmlFilePath() + "//");

			try {
				if (!testexists.exists()) {
					LOGGER.info("File is created {}", testexists.mkdir());
				}
				testexists = new File(appProperties.getKmlFilePath() + "//" + fileName +  ".kml");

				if (!testexists.exists()) {
					kmlUrl = KmlFileWriter(fileName, testexists, kml);
				} else {
					// schleifenvariable
					String filecontent = "";

					ArrayList<String> newoutput = new ArrayList<>();

					try {
						BufferedReader in = new BufferedReader(new FileReader(testexists));
						while ((filecontent = in.readLine()) != null)

							newoutput.add(filecontent);

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					newoutput.add(2, kml);

					String rewrite = "";
					for (String s : newoutput) {
						rewrite += s;
					}

					try {
						kmlUrl = KmlFileWriter(fileName, testexists, rewrite);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Files.delete(Paths.get(appProperties.getKmlFilePath() + "//" + fileName + ".kml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return kmlUrl;
			
		} else if(farmerCaseKMLPoints.size() == 1) {
			LOGGER.info("***Only one kml point***");
			String kmlUrl = null;
			StringBuffer latlong = new StringBuffer();

			String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n";

			String kmlelementOne = "<Document>\n" + 
					"	<Style id=\"s_ylw-pushpin_hl1\">\n" + 
					"		<IconStyle>\n" + 
					"			<scale>1.5</scale>\n" + 
					"			<Icon>\n" + 
					"				<href>http://maps.google.com/mapfiles/kml/paddle/red-circle.png</href>\n" + 
					"			</Icon>\n" + 
					"			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
					"		</IconStyle>\n" + 
					"	</Style>\n" + 
					"	<StyleMap id=\"m_ylw-pushpin4\">\n" + 
					"		<Pair>\n" + 
					"			<key>normal</key>\n" + 
					"			<styleUrl>#s_ylw-pushpin0</styleUrl>\n" + 
					"		</Pair>\n" + 
					"		<Pair>\n" + 
					"			<key>highlight</key>\n" + 
					"			<styleUrl>#s_ylw-pushpin_hl1</styleUrl>\n" + 
					"		</Pair>\n" + 
					"	</StyleMap>\n" + 
					"	<Style id=\"s_ylw-pushpin0\">\n" + 
					"		<IconStyle>\n" + 
					"			<scale>1.1</scale>\n" + 
					"			<Icon>\n" + 
					"				<href>http://maps.google.com/mapfiles/kml/paddle/red-circle.png</href>\n" + 
					"			</Icon>\n" + 
					"			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
					"		</IconStyle>\n" + 
					"	</Style>\n" + 
					"	<Placemark>\n" + 
					"		<LookAt>\n" + 
					"			<longitude>" + farmerCaseKMLPoints.get(0).getLongitude() + "</longitude>\n" + 
					"			<latitude>" + farmerCaseKMLPoints.get(0).getLatitude() + "</latitude>\n" +
					"			<altitude>0</altitude>\n" + 
					"			<heading>0.3802349760469073</heading>\n" + 
					"			<tilt>21.85945944778604</tilt>\n" + 
					"			<range>928.615221376277</range>\n" + 
					"			<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" + 
					"		</LookAt>\n" + 
					"		<styleUrl>#m_ylw-pushpin4</styleUrl>\n" + 
					"		<Point>\n" + 
					"			<gx:drawOrder>1</gx:drawOrder>\n" + 
					"			<coordinates>";
		
			latlong.append(farmerCaseKMLPoints.get(0).getLongitude()).append(",")
					.append(farmerCaseKMLPoints.get(0).getLatitude()).append(",5");
			String kmlelementTwo = "</coordinates>\n" + 
					"		</Point>\n" + 
					"	</Placemark>\n" + 
					"</Document>\n";

			String kmlend = "</kml>";

			ArrayList<String> content = new ArrayList<>();
			content.add(0, kmlstart);
			content.add(1, kmlelementOne.concat(String.valueOf(latlong)).concat(kmlelementTwo));
			content.add(2, kmlend);

			String kml = content.get(0) + content.get(1) + content.get(2);
//		File file = null;

			File testexists = new File(appProperties.getKmlFilePath() + "//");

			try {
				if (!testexists.exists()) {
					LOGGER.info("File is created {}", testexists.mkdir());
				}
				testexists = new File(appProperties.getKmlFilePath() + "//" + fileName +  ".kml");

				if (!testexists.exists()) {
					kmlUrl = KmlFileWriter(fileName, testexists, kml);
				} else {
					// schleifenvariable
					String filecontent = "";

					ArrayList<String> newoutput = new ArrayList<>();

					try {
						BufferedReader in = new BufferedReader(new FileReader(testexists));
						while ((filecontent = in.readLine()) != null)

							newoutput.add(filecontent);

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					newoutput.add(2, kml);

					String rewrite = "";
					for (String s : newoutput) {
						rewrite += s;
					}

					try {
						kmlUrl = KmlFileWriter(fileName, testexists, rewrite);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Files.delete(Paths.get(appProperties.getKmlFilePath() + "//" + fileName + ".kml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return kmlUrl;
		} else {
			return null;
		}
	}

	private String KmlFileWriter(String fileName, File testexists, String rewrite) throws IOException {
		Writer fwriter;
		FileUploadResponseDTO fileRepoDto;
		fwriter = new FileWriter(appProperties.getKmlFilePath() + "//" + fileName + ".kml");
		fwriter.write(rewrite);
		fwriter.flush();
		fwriter.close();
		fileRepoDto = this.fileManager.uploadFile("ts/case_kml/", false, testexists.getName(), true,
				fileManagerUtil.getMultipartFile(testexists), null);
		LOGGER.info("File Name {}", fileRepoDto.getPublicUrl());

		return fileRepoDto.getPublicUrl();
	}

	public Map<String, Object> getTermsAndCondition() {

		Map<String, Object> responseMap = new HashMap<>();

		try {
			String TnC = farmerAppRepository.getTnC();

			responseMap.put("status", true);
			responseMap.put("text", TnC);
			responseMap.put("message", "Terms And Condition Delivered Successfully");

		} catch (Exception e) {
			responseMap.put("status", false);
			responseMap.put("error", "Failed To Fetch Terms And Condition");
			responseMap.put("message", e);
			return responseMap;
		}
		return responseMap;

	}
	
	public Map<String, Object> saveLatLong(String authToken,FarmerLatLongDTO farmerLatLongDTO) {
		
		Map<String, Object> responseMessage = new HashMap<>();
		
		try {
			
		Optional<DrkFarmer> farmerFound = drkFarmerRepository.findByAuthToken(authToken);
		
		if(farmerFound.isPresent()) {
			
			Optional<FarmerLocation> farmerLocationFound = farmerLocationRepository.findByFarmerID(farmerFound.get().getId());
			
			if(farmerLocationFound.isPresent()) {
			
			FarmerLocation farmerLocation = farmerLocationFound.get();
			farmerLocation.setLatitude(farmerLatLongDTO.getLatitude());
			farmerLocation.setLongitude(farmerLatLongDTO.getLongitude());
			farmerLocationRepository.save(farmerLocation);
			
			}else {
				
				FarmerLocation farmerLocation = new FarmerLocation();
				farmerLocation.setFarmerID(farmerFound.get().getId());
				farmerLocation.setLatitude(farmerLatLongDTO.getLatitude());
				farmerLocation.setLongitude(farmerLatLongDTO.getLongitude());
				farmerLocationRepository.save(farmerLocation);
				
			}
			
			responseMessage.put("status", true);
			responseMessage.put("message", "Farmer Lat Long saved successfully");
			
		}else{
			
			responseMessage.put("status", false);
			responseMessage.put("message", "Unauthorized User");
			responseMessage.put("errorCode", ErrorConstants.UNAUTHORIZED);
		}
		
		return responseMessage;
		
		} catch (Exception e) {
			LOGGER.error("Server Error : ", e);
			responseMessage.put("status", false);
			responseMessage.put("error", "Failed To save Farmer Lat Long");
			responseMessage.put("message", e);
			return responseMessage;
			
		}
	}
	
}
