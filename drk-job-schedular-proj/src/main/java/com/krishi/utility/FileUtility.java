package com.krishi.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.krishi.properties.AppProperties;
//import in.cropdata.toolsuite.sdk.FileManagerUtil;
//import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.mock.web.MockMultipartFile;

import com.krishi.model.DataInsertionModel;
import com.krishi.repository.AlliedActivityRepository;
import com.krishi.repository.BankBranchRepository;
import com.krishi.repository.BankRepository;
import com.krishi.repository.LhWarehouseRepository;
import com.krishi.repository.PackagingRepository;
import com.krishi.repository.RegionRepository;
import com.krishi.repository.PhenophaseDurationRepository;
import com.krishi.repository.StateRepository;
import org.springframework.web.multipart.MultipartFile;

/**
 * Utility for handling file operations along with Azure Blob Storage.
 * 
 * @author CDT - Pranay
 */

@Component
public class FileUtility {

	private static final Logger LOGGER = LogManager.getLogger(FileUtility.class);

//	@Value("${azure.storage.environment}")
	private String env = "demo";

//	@Value("${path.file.bank}")
	private String bankZipPath = "path.file.bank";

//	@Value("${path.file.branch}")
	private String branchZipPath = "path.file.branch";

//	@Value("${path.file.activity}")
	private String activityZipPath = "path.file.activity";

//	@Value("${path.file.lhwarehouse}")
	private String lhwarehousePath = "path.file.lhwarehouse";

//	@Value("${path.file.packaging}")
	private String packagingPath = "path.file.packaging";

//	@Value("${path.file.phenophase_duration}")
	private String phenophaseDurationPath = "path.file.phenophase_duration ";

	@Autowired
	private BankBranchRepository branchRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AlliedActivityRepository activityRepository;

	@Autowired
	private RegionRepository regionRepository;

//	@Autowired
//	private FileManagerUtil fileManager;

//	@Autowired
//	private BlobContainerClient containerClient;

	@Autowired
	private LhWarehouseRepository lhWarehouseRepository;

	@Autowired
	private PackagingRepository packagingRepository;

	@Autowired
	private PhenophaseDurationRepository phenophaseDurationRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private AppProperties appProperties;

	public String uploadFileToBlob(String fileToUploadName, File fileToUpload, String dirPath) {
//		BlobClient blobClient = null;
		try {
//			String fileName = env + File.separator + "drk" + File.separator + dirPath + File.separator
//					+ fileToUploadName;
//			LOGGER.info("Uploading file -> {} ", fileName);
//			blobClient = containerClient.getBlobClient(fileName);
//			blobClient.uploadFromFile(fileToUpload.getAbsolutePath(), true);
//
//			LOGGER.info("uploaded file public URL is -> {}", blobClient.getBlobUrl());
//			return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);

			return null;

		} catch (Exception ex) {
			LOGGER.error("File upload failed -> {}", fileToUploadName, ex);
		}

		return "";
	}

	/*
	 * public String uploadKmlFileToBlob(String fileToUploadName, File fileToUpload,
	 * String dirPath) { BlobClient blobClient = null; try { String fileName = env +
	 * File.separator + "drk" + File.separator + dirPath + File.separator +
	 * fileToUploadName; LOGGER.info("Uploading file -> {} " + fileName); blobClient
	 * = containerClient.getBlobClient(fileName);
	 * blobClient.uploadFromFile(fileToUpload.getAbsolutePath(), true);
	 * 
	 * return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);
	 * 
	 * } catch (Exception ex) { LOGGER.error("File upload failed -> {}",
	 * fileToUploadName, ex); }
	 * 
	 * return ""; }
	 */

