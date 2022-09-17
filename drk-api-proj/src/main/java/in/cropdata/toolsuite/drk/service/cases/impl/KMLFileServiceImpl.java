package in.cropdata.toolsuite.drk.service.cases.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import in.cropdata.toolsuite.drk.controller.cases.KMLFileRestController;
import in.cropdata.toolsuite.drk.dao.cases.CaseDetailsDao;
import in.cropdata.toolsuite.drk.dao.cases.KMLFileDao;
import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.cases.FileStorageException;
import in.cropdata.toolsuite.drk.model.cases.CaseDetails;
import in.cropdata.toolsuite.drk.properties.FileStorageProperties;
import in.cropdata.toolsuite.drk.properties.GstmProperties;
import in.cropdata.toolsuite.drk.service.cases.KMLFileService;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import in.cropdata.toolsuite.sdk.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         23-Nov-2019
 */
@Slf4j
@Service
public class KMLFileServiceImpl implements KMLFileService {

	private static final Logger logger = LoggerFactory.getLogger(KMLFileRestController.class);

	private static String _filename = null;
	private static String _absPath = "";

	@Autowired
	KMLFileDao kmlFileDao;

	@Autowired
	ResourceDao resourceDao;

	private Path fileStorageLocation;

	private Path _zipFileStorageLocation;

	@Autowired
	FileStorageProperties fileStorageProperties;

	@Autowired
	GstmProperties gstmProperties;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private PlatformTransactionManager gstmTransactionManager;

