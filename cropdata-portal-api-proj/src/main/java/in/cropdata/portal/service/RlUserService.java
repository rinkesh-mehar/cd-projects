/**
 * 
 */
package in.cropdata.portal.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.dto.RlUserInfoDTO;
import in.cropdata.portal.exceptions.AlreadyExistException;
import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.RlUser;
import in.cropdata.portal.model.RlUserAggrement;
import in.cropdata.portal.model.RlUserBankDetails;
import in.cropdata.portal.repository.RlUserAggrementRepository;
import in.cropdata.portal.repository.RlUserBankDetailsRepository;
import in.cropdata.portal.repository.RlUserRepository;
import in.cropdata.portal.util.CryptoUtils;
import in.cropdata.portal.util.ImageCompressionUtils;
import in.cropdata.portal.util.ResponseMessageUtil;
import in.cropdata.portal.vo.RlUserInfoVO;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

/**
 * This Service is used for managing the various operations for RL Users.
 * 
 * @since 1.0
 * @author Vivek Gajbhiye
 * @author PranaySK
 * @Date 02-09-2020
 */

@Service
public class RlUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RlUserService.class);

	@Autowired
	private RlUserRepository rlUserRepository;

	@Autowired
	private RlUserAggrementRepository rlUserAggrementRepository;

	@Autowired
	private RlUserBankDetailsRepository bankDetailsRepository;

	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private ImageCompressionUtils imageCompressionUtils;

	public List<RlUser> getRlUserList() {
		return this.rlUserRepository.findAll();
	}

	public RlUserInfoVO getRlUserInfoById(String inputUserId) {
		LOGGER.info("Decrypting Rl inputUserId -> {}", inputUserId);
		Integer userId = Integer.valueOf(CryptoUtils.decrypt(inputUserId));
		LOGGER.info("Rl userId -> {}", userId);
		if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_REG_PENDING) > 0) {
			LOGGER.info("Checking Rl User with Status as Pending...");
			Optional<RlUser> dbUser = rlUserRepository.findByIdAndStatus(userId, APIConstants.USER_REG_PENDING);
			Optional<RlUserAggrement> dbRlUserAggrement = rlUserAggrementRepository.findByRlUserIdAndStatus(userId,
					APIConstants.USER_REG_PENDING);
			if (dbUser.isPresent() && dbRlUserAggrement.isPresent()) {
				LOGGER.info("Updating the user status as Working...");
				/** Updating User status */
				RlUser rlUser = new RlUser();
				BeanUtils.copyProperties(dbUser.get(), rlUser);
				rlUser.setId(userId);
				rlUser.setRefreshCount(0);
				rlUser.setStatus(APIConstants.USER_WORKING);
				rlUserRepository.save(rlUser);
				/** Updating User Agreement status */
				RlUserAggrement userAgreement = new RlUserAggrement();
				BeanUtils.copyProperties(dbRlUserAggrement.get(), userAgreement);
				userAgreement.setRlUserId(userId);
				userAgreement.setStatus(APIConstants.USER_WORKING);
				rlUserAggrementRepository.save(userAgreement);
			}
			return this.rlUserRepository.getUserInfoByUserIdAndStatus(userId, APIConstants.USER_WORKING);

		} else if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_WORKING) > 0) {
			LOGGER.info("Checking Rl User with Status as Working...");
			Optional<RlUser> dbUser = rlUserRepository.findByIdAndStatus(userId, APIConstants.USER_WORKING);
			Optional<RlUserAggrement> dbRlUserAggrement = rlUserAggrementRepository.findByRlUserIdAndStatus(userId,
					APIConstants.USER_WORKING);
			if (dbUser.isPresent() && dbRlUserAggrement.isPresent()) {
				LOGGER.info("Expiring the user status...");
				if(dbUser.get().getRefreshCount() < 3) {
					/** Updating page refresh count */
					RlUser rlUser = new RlUser();
					BeanUtils.copyProperties(dbUser.get(), rlUser);
					rlUser.setRefreshCount(dbUser.get().getRefreshCount() + 1);
					rlUserRepository.save(rlUser);
					return this.rlUserRepository.getUserInfoByUserIdAndStatus(userId, APIConstants.USER_WORKING);
				}else {
					/** Updating User status */
					RlUser rlUser = new RlUser();
					BeanUtils.copyProperties(dbUser.get(), rlUser);
					rlUser.setId(userId);
					rlUser.setStatus(APIConstants.USER_EXPIRED);
					rlUserRepository.save(rlUser);
					/** Updating User Agreement status */
					RlUserAggrement userAgreement = new RlUserAggrement();
					BeanUtils.copyProperties(dbRlUserAggrement.get(), userAgreement);
					userAgreement.setRlUserId(userId);
					userAgreement.setStatus(APIConstants.USER_EXPIRED);
					rlUserAggrementRepository.save(userAgreement);
				}
			}
			return this.rlUserRepository.getUserInfoByUserIdAndStatus(userId, APIConstants.USER_EXPIRED);

		} else if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_EXPIRED) > 0) {
			throw new InvalidDataException("The link you are trying to reach is expired!");

		} else if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_REG_COMPLETED) > 0) {
			throw new InvalidDataException("User registration is already completed!");

		} else {
			throw new DoesNotExistException("Requested User Detail Does Not Exist!");
		}
	}

	public ResponseMessage registerRlUser(RlUserInfoDTO rlUserInfoDTO) {
		LOGGER.info("RL User Info -> {} {}", rlUserInfoDTO.getFirstName(), rlUserInfoDTO.getLastName());
		try {
			Integer userId = Integer.valueOf(CryptoUtils.decrypt(rlUserInfoDTO.getUserId()));
			LOGGER.info("Getting User Info for -> {}", userId);
			if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_REG_COMPLETED) > 0) {
				LOGGER.info("User registration is completed for user id -> {}", userId);
				return responseMessageUtil.sendResponse(false, "",
						"User registration is already completed for user id: " + userId);

			} else if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_EXPIRED) > 0) {
				LOGGER.info("User link expired for user id -> {}", userId);
				return responseMessageUtil.sendResponse(false, "",
						"Registration link is expired for user id: " + userId);

			} else if (this.rlUserRepository.checkUserExists(userId, APIConstants.USER_WORKING) > 0) {
				return this.registerUser(userId, rlUserInfoDTO);
			}

			LOGGER.info("User does not exist with id -> {}", userId);
			return responseMessageUtil.sendResponse(false, "", "User" + APIConstants.RESPONSE_DOES_NOT_EXIST + userId);

		} catch (Exception ex) {
			LOGGER.error("Something went wrong -> ", ex);
			return responseMessageUtil.sendResponse(false, "", "Something went wrong: " + ex.getMessage());
		}
	}

	private ResponseMessage registerUser(final Integer userId, final RlUserInfoDTO rlUserInfoDTO) {
		try {
			Optional<RlUser> dbUser = rlUserRepository.findByIdAndStatus(userId, APIConstants.USER_WORKING);
			Optional<RlUserAggrement> dbRlUserAggrement = rlUserAggrementRepository.findByRlUserIdAndStatus(userId,
					APIConstants.USER_WORKING);
			if (dbUser.isPresent() && dbRlUserAggrement.isPresent()) {
				LOGGER.info("Setting User Info...");
				RlUser rlUser = new RlUser();
				BeanUtils.copyProperties(dbUser.get(), rlUser);
				rlUser.setId(userId);
				rlUser.setName(rlUserInfoDTO.getFirstName() + " " + rlUserInfoDTO.getLastName());
				rlUser.setMobileNumber(rlUserInfoDTO.getMobileNo());
				rlUser.setAddharNo(rlUserInfoDTO.getAadharNo());
				rlUser.setPan(rlUserInfoDTO.getPanNo());
				rlUser.setAggrementAccepted(rlUserInfoDTO.getAgreementAccepted());
				/** Check duplicate Email */
				this.checkEmailExistence(rlUser.getEmail());
				/** Check duplicate Aadhaar number */
				this.checkAadhaarExistence(rlUser.getAddharNo());

				LOGGER.info("Uploading User files...");
				String idImageDirName = "cropdata-portal/id-cards";
				String idCardsPathKey = "portal-users-docs";
				if (!(rlUserInfoDTO.getProfileImage().isEmpty() && rlUserInfoDTO.getAadharImage().isEmpty())) {
					/** Uploading Profile Image */
					MultipartFile userProfileImage = imageCompressionUtils
							.compressProfileImage(rlUserInfoDTO.getProfileImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userProfileImage, "portal-users",
							"cropdata-portal/profile-images");
					rlUser.setUserImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl() : null);
					/** Uploading Aadhaar Image */
					MultipartFile userAadharImage = imageCompressionUtils
							.compressDocumentImage(rlUserInfoDTO.getAadharImage());
					uploadResponseDTO = this.uploadUserFile(userAadharImage, idCardsPathKey, idImageDirName);
					rlUser.setAddharImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl() : null);

				} else {
					throw new InvalidDataException("Profile Image or Aadhaar Image can not be empty!");
				}

				if (!rlUserInfoDTO.getPanCardImage().isEmpty()) {
					/** Uploading Pan Card Image */
					MultipartFile userPanImage = imageCompressionUtils
							.compressDocumentImage(rlUserInfoDTO.getPanCardImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userPanImage, idCardsPathKey,
							idImageDirName);
					rlUser.setPanImageUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl() : null);
				}

				if (!rlUserInfoDTO.getDlImage().isEmpty()) {
					/** Uploading Driving License Image */
					MultipartFile userDLImage = imageCompressionUtils.compressDocumentImage(rlUserInfoDTO.getDlImage());
					FileUploadResponseDTO uploadResponseDTO = this.uploadUserFile(userDLImage, idCardsPathKey,
							idImageDirName);
					rlUser.setDrivingLicenceUrl(uploadResponseDTO != null ? uploadResponseDTO.getPublicUrl() : null);
				}

				rlUser.setStatus(APIConstants.USER_REG_COMPLETED);
				rlUserRepository.save(rlUser);
				/** Updating User Agreement data */
				RlUserAggrement userAgreement = new RlUserAggrement();
				BeanUtils.copyProperties(dbRlUserAggrement.get(), userAgreement);
				userAgreement.setRlUserId(userId);
				userAgreement.setStatus(APIConstants.USER_REG_COMPLETED);

				rlUserAggrementRepository.save(userAgreement);
				/** Updating User Bank details */
				this.updateBankDetails(userId, rlUserInfoDTO, idCardsPathKey, idImageDirName);

				LOGGER.info("User registration completed. Returning response...");
				return responseMessageUtil.sendResponse(true, "User Registration Completed Successfully!", "");

			} else {
				LOGGER.info("User does not exist with id -> {}", userId);
				return responseMessageUtil.sendResponse(false, "",
						"User" + APIConstants.RESPONSE_DOES_NOT_EXIST + CryptoUtils.encrypt(Integer.toString(userId)));
			}
		} catch (AlreadyExistException ex) {
			return responseMessageUtil.sendResponse(false, "", ex.getMessage());
		} catch (DataIntegrityViolationException ex) {
			String value = ex.getRootCause().getMessage().substring(ex.getRootCause().getMessage().indexOf("'") + 1);
			return responseMessageUtil.sendResponse(false, "",
					"User " + APIConstants.RESPONSE_ALREADY_EXIST + value.substring(0, value.indexOf("'")));
		} catch (Exception ex) {
			LOGGER.error("Something went wrong -> ", ex);
			return responseMessageUtil.sendResponse(false, "", "Registration failed! " + ex.getMessage());
		}
	}

	private void checkEmailExistence(final String email) {
		if (this.rlUserRepository.checkEmail(email) > 1) {
			LOGGER.info("User already exists for -> {}", email);
			throw new AlreadyExistException("User " + APIConstants.RESPONSE_ALREADY_EXIST + email);
		}
	}

	private void checkAadhaarExistence(final String aadharNo) {
		System.out.println("Count : " + this.rlUserRepository.checkAadharNo(aadharNo));
		if (this.rlUserRepository.checkAadharNo(aadharNo) > 0) {
			LOGGER.info("User already exists for -> {}", aadharNo);
			throw new AlreadyExistException("User already exist with Aadhar No. : " + aadharNo);
		}
	}

	private void updateBankDetails(final Integer rlUserId, final RlUserInfoDTO userDTO, final String pathKey,
			final String dirPath) throws IOException {
		Optional<RlUserBankDetails> foundBankDetails = bankDetailsRepository.findByRlUserId(rlUserId);
		if (foundBankDetails.isPresent()) {
			/** Updating User Bank details */
			RlUserBankDetails bankDetails = foundBankDetails.get();
			bankDetails.setId(foundBankDetails.get().getId());
			bankDetails.setRlUserId(rlUserId);
			bankDetails.setBankName(userDTO.getBankName());
			bankDetails.setAccountNumber(userDTO.getAccountNo());
			bankDetails.setIfscCode(userDTO.getIfscCode());
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
			LOGGER.info("User Bank details updated for -> {}", rlUserId);
		}
	}

	private FileUploadResponseDTO uploadUserFile(MultipartFile file, final String pathKey, final String dirPath)
			throws IOException {
		final String[] SUPPORTED_EXTN = { ".jpg", ".png", ".jpeg" };
		String imageFileName = file.getOriginalFilename();
		FileUploadResponseDTO fileUploadResponse = null;
		String fileExtension = Objects.requireNonNull(imageFileName);
		LOGGER.info("Filename -> {}", imageFileName);
		if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension::contains)) {
			Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
			if (pathKeyResponse != null) {
				boolean result = (boolean) pathKeyResponse.get("exist");
				LOGGER.info("result -> {}", result);
				if (result) {
					LOGGER.info("pathkey exists, upload the file...");
					fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false, imageFileName, true, file,
							null);
				} else {
					LOGGER.info("pathkey does not exist, create directory and then upload the file...");
					String moduleName = "ts";
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

}
