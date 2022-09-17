/**
 *
 */
package in.cropdata.cdtmasterdata.website.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.properties.AppProperties;
import in.cropdata.cdtmasterdata.util.CryptoUtils;
import in.cropdata.cdtmasterdata.util.EmailServiceUtil;
import in.cropdata.cdtmasterdata.util.ImageCompressionUtils;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.RlUserDTO;
import in.cropdata.cdtmasterdata.website.model.RlUser;
import in.cropdata.cdtmasterdata.website.model.RlUserAggrement;
import in.cropdata.cdtmasterdata.website.model.RlUserBankDetails;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;
import in.cropdata.cdtmasterdata.website.model.vo.RlUserVo;
import in.cropdata.cdtmasterdata.website.repository.RlUserAggrementRepository;
import in.cropdata.cdtmasterdata.website.repository.RlUserBankDetailsRepository;
import in.cropdata.cdtmasterdata.website.repository.RlUserRepository;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

/**
 * @author vivek-cropdata
 * @author PranaySK
 */

@Service
public class RlUserService {

	private static final Logger log = LoggerFactory.getLogger(RlUserService.class);

	@Autowired
	private RlUserRepository rlUserRepository;

	@Autowired
	private RlUserAggrementRepository rlUserAggrementRepository;

	@Autowired
	private RlUserBankDetailsRepository bankDetailsRepository;

	@Autowired
	private EmailServiceUtil emailServiceUtil;

	@Autowired
	private AppProperties properties;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private ImageCompressionUtils imageCompressionUtils;
	

	public List<RlUserVo> getRlUserList() {
		return rlUserRepository.gtListOfRlUser();
	}

	public List<Integer> listOfPages() {
		try {
			List<Integer> listOfPageNo = new ArrayList<>();
			Integer totalCount = rlUserRepository.totalNoOfPages();

			for (int i = 1; i <= totalCount; i++) {
				listOfPageNo.add(i);
			}

			return listOfPageNo;

		} catch (Exception ex) {
			throw new DoesNotExistException("No Records Found!");
		}
	}