	@Override
	public File targetFile(String fileName, String fileExtn) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this._zipFileStorageLocation = Paths.get(gstmProperties.getZipExctrzct()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
			Files.createDirectories(this._zipFileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					e);
		}
		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		File targetFile = new File(targetLocation + fileExtn);
		logger.info("targetLocation " + targetLocation.getFileName());
		return targetFile;
	}

	@Override
	public Map _exctractCaseDetails(MultipartFile _zipFile, List<CaseDetails> _data) {
		_filename = _zipFile.getOriginalFilename();
		Map<Integer, Object> _kmlMap = new HashMap<>();
		try {
			File _targetFile = targetFile(_zipFile.getOriginalFilename(), "");
			(_zipFile).transferTo(_targetFile);
			_absPath = _targetFile.getAbsolutePath();
			try {
				ZipFile _unzip = new ZipFile(_targetFile.getAbsoluteFile());
				List _fileHeaderList = _unzip.getFileHeaders();
//				if (_data.size() == _fileHeaderList.size()) {
				try {
					log.info("store " + _zipFile.getOriginalFilename() + "to " + _absPath);
					_unzip.extractAll(this.gstmProperties.getZipExctrzct());
					log.info(_zipFile.getOriginalFilename() + "exctract to " + gstmProperties.getZipExctrzct());
				} catch (ZipException ze) {
					log.error("exception occured while decompressing " + _zipFile.getOriginalFilename() + " file "
							+ ze.getMessage());
					throw new ZipException("Zip Extraction Failed");
				}
				List<String> failedCases = new ArrayList<String>();
				List<CaseDetails> _kmlList = new ArrayList<>();
				String kmlPath = this.gstmProperties.getZipExctrzct();
				if (kmlPath != null && !kmlPath.endsWith(File.separator)) {
					kmlPath += String.valueOf(File.separator);
				}
				for (CaseDetails _kml : _data) {
					File file = new File(kmlPath + _kml.getKmlName());

					if (file.exists()) {
						// upload KML to HDFS via FileManager
						String _kmlId = null;
						String publicUrl = null;
						String pathKey = "kml";
						String moduleName = "gstm";
						String dirPath = pathKey;
						String contentType = "text/plain";
						byte[] content = null;
						String _kmlFileName = file.getAbsolutePath();
						log.info("file " + _kmlFileName);
						try {
							content = Files.readAllBytes(file.toPath());
						} catch (final IOException e) {
						}
						String fileNameFromAbsulatePath = FileUtil.getFileNameFromAbsulatePath(_kmlFileName);
						MultipartFile _kmlFile = new MockMultipartFile(fileNameFromAbsulatePath, file.getName(),
								contentType, content);
						
//						MultipartFile _kmlFile = this.fileManagerUtil.getMultipartFile(_kmlFileName);
						log.info("__kmlFile >>>>>>>>>>>>>" + _kmlFile.getOriginalFilename());
						Map<String, Object> pathKeyResponse = this.fileManagerUtil.isPathKeyExist(pathKey);
						logger.info("pathKeyResponse : " + pathKeyResponse);
						logger.info("PATHKEY; " + pathKeyResponse.entrySet());
						if (pathKeyResponse != null && pathKeyResponse.get("exist") != null) {
							boolean result = (boolean) pathKeyResponse.get("exist");
							if (result) {

								FileUploadResponseDTO _fileUploadResp = this.fileManagerUtil.uploadFile(pathKey, false,
										_kmlFile.getName(), true, _kmlFile, null);
								if (_fileUploadResp != null && _fileUploadResp.isSuccess()) {
									_kmlId = _fileUploadResp.getId();
									publicUrl = _fileUploadResp.getPublicUrl();
									log.info("publicUrl >>>>>>>>>>>" + publicUrl);
								}

//								_kmlId = uploadKmlFileToAzure(pathKey, _kmlFile);

								logger.info("_kmlId " + _kmlId);
							} else {
								DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
										pathKey, dirPath, null);
								System.out.println("CreateDirResp: " + createDirResp);
								if (createDirResp != null && createDirResp.isSuccess()) {

									FileUploadResponseDTO _fileUploadResp = this.fileManagerUtil.uploadFile(pathKey,
											false, _kmlFile.getName(), true, _kmlFile, null);
									if (_fileUploadResp != null && _fileUploadResp.isSuccess()) {
										_kmlId = _fileUploadResp.getId();
										publicUrl = _fileUploadResp.getPublicUrl();
									}

//									_kmlId = uploadKmlFileToAzure(pathKey, _kmlFile);

								}
							}
							logger.info("kmlId :: " + _kmlId);
							logger.info("publicUrl :: " + publicUrl);
						} // pathKeyResponse not null
						Integer subRegionID = null;
						Integer regionId = null;
						Integer stateCode = null;
						try {
							subRegionID = this.resourceDao.getSubRegionIDByVillageCode(_kml.getVillageCode());
							regionId = this.resourceDao.getRegionIdByVillageCode(_kml.getVillageCode());
							stateCode = this.resourceDao.getStateCodeByVillageCode(_kml.getVillageCode());
						} catch (EmptyResultDataAccessException e) {
							throw new InvalidDataException("Invalid Village Code");
						}

						_kml.setKmlId(_kmlId);
						_kml.setSubRegionID(subRegionID);
						_kml.setRegionID(regionId);
						_kml.setKmlUrl(publicUrl);
						_kml.setStateCode(stateCode);
						_kml.setKmlProcessingStatus("Pending");

//							_kml.setSpotGuidenceKmlID(_kmlId);
//							if (kmlMap.get(_kml.getCaseId()) == null) {
						_kmlList.add(_kml);
						log.info(_kml.getKmlName() + " is marked for processing");
//							} else {
//							updateCasesList.add(_kml);
//								failedCases.add(String.valueOf(_kml.getCaseId()));
//								log.error(_kml.getCaseId() + " duplicate entry");
//							}
					} else {
						failedCases.add(_kml.getKmlName());
						log.error(_kml.getKmlName() + " not found");
					}
				}
				try {
					log.info("Saving " + _kmlList.size() + " records into database.");

					doSomethingWithMysql(_kmlList);
//						if (updateCasesList != null) {
//							int[] batchUpdate = this.caseDetailsDao.batchUpdate(updateCasesList,
//									updateCasesList.size());
//							log.info("batchUpdate count :: " + batchUpdate);
//						}
				} catch (Exception _exception) {
					throw new InvalidDataException(_exception.getMessage());
				}
				HashMap<String, Object> _res = new HashMap<>();
				_res.put("status", HttpStatus.OK);
				_res.put("success", "true");
				_res.put("message", "All KML files and its data have been processed");
				HashMap<String, Object> detailMap = new HashMap<>();
				detailMap.put("FailedCases", failedCases);
				detailMap.put("Reason", "KML file name and the give file name in data is different");
				_res.put("detailedMessage", detailMap);
				return _res;
//				} else {
//					String error = "Data Mismatch: found " + _fileHeaderList.size() + " KML files in ZIP and "
//							+ _data.size() + " Case JSON data.";
//					throw new InvalidDataException(error);
//				}
			} catch (ZipException ze) {
				log.error("exception occured while decompressing " + _zipFile.getOriginalFilename() + " file "
						+ ze.getMessage());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return _kmlMap;
	}

	@Retryable(maxAttempts = 4, backoff = @Backoff(delay = 500))
	public void doSomethingWithMysql(List<CaseDetails> _kmlList) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(gstmTransactionManager); // autowired
//	     transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE); // this increases deadlocks, but prevents multiple simultaneous similar requests from inserting multiple rows

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				kmlFileDao.saveAll(_kmlList);
			}

		});
	}// doSomethingWithMysql

	public String uploadKmlFileToAzure(String _pathKey, MultipartFile _kml) {
		String _id = null;
		try {
			FileUploadResponseDTO _fileUploadResp = this.fileManagerUtil.uploadFile(_pathKey, false, null, true, _kml,
					null);
			if (_fileUploadResp != null && _fileUploadResp.isSuccess()) {
				_id = _fileUploadResp.getId();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return _id;
	}

	@Override
	public Optional<CaseDetails> getSpotGauidenceByCaseId(int caseID) {
		Optional<CaseDetails> id = this.kmlFileDao.findById(caseID);
		return id;
	}

	@Override
	public Map updateCaseDetails(List<CaseDetails> _caseList) {
		List<CaseDetails> _dataList = new ArrayList<>();
		HashMap<String, Object> _res = null;
		try {
			_dataList.addAll(_caseList);
//			for (int i = 0; i < _dataList.size(); i++) {
//				CaseDetails caseDetails = null;
//				Optional<CaseDetails> caseDetailsOP = kmlFileDao.findByCaseId(_dataList.get(i).getCaseId());
//				if (caseDetailsOP.isPresent()) {
//					caseDetails = caseDetailsOP.get();
//					log.info("commodity : " + caseDetails.getCommodityId() + "vari : " + caseDetails.getVarietyId()
//							+ "statecode : " + caseDetails.getStateCode());
//					Double standerdQua = this.kmlFileDao.getStanderdQua(caseDetails.getCommodityId(),
//							caseDetails.getVarietyId(), caseDetails.getStateCode());
//					caseDetails.setYieldPercent(standerdQua);
//					log.info("standerdQua : " + standerdQua);
//					double yield = _dataList.get(i).getCurrentQuantity() / standerdQua * 100;
//					BigDecimal finalYield = new BigDecimal(yield).setScale(2, RoundingMode.HALF_UP);
//					double calculateYield = finalYield.doubleValue();
//					log.info("yield : " + calculateYield);
//					this.kmlFileDao.updateCaseDetails(_dataList.get(i).getCorrectedSowingDate(),
//							_dataList.get(i).getHarvestWeek(), calculateYield, _dataList.get(i).getCurrentQuantity(),
//							_dataList.get(i).getCaseId());
//
//				} else {
//					_res.put("success", "false");
//					_res.put("message", +_dataList.get(i).getCaseId() + " this case not available");
//				}
//
//			}
			CaseDetails caseDetailsDb = null;
			for (CaseDetails caseDetails : _dataList) {
				Optional<CaseDetails> findByCaseId = kmlFileDao.findByCaseId(caseDetails.getCaseId());
				caseDetailsDb = findByCaseId.get();

//				if (caseDetails.getRegionID()!= null) {
//					caseDetailsDb.setRegionID(caseDetails.getRegionID());
//				}
//				if (caseDetails.getSubRegionID() == null) {
//					caseDetails.setSubRegionID(caseDetails.getSubRegionID());
//				}
				if (caseDetails.getFarmerId() != null) {
					caseDetailsDb.setFarmerId(caseDetails.getFarmerId());
				}
				if (caseDetails.getFarmId() != null) {
					caseDetailsDb.setFarmId(caseDetails.getFarmId());
				}

				if (caseDetails.getRightId() != null) {
					caseDetailsDb.setRightId(caseDetails.getRightId());
				}
				if (caseDetails.getLotId() != null) {
					caseDetailsDb.setLotId(caseDetails.getLotId());
				}

				if (caseDetails.getStateCode() != null) {
					caseDetailsDb.setStateCode(caseDetails.getStateCode());
				}
				if (caseDetails.getVillageCode() != null) {
					caseDetailsDb.setVillageCode(caseDetails.getVillageCode());
				}
				if (caseDetails.getCommodityId() != null) {
					caseDetailsDb.setCommodityId(caseDetails.getCommodityId());
				}
				if (caseDetails.getVarietyId() != null) {
					caseDetailsDb.setVarietyId(caseDetails.getVarietyId());
				}
				if (caseDetails.getSeasonID() != null) {
					caseDetailsDb.setSeasonID(caseDetails.getSeasonID());
				}
				if (caseDetails.getSeedSourceId() != null) {
					caseDetailsDb.setSeedSourceId(caseDetails.getSeedSourceId());
				}
				if (caseDetails.getSeedTreatmentId() != null) {
					caseDetailsDb.setSeedTreatmentId(caseDetails.getSeedTreatmentId());
				}
				if (caseDetails.getSeedRate() != null) {
					caseDetailsDb.setSeedRate(caseDetails.getSeedRate());
				}
				if (caseDetails.getIrrigationSourceId() != null) {
					caseDetailsDb.setIrrigationSourceId(caseDetails.getIrrigationSourceId());
				}
				if (caseDetails.getStressSeverityId() != null) {
					caseDetailsDb.setStressSeverityId(caseDetails.getStressSeverityId());
				}
				if (caseDetails.getPaymentStatus() != null) {
					caseDetailsDb.setPaymentStatus(caseDetails.getPaymentStatus());
				}

				if (caseDetails.getFarmerGivenSowingWeek() != null) {
					caseDetailsDb.setFarmerGivenSowingWeek(caseDetails.getFarmerGivenSowingWeek());
				}

				if (caseDetails.getFarmerGivenSowingYear() != null) {
					caseDetailsDb.setFarmerGivenSowingYear(caseDetails.getFarmerGivenSowingYear());
				}

				if (caseDetails.getCurrentSowingWeek() != null) {
					caseDetailsDb.setCurrentSowingWeek(caseDetails.getCurrentSowingWeek());
				}

				if (caseDetails.getCurrentSowingYear() != null) {
					caseDetailsDb.setCurrentSowingYear(caseDetails.getCurrentSowingYear());
				}

				if (caseDetails.getCurrentPhenophaseID() != null) {
					caseDetailsDb.setCurrentPhenophaseID(caseDetails.getCurrentPhenophaseID());
				}
				if (caseDetails.getHarvestWeek() != null) {
					caseDetailsDb.setHarvestWeek(caseDetails.getHarvestWeek());
				}
				if (caseDetails.getHarvestYear() != null) {
					caseDetailsDb.setHarvestYear(caseDetails.getHarvestYear());
				}
				if (caseDetails.getKmlId() != null) {
					caseDetailsDb.setKmlId(caseDetails.getKmlId());
				}
				if (caseDetails.getKmlName() != null) {
					caseDetailsDb.setKmlName(caseDetails.getKmlName());
				}
				if (caseDetails.getKmlProcessingStatus() != null) {
					caseDetailsDb.setKmlProcessingStatus(caseDetails.getKmlProcessingStatus());
				}
				if (caseDetails.getSpotGuidenceKmlID() != null) {
					caseDetailsDb.setSpotGuidenceKmlID(caseDetails.getSpotGuidenceKmlID());
				}
				if (caseDetails.getDeathReason() != null) {
					caseDetailsDb.setDeathReason(caseDetails.getDeathReason());
				}
				if (caseDetails.getRightStatus() != null) {
					caseDetailsDb.setRightStatus(caseDetails.getRightStatus());
				}
				if (caseDetails.getPlantHealth() != null) {
					caseDetailsDb.setPlantHealth(caseDetails.getPlantHealth());
				}
				if (caseDetails.getLandAreaMisMatchPercent() != null) {
					caseDetailsDb.setLandAreaMisMatchPercent(caseDetails.getLandAreaMisMatchPercent());
				}
				if (caseDetails.getSeedSample() != null) {
					caseDetailsDb.setSeedSample(caseDetails.getSeedSample());
				}
				if (caseDetails.getEstimatedQuantity() != null) {
					caseDetailsDb.setEstimatedQuantity(caseDetails.getEstimatedQuantity());
				}
				if (caseDetails.getCurrentQuantity() != null) {
					caseDetailsDb.setCurrentQuantity(caseDetails.getCurrentQuantity());
				}
				if (caseDetails.getYieldPercent() != null) {
					caseDetailsDb.setYieldPercent(caseDetails.getYieldPercent());
				}

//				if (caseDetails.getIsBenchmarkSpot() != null) {
//					caseDetailsDb.setIsBenchmarkSpot(caseDetails.getIsBenchmarkSpot());
//				}

				if (caseDetails.getKmlUrl() != null) {
					caseDetailsDb.setKmlUrl(caseDetails.getKmlUrl());
				}
				if (caseDetails.getSeedSampleReceived() != null) {
					caseDetailsDb.setSeedSampleReceived(caseDetails.getSeedSampleReceived());
				}
				if (caseDetails.getDueAmount() != null) {
					caseDetailsDb.setDueAmount(caseDetails.getDueAmount());
				}
				if (caseDetails.getCroppingArea() != null) {
					caseDetailsDb.setCroppingArea(caseDetails.getCroppingArea());
				}
				if (caseDetails.getSpacingPlant() != null) {
					caseDetailsDb.setSpacingPlant(caseDetails.getSpacingPlant());
				}
				if (caseDetails.getIrrigationMethodID() != null) {
					caseDetailsDb.setIrrigationMethodID(caseDetails.getIrrigationMethodID());
				}
				if (caseDetails.getNumberOfIrrigations() != null) {
					caseDetailsDb.setNumberOfIrrigations(caseDetails.getNumberOfIrrigations());
				}
				if (caseDetails.getWeekOfIrrigation() != null) {
					caseDetailsDb.setWeekOfIrrigation(caseDetails.getWeekOfIrrigation());
				}
				if (caseDetails.getYearOfIrrigation() != null) {
					caseDetailsDb.setYearOfIrrigation(caseDetails.getYearOfIrrigation());
				}
				if (caseDetails.getAgrochemicalApplicationTypeID() != null) {
					caseDetailsDb.setAgrochemicalApplicationTypeID(caseDetails.getAgrochemicalApplicationTypeID());
				}
				if (caseDetails.getFertilizerID() != null) {
					caseDetailsDb.setFertilizerID(caseDetails.getFertilizerID());
				}
				if (caseDetails.getFertilizerDose() != null) {
					caseDetailsDb.setFertilizerDose(caseDetails.getFertilizerDose());
				}
				if (caseDetails.getFertilizerSplitDose() != null) {
					caseDetailsDb.setFertilizerSplitDose(caseDetails.getFertilizerSplitDose());
				}
				if (caseDetails.getFertilizerUomID() != null) {
					caseDetailsDb.setFertilizerUomID(caseDetails.getFertilizerUomID());
				}
				if (caseDetails.getFertilizerWeekOfApplication() != null) {
					caseDetailsDb.setFertilizerWeekOfApplication(caseDetails.getFertilizerWeekOfApplication());
				}
				if (caseDetails.getSeedTreatmentDose() != null) {
					caseDetailsDb.setSeedTreatmentDose(caseDetails.getSeedTreatmentDose());
				}
				if (caseDetails.getSeedTreatmentUomID() != null) {
					caseDetailsDb.setSeedTreatmentUomID(caseDetails.getSeedTreatmentUomID());
				}
				if (caseDetails.getAgrochemicalID() != null) {
					caseDetailsDb.setAgrochemicalID(caseDetails.getAgrochemicalID());
				}
				if (caseDetails.getAgrochemicalBrandID() != null) {
					caseDetailsDb.setAgrochemicalBrandID(caseDetails.getAgrochemicalBrandID());
				}
				if (caseDetails.getAgrochemicalDose() != null) {
					caseDetailsDb.setAgrochemicalDose(caseDetails.getAgrochemicalDose());
				}
				if (caseDetails.getAgrochemicalUomID() != null) {
					caseDetailsDb.setAgrochemicalUomID(caseDetails.getAgrochemicalUomID());
				}
				if (caseDetails.getAgrochemicalWeekOfApplication() != null) {
					caseDetailsDb.setAgrochemicalWeekOfApplication(caseDetails.getAgrochemicalWeekOfApplication());
				}
				if (caseDetails.getAgrochemicalYearOfApplication() != null) {
					caseDetailsDb.setAgrochemicalYearOfApplication(caseDetails.getAgrochemicalYearOfApplication());
				}
				if (caseDetails.getMobileTypeID() != null) {
					caseDetailsDb.setMobileTypeID(caseDetails.getMobileTypeID());
				}
				if (caseDetails.getSpacingRow() != null) {
					caseDetailsDb.setSpacingRow(caseDetails.getSpacingRow());
				}

				if (caseDetails.getSeedTreatment() != null) {
					caseDetailsDb.setSeedTreatment(caseDetails.getSeedTreatment());
				}

				kmlFileDao.save(caseDetails);
			}

			_res = new HashMap<>();
			_res.put("success", "true");
			_res.put("message", "All data have been updated");
		} catch (Exception e) {
			e.printStackTrace();
			_res = new HashMap<>();
			_res.put("success", "false");
			_res.put("message", e.getMessage());
		}

		return _res;
	}

	@Override
	public Map updateStatusAndReason(List<CaseDetails> _caseList) {
		List<CaseDetails> _dataList = new ArrayList<>();
		HashMap<String, Object> _res = null;
		try {
			_dataList.addAll(_caseList);
			for (int i = 0; i < _dataList.size(); i++) {
				logger.info("_caseList =>       " + _caseList);
				this.kmlFileDao.updateStatusAndReason(_dataList.get(i).getDeathReason(), _dataList.get(i).getCaseId());
			}
			_res = new HashMap<>();
			_res.put("status", HttpStatus.OK);
			_res.put("success", "true");
			_res.put("message", "All given cases are marked as Died");
		} catch (Exception e) {
			_res = new HashMap<>();
			_res.put("status", HttpStatus.EXPECTATION_FAILED);
			_res.put("success", "false");
			_res.put("message", e.getCause());
		}
		return _res;
	}

}