	public void createAndUploadZipFile(String zipFor) {
		try {
			String sqlInsert = "";
			switch (zipFor) {
			case "bank":
				List<DataInsertionModel> bankDataModels = bankRepository.getBankData();
				sqlInsert = "INSERT OR REPLACE INTO `bank` (`id`, `name`) VALUES ";
				if (!bankDataModels.isEmpty()) {
					for (DataInsertionModel data : bankDataModels) {
						sqlInsert += "( " + data.getId() + ", '" + data.getName().replaceAll("\\'", "") + "' ), ";
					}
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
				}
				File bankFile = this.writeStatementsToFile(this.sanitizePath(bankZipPath), sqlInsert, "bank");
				File bankZip = this.prepareZipFile(bankFile, this.sanitizePath(bankZipPath) + "_sql.zip");
				this.uploadFileToBlob(bankZip.getName(), bankZip, "bank");
				break;
			case "branch":
				List<Integer> regionIdList = regionRepository.getRegionIds();
				for (Integer regionId : regionIdList) {
					List<DataInsertionModel> branchDataModels = branchRepository.getBankBranchData(regionId);
					sqlInsert = "INSERT OR REPLACE INTO `bank_branch` (`id`, `bank_id`, `name`, `ifsc`) VALUES ";
					if (!branchDataModels.isEmpty()) {
						for (DataInsertionModel data : branchDataModels) {
							sqlInsert += "( " + data.getId() + ", " + data.getBankId() + ", '"
									+ data.getName().replaceAll("\\'", "") + "', '" + data.getIfsc() + "' ), ";
						}
						sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
					}
					File branchFile = this.writeStatementsToFile(this.sanitizePath(branchZipPath), sqlInsert,
							"branch" + regionId);
					File branchZip = this.prepareZipFile(branchFile,
							this.sanitizePath(branchZipPath) + regionId + "_sql.zip");
					this.uploadFileToBlob(branchZip.getName(), branchZip, "bank_branch");
				}
				break;
			case "activity":
				List<DataInsertionModel> activityDataModels = activityRepository.getAlliedActivityData();
				sqlInsert = "INSERT OR REPLACE INTO `allied_activity` (`id`, `name`) VALUES ";
				if (!activityDataModels.isEmpty()) {
					for (DataInsertionModel data : activityDataModels) {
						sqlInsert += "( " + data.getId() + ", '" + data.getName().replaceAll("\\'", "") + "' ), ";
					}
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
				}
				File activityFile = this.writeStatementsToFile(this.sanitizePath(activityZipPath), sqlInsert,
						"activity");
				File activityZip = this.prepareZipFile(activityFile, this.sanitizePath(activityZipPath) + "_sql.zip");
				this.uploadFileToBlob(activityZip.getName(), activityZip, "allied_activity");
				break;

			/** added new table data in zip format- CDT-Ujwal -Start */
			case "lhWarehouse":
				List<Integer> regionIlist = lhWarehouseRepository.regionList();
				for (Integer regionId : regionIlist) {
					List<DataInsertionModel> branchDataModels = lhWarehouseRepository.LhWarehouseList(regionId);
					sqlInsert = "INSERT OR REPLACE INTO `logistic_hub` (`id`, `name`, `address`, `state_code`, `district_code`) VALUES ";
					if (!branchDataModels.isEmpty()) {
						for (DataInsertionModel data : branchDataModels) {
							sqlInsert += "( " + data.getId() + ", '" + data.getName() + "', '"
									+ data.getAddress().replaceAll("\\'", "") + "', " + data.getStateCode() + ","
									+ data.getDistrictCode() + " ), ";
						}
						sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
					}
					File lhWarehouseFile = this.writeStatementsToFile(this.sanitizePath(lhwarehousePath), sqlInsert,
							"region" + regionId);
					File lhWarehouseZip = this.prepareZipFile(lhWarehouseFile,
							this.sanitizePath(lhwarehousePath) + regionId + "_sql.zip");
					this.uploadFileToBlob(lhWarehouseZip.getName(), lhWarehouseZip, "lh_warehouse");
				}
				break;
			case "packaging":
				List<DataInsertionModel> packagingModels = packagingRepository.getPackagingData();
				sqlInsert = "INSERT OR REPLACE INTO `general_packaging_type` (`id`, `name`) VALUES ";
				if (!packagingModels.isEmpty()) {
					for (DataInsertionModel data : packagingModels) {
						sqlInsert += "( " + data.getId() + ", '" + data.getName().replaceAll("\\'", "") + "' ), ";
					}
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
				}
				File packagingFile = this.writeStatementsToFile(this.sanitizePath(packagingPath), sqlInsert,
						"packaging");
				File packagingZip = this.prepareZipFile(packagingFile, this.sanitizePath(packagingPath) + "_sql.zip");
				this.uploadFileToBlob(packagingZip.getName(), packagingZip, "packaging");
				break;

				/** phenophase duration table into zip file for android sync - Rinkesh KM*/
				case "phenophaseDuration":
				List<Integer> stateIds = stateRepository.getStateIds();
				for (Integer stateId : stateIds) {

					List<DataInsertionModel> phenophaseDurationModel = phenophaseDurationRepository.getPhenophaseDurationRepository(stateId);
					sqlInsert = "INSERT OR REPLACE INTO `phenophase_duration` (`id`, `state_id`, `acz_id`, `phenophase_id` ,`commodity_id`, `variety_id`, `start_das` ,`end_das`, `sowing_start_week`, `sowing_end_week`, `harvest_start_week`, `harvest_end_week`, `status`) VALUES";
					if (!phenophaseDurationModel.isEmpty()) {
						for (DataInsertionModel data : phenophaseDurationModel) {
							sqlInsert += "( " + data.getId() + ", " + data.getStateCode() + ", " + data.getAczId() + ", " + data.getPhenophaseId() + ", " + data.getCommodityId() + ", " + data.getVarietyId() + ", " + data.getStartDas() + ", " + data.getEndDas() + ", " + data.getSowingStartWeek() + ", " + data.getSowingEndWeek() + ", " + data.getHarvestStartWeek() + ", " + data.getHarvestEndWeek() + ", " + (data.getStatus() == true ? 1 : 0) + " ), ";
						}
						sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 2) + " ;";
					}
					File phenophaseDurationFile = this.writeStatementsToFile(this.sanitizePath(phenophaseDurationPath), sqlInsert,
							"phenophase_duration");
					File phenophaseDurationZip = this.prepareZipFile(phenophaseDurationFile, this.sanitizePath(phenophaseDurationPath) + stateId +"_sql.zip");
					this.uploadFileToBlob(phenophaseDurationZip.getName(), phenophaseDurationZip, "phenophase_duration");
				}
			break;

			default:
				LOGGER.info("Invalid resource requested!");
				break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("File creation failed -> {}", zipFor, ex);
		}
		/** added new table data in zip format- CDT-Ujwal -Start */
	}