	public Page<RlUserVo> getRlUserListPaginated(Integer page, Integer size, String searchText) {
		try {
			log.info("getting all rl user info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortByIDAsc = PageRequest.of(page, size, Sort.by("ID").ascending());

			return rlUserRepository.getRlUserWithPage(sortByIDAsc, searchText);

		} catch (Exception ex) {
			throw new DoesNotExistException("No User Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage importUserRegistrationExcel(MultipartFile file, String roleName) throws IOException {
		List<RlUser> rlUsersList = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());) {
			XSSFSheet worksheet = workbook.getSheetAt(0);
			log.info("check last row number-----> {}", worksheet.getLastRowNum());
			log.info("check physical row number-----> {}", worksheet.getPhysicalNumberOfRows());
			if (worksheet.getLastRowNum() != 0) {
				for (int index = 1; index <= worksheet.getLastRowNum(); index++) {
					RlUser rlUser = new RlUser();
					Row row = worksheet.getRow(index);
					log.info("total Records is---> {}", row.getCell(1));
					if (row.getCell(0) != null && !Double.toString(row.getCell(0).getNumericCellValue()).equals("")) {
						// convert into int
						int value = (int) row.getCell(0).getNumericCellValue();
						log.info("value -> {}", value);
						int count = this.rlUserRepository.existRegionId(value);
						if (count == 0) {
							return responseMessageUtil.sendResponse(false, "",
									"RegionID" + APIConstants.RESPONSE_DOES_NOT_EXIST + value
											+ " , Please enter valid region ID!");
						}
						rlUser.setRegionId(value);
					} else {
						return responseMessageUtil.sendResponse(false, "",
								"RegionID Field is missing! Please check in row " + index);
					}
					if (row.getCell(1) != null && !row.getCell(1).getStringCellValue().equals("")) {
						rlUser.setName(row.getCell(1).getStringCellValue());
					} else {
						return responseMessageUtil.sendResponse(false, "",
								"Name Field is missing! Please check in row " + index);
					}
					if (row.getCell(2) != null && !row.getCell(2).getStringCellValue().equals("")) {
						Optional<RlUser> foundUser = this.rlUserRepository
								.findByEmail(row.getCell(2).getStringCellValue());
						if (foundUser.isPresent()) {
							return responseMessageUtil.sendResponse(false, "",
									"User" + APIConstants.RESPONSE_ALREADY_EXIST + foundUser.get().getEmail());
						}
						rlUser.setEmail(row.getCell(2).getStringCellValue());
					} else {
						return responseMessageUtil.sendResponse(false, "",
								"Email Field is missing! Please check in row " + index);
					}

					rlUser.setStatus(APIConstants.STATUS_PENDING);
					rlUser.setRoleName(roleName);
					rlUser.setAggrementAccepted("No");

					rlUsersList.add(rlUser);
				}
			} else {
				return responseMessageUtil.sendResponse(false, "", "Cannot upload empty sheet!");
			}

			List<RlUser> rlUsers = this.rlUserRepository.saveAll(rlUsersList);

			for (RlUser savedRlUser : rlUsers) {
				/** Add RL User Agreement */
				this.addRlUserAgreement(savedRlUser);
				/** Add RL User Bank Details */
				this.addRlUserBankDetails(savedRlUser);

				String redirectUrl = properties.getRlUserOnboardUrl()
						+ CryptoUtils.encrypt(Integer.toString(savedRlUser.getId()));
				String mailResponse = this.emailServiceUtil.sendMail(savedRlUser.getEmail(), savedRlUser.getName(),
						redirectUrl);
				log.info("mail response for user -> {}", mailResponse);
			}

			return responseMessageUtil.sendResponse(true, roleName + " data imported successfully", "");

		} catch (Exception ex) {
			throw new FileNotFoundException("Cannot upload sheet! " + ex.getMessage());
		}
	}

	public ResponseEntity<Resource> getRLDataInExcelFile(Integer pageNo) {
		Pageable sortedByIdDesc = PageRequest.of(pageNo - 1, 200, Sort.by("id").descending());
		Page<RlUserVo> rlUserDetails = rlUserRepository.getRlUserDataToExport(sortedByIdDesc);
//		String filename = "RL-Employee-Data.xlsx";
		/** Getting excel data */
		InputStreamResource file = new InputStreamResource(this.exportData(rlUserDetails));
		/** Return response */
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(file);
	}

	private ByteArrayInputStream exportData(Page<RlUserVo> rlUserDetails) {
		log.info("generating excel file for rl user info...");
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			final String[] headers = { "UserId", "Region Name", "RoleName", "Name", "Email", "MobileNumber", "AadharNo",
					"PanNo", "UserImageUrl", "AadharImageUrl", "PanImageUrl", "DrivingLicenseImageUrl", "BankName",
					"AccountNumber", "IFSCCode", "BankImageUrl", "AggrementAccepted", "Status" };

			Sheet sheet = workbook.createSheet("RL-Employee-Data");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			// Header
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < headers.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(headers[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (RlUserVo rlUserVo : rlUserDetails) {
				Row row = sheet.createRow(rowIdx++);
				int columnCount = 0;
				row.createCell(columnCount++).setCellValue(rlUserVo.getId());
				row.createCell(columnCount++).setCellValue(rlUserVo.getRegionName());
				row.createCell(columnCount++).setCellValue(rlUserVo.getRoleName());
				row.createCell(columnCount++).setCellValue(rlUserVo.getName());
				row.createCell(columnCount++).setCellValue(rlUserVo.getEmail());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getMobileNumber() == null ? "" : rlUserVo.getMobileNumber());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getAadharNo() == null ? "" : rlUserVo.getAadharNo());
				row.createCell(columnCount++).setCellValue(rlUserVo.getPanNo() == null ? "" : rlUserVo.getPanNo());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getUserImageUrl() == null ? "" : rlUserVo.getUserImageUrl());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getAadharImageUrl() == null ? "" : rlUserVo.getAadharImageUrl());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getPanImageUrl() == null ? "" : rlUserVo.getPanImageUrl());
				row.createCell(columnCount++).setCellValue(
						rlUserVo.getDrivingLicenseImageUrl() == null ? "" : rlUserVo.getDrivingLicenseImageUrl());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getBankName() == null ? "" : rlUserVo.getBankName());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getAccountNumber() == null ? "" : rlUserVo.getAccountNumber());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getIFSCCode() == null ? "" : rlUserVo.getIFSCCode());
				row.createCell(columnCount++)
						.setCellValue(rlUserVo.getBankImageUrl() == null ? "" : rlUserVo.getBankImageUrl());
				row.createCell(columnCount++).setCellValue(rlUserVo.getAggrementAccepted());
				row.createCell(columnCount++).setCellValue(rlUserVo.getStatus());
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException ex) {
			throw new InvalidDataException("Failed to export data to Excel file: " + ex.getMessage());
		}
	}

	public ResponseMessage updateSelectedRlUser(Integer userId, RlUserDTO userDTO) throws IOException {
		try {
			Optional<RlUser> foundRlUser = this.rlUserRepository.findById(userId);
			if (foundRlUser.isPresent()) {
				ObjectMapper objectMapper = new ObjectMapper();
				RlUser rlUser = objectMapper.readValue(userDTO.getData(), RlUser.class);
				rlUser.setId(foundRlUser.get().getId());
				/** Check duplicate Aadhaar number */
				this.checkAadhaarExistence(rlUser.getAadharNo());
				/** Updating Profile Image */
				if (userDTO.getUserProfileImage() != null) {
					MultipartFile userProfileImage = imageCompressionUtils
							.compressProfileImage(userDTO.getUserProfileImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userProfileImage, "portal-users",
							"profile-images");
					rlUser.setUserImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl()
							: foundRlUser.get().getUserImageUrl());
				} else {
					rlUser.setUserImageUrl(foundRlUser.get().getUserImageUrl());
				}
				String pathKey = "portal-user-docs";
				String dirPath = "id-cards";
				/** Updating Aadhaar Image */
				if (userDTO.getUserAadharImage() != null) {
					MultipartFile userAadharImage = imageCompressionUtils
							.compressDocumentImage(userDTO.getUserAadharImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userAadharImage, pathKey, dirPath);
					rlUser.setAadharImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl()
							: foundRlUser.get().getAadharImageUrl());
				} else {
					rlUser.setAadharImageUrl(foundRlUser.get().getAadharImageUrl());
				}
				/** Updating PAN card Image */
				if (userDTO.getUserPanImage() != null) {
					MultipartFile userPanImage = imageCompressionUtils.compressDocumentImage(userDTO.getUserPanImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userPanImage, pathKey, dirPath);
					rlUser.setPanImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl()
							: foundRlUser.get().getPanImageUrl());
				} else {
					rlUser.setPanImageUrl(foundRlUser.get().getPanImageUrl());
				}
				/** Updating DL Image */
				if (userDTO.getUserDLImage() != null) {
					MultipartFile userDLImage = imageCompressionUtils.compressDocumentImage(userDTO.getUserDLImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userDLImage, pathKey, dirPath);
					rlUser.setDrivingLicenceUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl()
							: foundRlUser.get().getDrivingLicenceUrl());
				} else {
					rlUser.setDrivingLicenceUrl(foundRlUser.get().getDrivingLicenceUrl());
				}

				/** Updating user details */
				RlUser savedRlUser = this.rlUserRepository.save(rlUser);
				/** Updating user agreement details */
				Optional<RlUserAggrement> foundAgreement = rlUserAggrementRepository
						.findByRlUserId(savedRlUser.getId());
				if (foundAgreement.isPresent()) {
					RlUserAggrement agreement = new RlUserAggrement();
					agreement.setId(foundAgreement.get().getId());
					agreement.setRlUserId(savedRlUser.getId());
					agreement.setAggrementDocumentId(this.getAgreementDocsId(savedRlUser.getRoleName()));
					agreement.setStatus(savedRlUser.getStatus());
					this.rlUserAggrementRepository.save(agreement);
				}
				/** Updating user bank details */
				this.updateBankDetails(savedRlUser.getId(), userDTO, pathKey, dirPath);

				/** Sending mail for user with status 'Pending' */
				if (APIConstants.STATUS_PENDING.equals(savedRlUser.getStatus())) {
					String redirectUrl = properties.getRlUserOnboardUrl()
							+ CryptoUtils.encrypt(Integer.toString(savedRlUser.getId()));
					String mailResponse = this.emailServiceUtil.sendMail(savedRlUser.getEmail(), savedRlUser.getName(),
							redirectUrl);
					log.info("mail response -> {}", mailResponse);
				}

				return responseMessageUtil.sendResponse(true,
						"User" + APIConstants.RESPONSE_UPDATE_SUCCESS + rlUser.getId(), "");
			} else
				return responseMessageUtil.sendResponse(false, "",
						"User Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + userId);
		} catch (AlreadyExistException ex) {
			return responseMessageUtil.sendResponse(false, "", ex.getMessage());
		} catch (DataIntegrityViolationException ex) {
			String value = ex.getRootCause().getMessage().substring(ex.getRootCause().getMessage().indexOf("'") + 1);
			return responseMessageUtil.sendResponse(false, "",
					"User already exists with email : " + value.substring(0, value.indexOf("'")));
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", "Something went wrong - " + ex.getMessage());
		}
	}

	public RlUserVo getRlUserDetail(Integer userId) {
		RlUserVo userInfo = this.rlUserRepository.getRlUserInfo(userId);
		if (!Objects.isNull(userInfo)) {
			log.info("User found with userId -> {}", userId);
			return userInfo;
		} else
			throw new DoesNotExistException("User Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + userId);
	}

	public ResponseMessage addRlUser(RlUser user) {
		try {
			Optional<RlUser> foundUser = this.rlUserRepository.findByEmail(user.getEmail());
			if (!foundUser.isPresent()) {
				log.info("Email Not Registered -> {} Saving the user details!", user.getEmail());
				/** Saving user data */
				user.setStatus(APIConstants.STATUS_PENDING);
				RlUser savedRlUser = this.rlUserRepository.save(user);
				/** Saving RL User Agreement */
				this.addRlUserAgreement(savedRlUser);
				/** Saving RL User Bank Details */
				this.addRlUserBankDetails(savedRlUser);

				String redirectUrl = properties.getRlUserOnboardUrl()
						+ CryptoUtils.encrypt(Integer.toString(savedRlUser.getId()));
				String mailResponse = this.emailServiceUtil.sendMail(savedRlUser.getEmail(), savedRlUser.getName(),
						redirectUrl);
				log.info("mail response -> {}", mailResponse);

				return responseMessageUtil.sendResponse(true, "User" + APIConstants.RESPONSE_ADD_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"User" + APIConstants.RESPONSE_ALREADY_EXIST + user.getEmail());
			}
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", "Something went wrong - " + ex.getMessage());
		}
	}

	private void addRlUserAgreement(RlUser savedRlUser) {
		Integer rlUserId = savedRlUser.getId();
		RlUserAggrement agreement = new RlUserAggrement();
		agreement.setAggrementDocumentId(this.getAgreementDocsId(savedRlUser.getRoleName()));
		agreement.setRlUserId(rlUserId);
		agreement.setStatus(APIConstants.STATUS_PENDING);
		this.rlUserAggrementRepository.save(agreement);
		log.info("User Agreement added for -> {}", rlUserId);
	}

	private void addRlUserBankDetails(RlUser savedRlUser) {
		Integer rlUserId = savedRlUser.getId();
		RlUserBankDetails userBankDetails = new RlUserBankDetails();
		userBankDetails.setRlUserId(rlUserId);
		this.bankDetailsRepository.save(userBankDetails);
		log.info("User Bank details entry added for -> {}", rlUserId);
	}

	private void updateBankDetails(final Integer rlUserId, final RlUserDTO userDTO, final String pathKey,
			final String dirPath) throws IOException {
		Optional<RlUserBankDetails> foundBankDetails = bankDetailsRepository.findByRlUserId(rlUserId);
		if (foundBankDetails.isPresent()) {
			ObjectMapper objectMapper = new ObjectMapper();
			RlUserBankDetails bankDetails = objectMapper.readValue(userDTO.getBankDetails(), RlUserBankDetails.class);
			bankDetails.setId(foundBankDetails.get().getId());
			bankDetails.setRlUserId(rlUserId);
			/** Updating Bank Account Image */
			if (userDTO.getAccountImage() != null) {
				MultipartFile accountImage = imageCompressionUtils.compressDocumentImage(userDTO.getAccountImage());
				FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(accountImage, pathKey, dirPath);
				bankDetails.setBankAccountImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl()
						: foundBankDetails.get().getBankAccountImageUrl());
			} else {
				bankDetails.setBankAccountImageUrl(foundBankDetails.get().getBankAccountImageUrl());
			}
			this.bankDetailsRepository.save(bankDetails);
			log.info("User Bank details updated for -> {}", rlUserId);
		}
	}

	private void checkAadhaarExistence(final String aadharNo) {
		
		if (!"".equals(aadharNo) && this.rlUserRepository.checkAadharNo(aadharNo) > 1) {
			log.info("User already exists for -> {}", aadharNo);
			throw new AlreadyExistException("User " + APIConstants.RESPONSE_ALREADY_EXIST + aadharNo);
		}
	}

	private Integer getAgreementDocsId(String rlUserRole) {
		if ("FLS".equals(rlUserRole)) {
			return 1;
		} else if ("MLS".equals(rlUserRole)) {
			return 3;
		} else if ("MLT".equals(rlUserRole)) {
			return 4;
		} else if ("PRS".equals(rlUserRole)) {
			return 2;
		}
		return 1;
	}

	private FileUploadResponseDTO uploadUserFile(MultipartFile file, final String pathKey, final String dirPath)
			throws IOException {
		final String[] SUPPORTED_EXTN = { ".jpg", ".png", ".jpeg" };
		String imageFileName = file.getOriginalFilename();
		FileUploadResponseDTO fileUploadResponse = null;
		String fileExtension = Objects.requireNonNull(imageFileName);
		log.info("Filename -> {}", imageFileName);
		if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension::contains)) {
			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			if (pathKeyResponse != null) {
				boolean result = (boolean) pathKeyResponse.get("exist");
				log.info("result -> {}", result);
				if (result) {
					log.info("pathkey exists, upload the file...");
					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, imageFileName, true, file,
							null);
				} else {
					log.info("pathkey does not exist, create directory and then upload the file...");
					String moduleName = "cropdata-portal";
					DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName, pathKey,
							dirPath, null);
					if (createDirResp != null && createDirResp.isSuccess()) {
						fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, imageFileName, true, file,
								null);
					}
				}
			}
		} else {
			throw new NoSuchFileException("File Format Not Supported!");
		}

		return fileUploadResponse;
	}

	public List<GeoRegionDto> getAllGeoRegion() {
		try {
			List<GeoRegionDto> list = rlUserRepository.getGeoRegionList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