	private File writeStatementsToFile(String filePath, String sqlInsert, String resource)
			throws FileNotFoundException, IOException {
		File tempFile = new File(filePath + File.separator + resource + "data.sql");
		this.checkAndDeleteExistingFile(tempFile);
		try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
			fileOutputStream.write(sqlInsert.getBytes(StandardCharsets.UTF_8));
		}
		return tempFile;
	}

	private File prepareZipFile(File fileToZip, String zipFilePath) {
		try {
			this.checkAndDeleteExistingFile(new File(zipFilePath));
			Path zipPath = Files.createFile(Paths.get(zipFilePath));
			try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipPath));
					FileInputStream fis = new FileInputStream(fileToZip);) {
				ZipEntry ze = new ZipEntry(fileToZip.getName());
				zs.putNextEntry(ze);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zs.write(buffer, 0, len);
				}
			}

			return zipPath.toFile();

		} catch (Exception ex) {
			LOGGER.error("Error while creating zip file -> {}", ex.getMessage(), ex);
		}
		return null;
	}

	private String sanitizePath(String path) {
		return path.endsWith(File.separator) ? path : path.concat(File.separator);
	}

	public void checkAndDeleteExistingFile(File existingFile) {
		if (existingFile.exists()) {
			if (!existingFile.delete()) {
				existingFile.deleteOnExit();
			}
		}
	}

	public static void checkDirExists(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
			LOGGER.info("Directory created for -> {}", dirPath);
		}
	}

	/**
	 *upload farmer json file in ABS
	 * */
/*	public String uploadFileToBlob(String fileToUploadName, File fileToUpload) {
//		BlobClient blobClient = null;
		String contentType = "text/plain";
		byte[] content = null;
		try {
//			String fileName = appProperties.getAbsEnv()  +File.separator +"drkrishi" +File.separator + "media" + File.separator + fileToUploadName + File.separator + fileToUpload.getName();
//			LOGGER.info("Uploading file -> {} ", fileName);
//			blobClient = containerClient.getBlobClient(fileName);
//			blobClient.uploadFromFile(fileToUpload.getAbsolutePath(), true);
//
//			LOGGER.info("uploaded file public URL is -> {}", blobClient.getBlobUrl());
//			return URLDecoder.decode(blobClient.getBlobUrl(), StandardCharsets.UTF_8);
			Path path = Paths.get(fileToUpload.getAbsolutePath());

			content = Files.readAllBytes(path);
			MultipartFile mltFile = new MockMultipartFile(fileToUpload.getAbsolutePath(), fileToUpload.getName(), contentType,
					content);

			FileUploadResponseDTO uploadResponse = null;
			uploadResponse = fileManager.uploadFile(fileToUploadName, false, fileToUpload.getName(), true, mltFile, null);
			System.out.println("FileUploadResp: " + uploadResponse.getPublicUrl());
			*//*TODO: temporary disable -- 14-05*//*

			return uploadResponse.getPublicUrl();
//			return null;
		} catch (Exception ex) {
			LOGGER.error("File upload failed -> {}", fileToUploadName, ex);
			return null;
		}

	}*/
}
