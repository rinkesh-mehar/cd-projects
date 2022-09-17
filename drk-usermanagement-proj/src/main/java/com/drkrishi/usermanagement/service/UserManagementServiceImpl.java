package com.drkrishi.usermanagement.service;

import java.awt.image.CropImageFilter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.drkrishi.usermanagement.dao.repository.MasterGstmSyncRepository;
import com.drkrishi.usermanagement.properties.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.drkrishi.usermanagement.dao.CreateUserDao;
import com.drkrishi.usermanagement.dao.RoleDao;
import com.drkrishi.usermanagement.dao.repository.AuditTrialRepository;
import com.drkrishi.usermanagement.dao.repository.RolesRepository;
import com.drkrishi.usermanagement.dao.repository.StaticDataReository;
import com.drkrishi.usermanagement.entity.AppMenu;
import com.drkrishi.usermanagement.entity.AuditTrialEntity;
import com.drkrishi.usermanagement.entity.PrVillageAssigment;
import com.drkrishi.usermanagement.entity.Region;
import com.drkrishi.usermanagement.entity.RoleMenu;
import com.drkrishi.usermanagement.entity.Roles;
import com.drkrishi.usermanagement.entity.State;
import com.drkrishi.usermanagement.entity.StaticData;
import com.drkrishi.usermanagement.entity.TaskAllocation;
import com.drkrishi.usermanagement.entity.UserCredentials;
import com.drkrishi.usermanagement.entity.UserRoles;
import com.drkrishi.usermanagement.entity.Users;
import com.drkrishi.usermanagement.entity.VillageTask;
import com.drkrishi.usermanagement.jwtsecurity.model.JwtUser;
import com.drkrishi.usermanagement.model.Config;
import com.drkrishi.usermanagement.model.CreateUserModel;
import com.drkrishi.usermanagement.model.ErrorMessage;
import com.drkrishi.usermanagement.model.ForgotPasswordModel;
import com.drkrishi.usermanagement.model.LoginUserDetailsModel;
import com.drkrishi.usermanagement.model.MenuModel;
import com.drkrishi.usermanagement.model.RegionsModel;
import com.drkrishi.usermanagement.model.ReportingRoleModel;
import com.drkrishi.usermanagement.model.ReportingToModel;
import com.drkrishi.usermanagement.model.ResponseMessage;
import com.drkrishi.usermanagement.model.RolesModel;
import com.drkrishi.usermanagement.model.StateModel;
import com.drkrishi.usermanagement.model.StatusMessage;
import com.drkrishi.usermanagement.model.UpdateUserModel;
import com.drkrishi.usermanagement.model.UserListModel;
import com.drkrishi.usermanagement.model.UserModel;
import com.drkrishi.usermanagement.model.ViewUserModel;
import com.drkrishi.usermanagement.model.WeekConfig;
import com.drkrishi.usermanagement.model.jwtToken;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private static final Logger LOGGER = LogManager.getLogger(UserManagementServiceImpl.class);

	private static int passwordLength = 6;

	private static int otpLength = 8;

	@Autowired
	private CreateUserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private SmsServiceImpl smsServiceImpl;
	@Autowired
	private AuditTrialRepository auditTrialRepository;
	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	private MasterGstmSyncRepository masterGstmSyncRepository;

	@Autowired
	private StaticDataReository staticdataRepo;

	@Autowired
	private AppProperties appProperties;

	String databaseErrorMessage = "User details had not been updated.";

	@Override
	public boolean createUser(CreateUserModel model) {

		Date date = new Date();

		Users drKrishiUsers = new Users(0, model.getFirstName(), model.getMiddleName(), model.getLastName(),
				model.getEmailAddress(), model.getMobileNumber(), model.getState(), model.getRegion(),
				model.getReportingTo(), date, date, model.getCreatedby(), "", 1, "");

		int userId = userDao.createUser(drKrishiUsers);

		if (userId != 0) {

			String password = generatePassword();
			String user_Password = passwordEncrypt(password.toString());

			UserCredentials credientials = new UserCredentials(0, userId, user_Password, 1, null, 0, 0,
					model.getCreatedby(), date, date, "");

			boolean usercredientialsCreated = userDao.createUserCredientials(credientials);

			if (usercredientialsCreated) {

				String roleName = roleDao.getRoleName(model.getRoleId());

				UserRoles drKrishiUserRole = new UserRoles(0, userId, model.getRoleId(), model.getState(), roleName);

				roleDao.saveUserRole(drKrishiUserRole);

				// ->INSERTING CREATE USER DETAILS INTO AUDIT TABLE
				// ->taking new values from model class

				saveCreateUserDetailsIntoAuditTable(userId, model, new Timestamp(new Date().getTime()));

				smsServiceImpl.userCreationNotification(model.getFirstName(), model.getMobileNumber(),
						model.getMobileNumber(), password.toString());

				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void saveCreateUserDetailsIntoAuditTable(int userId, CreateUserModel model, Timestamp timestamp) {

		try {
			String rolename = roleDao.findByReportingTo(model.getReportingTo());
			StringBuffer sb = new StringBuffer();
			sb.append(" CreatedDateTime = " + timestamp);
			sb.append("," + " ");
			sb.append("Crated by = " + model.getCreatedby());
			sb.append("," + " ");
			sb.append("," + " ");
			sb.append("Emailaddress = " + model.getEmailAddress());
			sb.append("," + " ");
			sb.append("Last name = " + model.getLastName());
			sb.append("," + " ");
			sb.append("Middel name = " + model.getMiddleName());
			sb.append("," + " ");
			sb.append("Mobile number = " + model.getMobileNumber());
			sb.append("," + " ");
			sb.append("Region = " + model.getRegion());
			sb.append("," + " ");
			sb.append("State = " + model.getState());
			sb.append("," + " ");
			sb.append("First name = " + model.getFirstName());
			sb.append("," + " ");
			sb.append("Reporting to = " + rolename);
			AuditTrialEntity auditTrial = new AuditTrialEntity(null, "New", timestamp, null, sb.toString(), "user",
					userId);
			auditTrialRepository.save(auditTrial);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean updateUser(UpdateUserModel model) {

		try {

			Date date = new Date();
			int userId = model.getUser_id();
			Users drKrishiUsers = userDao.viewUserDetails(userId);

			if (drKrishiUsers != null) {

				drKrishiUsers.setFirstName(model.getFirstName());
				drKrishiUsers.setMiddleName(model.getMiddleName());
				drKrishiUsers.setLastName(model.getLastName());
				drKrishiUsers.setMobileNumber(model.getMobileNumber());
				drKrishiUsers.setEmailAddress(model.getEmailAddress());
				drKrishiUsers.setStateId(model.getState());
				drKrishiUsers.setRegionId(model.getRegion());
				drKrishiUsers.setReportingTo(model.getReportingTo());
				drKrishiUsers.setModifiedBy(model.getCreatedby());
				drKrishiUsers.setModifiedDateTime(date);

				int modifiedUserId = userDao.createUser(drKrishiUsers);

				if (modifiedUserId != 0) {

					UserCredentials userCredientials = userDao.getUserCredientials(userId);

					if ( userCredientials != null ) {
						userCredientials.setModifiedBy(model.getCreatedby());
					}

					UserRoles userRoles = roleDao.getUserRole(userId);
					if(userRoles.getRoleId() != model.getRoleId()) {
						String oldRoleName = roleDao.getRoleName(userRoles.getRoleId());
						String newRoleName = roleDao.getRoleName(model.getRoleId());
						smsServiceImpl.roleUpdateNotification(drKrishiUsers.getFirstName(), drKrishiUsers.getMobileNumber(), oldRoleName, newRoleName);
					}
					userRoles.setRoleId(model.getRoleId());
					roleDao.saveUserRole(userRoles);

					String rolename = roleDao.findByReportingTo(model.getReportingTo());

					saveUpdateUseDetailsintoAuditTable(userId, model, rolename, drKrishiUsers);

					return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return false;
	}

	private void saveUpdateUseDetailsintoAuditTable(int userId, UpdateUserModel model, String rolename,
			Users drKrishiUsers) {

		try {

			java.sql.Timestamp timestamp = new Timestamp(new Date().getTime());
			StringBuffer sbNew = new StringBuffer();

			sbNew.append(" ModifiedDateTime = " + timestamp);
			sbNew.append("," + " ");
			sbNew.append("Created by = " + model.getCreatedby());
			sbNew.append("," + " ");
			sbNew.append("Emailaddress = " + model.getEmailAddress());
			sbNew.append("," + " ");
			sbNew.append("Last name = " + model.getLastName());
			sbNew.append("," + " ");
			sbNew.append("Middel nmae = " + model.getMiddleName());
			sbNew.append("," + " ");
			sbNew.append("Mobile number = " + model.getMobileNumber());
			sbNew.append("," + " ");
			sbNew.append("Region = " + model.getRegion());
			sbNew.append("," + " ");
			sbNew.append("State = " + model.getState());
			sbNew.append("," + " ");
			sbNew.append("First name = " + model.getFirstName());
			sbNew.append("," + " ");
			sbNew.append("Reporting to = " + rolename);

			// ->taking old values from database
			// Users oldvalues= userDao.findById(userId);
			AuditTrialEntity auditTrial = new AuditTrialEntity(null, "Edit", timestamp, drKrishiUsers.toString(),
					sbNew.toString(), "user", userId);
			auditTrialRepository.save(auditTrial);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String generatePassword() {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "1234567890";
		String symbols = "!@#$%^&*_=+-/.?<>)";
		String values = Capital_chars + numbers;// + Small_chars; + symbols;
		Random rndm_method = new Random();
		StringBuilder sb = new StringBuilder(passwordLength);

		for (int i = 0; i < passwordLength; i++) {
			sb.append(values.charAt(rndm_method.nextInt(values.length())));
		}
		return sb.toString();
	}

	public char[] OTP() {
		String numbers = "0123456789";
		Random rndm_method = new Random();
		char[] otp = new char[otpLength];
		for (int i = 0; i < otpLength; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}
		return otp;
	}

	@Override
	public Users getLoginUserDetails(String emailAddress) {
		return userDao.getUserByEmailAddress(emailAddress);
	}

	@Override
	public boolean validateUserPassword(int userId, String password) {
		return userDao.validateUserPassword(userId, password);
	}

	@Override

	public boolean passwordChange(int userId, String newPassword) {

		UserCredentials userCredientials = userDao.getUserCredientials(userId);

		if (userCredientials != null) {

			userCredientials.setUserPassword(newPassword);
			userCredientials.setIs_force_pwd_change(1);
			userCredientials.setModifiedDateTime(new Date());
			userCredientials.setPasswordResetDate(new Date());
			userCredientials.setTransaction_type(0);
			String oldpass = userCredientials.getUserPassword();
			// ->INSERTING PASSWORD CHANGE INTO AUDIT TABLE
			savePasswordchangeDetailsintoAuditTable(oldpass, newPassword, userId);

			return userDao.updateUserCredientials(userCredientials);
		} else
			return false;
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	private void savePasswordchangeDetailsintoAuditTable(String oldpass, String newPassword, int userId) {

		try {
			java.sql.Timestamp timestamp = new Timestamp(new Date().getTime());
			AuditTrialEntity auditTrial = new AuditTrialEntity(null, "changepassword", timestamp, oldpass,
					newPassword.toString(), "user_credentials", userId);
			auditTrialRepository.save(auditTrial);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean passwordReset(int userId, String newPassword) {

		UserCredentials drKrishiUserCredientials = userDao.getUserCredientials(userId);

		if (drKrishiUserCredientials != null) {
			drKrishiUserCredientials.setUserPassword(newPassword);
			drKrishiUserCredientials.setIs_force_pwd_change(1);
			drKrishiUserCredientials.setTransaction_type(0);
			drKrishiUserCredientials.setPasswordResetDate(new Date());

			return userDao.updateUserCredientials(drKrishiUserCredientials);
		} else {
			return false;
		}

	}

	@Override
	public int countInvalidLoginAttempts(int userId) {
		return userDao.getInvalidLoginAttemptCount(userId);
	}

	@Override
	public boolean updateInvalidLoginAttempts(int userId, int count) {

		UserCredentials credientials = userDao.getUserCredientials(userId);

		if (credientials != null) {
			credientials.setInvalidAttempts(count);
			credientials.setModifiedDateTime(new Date());
			return userDao.updateUserCredientials(credientials);
		} else {
			return false;
		}

	}

	@Override
	public boolean updateLoginTime(int userId) {

		UserCredentials credientials = userDao.getUserCredientials(userId);

		if (credientials != null) {
			credientials.setModifiedDateTime(new Date());
			return userDao.updateUserCredientials(credientials);
		} else {
			return false;
		}
	}

	@Override
	public boolean suspendUserAccount(int userId) {

		Users user = userDao.viewUserDetails(userId);

		if (user != null) {
			user.setStatus(2);
			return userDao.updateUserDetails(user);
		} else {
			return false;
		}

	}

	@Override
	public boolean checkUserIsSuspended(int userId) {
		Users user = userDao.viewUserDetails(userId);
		UserCredentials credientials = userDao.getUserCredientials(userId);
		if (user != null && credientials != null && (user.getStatus() == 2 && credientials.getInvalidAttempts() == 5))
			return true;
		else
			return false;
	}

	@Override
	public boolean checkUserIsInActive(int userId) {

		Users user = userDao.viewUserDetails(userId);
		if ((user != null) && (user.getStatus() == 0))
			return true;
		else
			return false;
	}

	@Override
	public boolean activateUserAccount(int userId, String comments) {

		Date modified_Date_Time = new Date();
		UserCredentials credientials = userDao.getUserCredientials(userId);
		Users user = userDao.viewUserDetails(userId);

		if (user != null && user != null) {

			user.setStatus(1);
			user.setComments(comments);
			user.setModifiedDateTime(modified_Date_Time);
			userDao.updateUserDetails(user);

			credientials.setInvalidAttempts(0);
			credientials.setModifiedDateTime(modified_Date_Time);

			userDao.updateUserCredientials(credientials);

			return true;
		} else
			return false;
	}

	public boolean activateUserAccountFromLockedStatus(int userId, String comments, String newPassword) {

		Date modified_Date_Time = new Date();
		UserCredentials credientials = userDao.getUserCredientials(userId);
		Users user = userDao.viewUserDetails(userId);

		if (user != null && credientials != null) {

			user.setStatus(1);
			user.setComments(comments);
			user.setModifiedDateTime(modified_Date_Time);
			userDao.updateUserDetails(user);

			credientials.setInvalidAttempts(0);
			credientials.setIs_force_pwd_change(0);
			credientials.setUserPassword(newPassword);
			credientials.setModifiedDateTime(modified_Date_Time);
			credientials.setTransaction_type(3);

			userDao.updateUserCredientials(credientials);

			return true;
		} else
			return false;
	}

	@Override
	public boolean inActivateUserAccount(int userId, String comments) {

		Date modified_Date_Time = new Date();
		Users user = userDao.viewUserDetails(userId);

//		UserCredentials credientials = userDao.getUserCredientials(userId);

		if (user != null) {

			user.setStatus(0);
			user.setComments(comments);
			user.setModifiedDateTime(modified_Date_Time);

			return userDao.updateUserDetails(user);
		} else {
			return false;
		}
	}

	@Override
	public Users viewUserDetails(int userId) {
		return userDao.viewUserDetails(userId);
	}

	@Override
	public UserCredentials viewUserCredientialsDetails(int userId) {
		return userDao.getUserCredientials(userId);
	}

	@Override
	public ViewUserModel findUserDetails(int userId) {

		Users user = userDao.viewUserDetails(userId);
		UserCredentials credientials = userDao.getUserCredientials(userId);

		if (user != null && credientials != null) {

			String status = "", stateName = "", regionName = "";

			int userstatus = user.getStatus();

			if (userstatus == 0)
				status = "Inactive";
			else if (userstatus == 1)
				status = "Active";
			else if (userstatus == 2)
				status = "Locked";

			int state = user.getStateId();
			if (state == 0)
				stateName = "ALL";
			else
				stateName = roleDao.getStateName(user.getStateId());

			int region = user.getRegionId();
			if (region == 0)
				regionName = "ALL";
			else
				regionName = roleDao.getRegionName(user.getRegionId());

			String firstName = user.getFirstName();
			String middleName = user.getMiddleName();
			String lastName = user.getLastName();
			String emailAddress = user.getEmailAddress();
			String mobileNumber = user.getMobileNumber();
			String createdby = user.getCreatedBy();

			int user_id = userId;
			int roleId = roleDao.getUserRoleId(userId);
			String roleName = roleDao.getRoleName(roleId);
			int reportingRoleId = roleDao.getRoleDetails(roleId).getReportingTo();
			String reportingRoleName = roleDao.getRoleName(reportingRoleId);
			int reportingTo = user.getReportingTo();
			String reportingName = getUserFullName(userDao.viewUserDetails(reportingTo));
			String modifiedDate = user.getModifiedDateTime().toString();
			String createdDate = credientials.getCreatedDateTime().toString();
			String comments = user.getComments();

			ViewUserModel viewUserModel = new ViewUserModel(user_id, firstName, middleName, lastName, emailAddress,
					mobileNumber, state, stateName, region, regionName, roleId, roleName, reportingRoleId,
					reportingRoleName, status, createdby, reportingTo, reportingName, createdDate, modifiedDate,
					comments);

			return viewUserModel;
		}

		return null;
	}

	@Override
	public boolean saveUserToRole(int userId, int roleId) {

		try {

			UserRoles krishiUserRole = roleDao.getUserRole(userId);

			if (krishiUserRole != null) {

				krishiUserRole.setRoleId(roleId);
				roleDao.saveUserRole(krishiUserRole);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Object> getSpecificUserList(int userId) {

		List<Users> list = new ArrayList<Users>();
		List<Object> userlist = new ArrayList<Object>();

		int roleId = roleDao.getUserRoleId(userId);
		String roleName = roleDao.getRoleName(roleId);

		if (roleName != null && roleName.equalsIgnoreCase("System Admin")) {
			list = userDao.getAllUserList();
		} else {
			list = userDao.getSpecificUserList(userId);
		}

		list.forEach(users -> {

			if (users.getId() != userId) {

				String reportingName = "";
//				UserCredentials credentials = userDao.getUserCredientials(users.getId());

				Users drKrishiUsers = userDao.viewUserDetails(users.getReportingTo());

				if (drKrishiUsers != null) {
					reportingName = getUserFullName(drKrishiUsers);
				}

				if (users != null) {

					int status = users.getStatus();

					String userStatus = "";
					if (status == 0)
						userStatus = "Inactive";
					else if (status == 1)
						userStatus = "Active";
					else if (status == 2)
						userStatus = "Locked";

					String name = getUserFullName(users);

					int role_Id = roleDao.getUserRoleId(users.getId());
					String userRole = roleDao.getRoleName(role_Id);

					String stateName = "";
					if (users.getStateId() == 0)
						stateName = "ALL";
					else
						stateName = roleDao.getStateName(users.getStateId());

					UserListModel listModel = new UserListModel(users.getId(), name, users.getMobileNumber(), stateName,
							userStatus, reportingName, new Timestamp(new Date().getTime()));

					listModel.setStateId(users.getStateId());
					listModel.setRegionId(users.getRegionId());
					listModel.setRole(userRole);

					userlist.add(listModel);
				}
			}
		});

		return userlist;
	}

	@Override
	public boolean updateOneTimePassword(int userId, String password) {

		UserCredentials credientials = userDao.getUserCredientials(userId);

		if (credientials != null) {

			credientials.setUserPassword(password);
			credientials.setIs_force_pwd_change(0);
			credientials.setTransaction_type(2);

			credientials.setModifiedDateTime(new Date());
			return userDao.updateUserCredientials(credientials);
		} else {
			return false;
		}
	}

	@Override
	public boolean newUserActivationPassword(int userId, String password) {

		UserCredentials credientials = userDao.getUserCredientials(userId);
		Users drKrishiUsers = userDao.viewUserDetails(userId);

		if (credientials != null) {

			drKrishiUsers.setStatus(1);
			drKrishiUsers.setModifiedDateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			userDao.updateUserDetails(drKrishiUsers);

			credientials.setUserPassword(password);
			credientials.setIs_force_pwd_change(0);
			credientials.setPasswordResetDate(new java.sql.Timestamp(new java.util.Date().getTime()));

			return userDao.updateUserCredientials(credientials);
		} else {
			return false;
		}
	}

	@Override
	public String getUserRole(int userId) {

		int roleId = roleDao.getUserRoleId(userId);

		return roleDao.getRoleName(roleId);
	}

	@Override
	public boolean validateEmailAddress(String emailAddress) {

		Users drKrishiUsers = userDao.getUserByEmailAddress(emailAddress);

		if (drKrishiUsers != null && drKrishiUsers.getEmailAddress().equals(emailAddress))
			return true;
		else
			return false;

	}

	@Override
	public boolean validateMobileNumb(String mobileNumber) {
		return userDao.validateMobileNumber(mobileNumber);
	}

	@Override
	public boolean validateUpdateMobileNumber(String mobileNumber, int userId) {
		return userDao.validateUpdateMobileNumber(mobileNumber, userId);
	}

	@Override
	public boolean validateUpdateEmailAddress(String emailAddress, int userId) {
		return userDao.validateUpdateEmailAddress(emailAddress, userId);
	}

	@Override
	public List<Object> getListofReporting() {

		List<Object> userlist = new ArrayList<Object>();
		List<Users> usersList = userDao.getAllUserList();

		usersList.forEach(users -> {

			String fullName = getUserFullName(users);

			UserListModel listModel = new UserListModel();
			listModel.setName(fullName);
			listModel.setUser_id(users.getId());

			userlist.add(listModel);
		});
		return userlist;
	}

	public List<Object> getListofReportingTo(UserModel userModel) {

		List<Object> userlist = new ArrayList<Object>();

		int stateId = userModel.getState();
		int regionId = userModel.getRegion();
		int reportingRoleId = userModel.getReportingRoleId();

		List<Users> usersList = userDao.getlistOfReportingTo(stateId, regionId, reportingRoleId);

		usersList.forEach(users -> {

			String fullName = getUserFullName(users);

			UserListModel listModel = new UserListModel();

			int roleId = roleDao.getUserRoleId(users.getId());
			String userRoleName = roleDao.getRoleName(roleId);

			if (userRoleName != null && !userRoleName.equals("Joinee")) {

				listModel.setName(fullName);
				listModel.setStateId(users.getStateId());
				listModel.setRegionId(users.getRegionId());
				listModel.setUser_id(users.getId());
				userlist.add(listModel);
			}
		});
		return userlist;
	}

	public List<Object> getselfReportingTo(UserModel userModel) {

		List<Object> userlist = new ArrayList<Object>();
		int userId = userModel.getUserId();
		Users drKrishiUsers = userDao.viewUserDetails(userId);

		if (drKrishiUsers != null) {

			String fullName = getUserFullName(drKrishiUsers);

			UserListModel listModel = new UserListModel();
			listModel.setName(fullName);
			listModel.setStateId(drKrishiUsers.getStateId());
			listModel.setRegionId(drKrishiUsers.getRegionId());
			listModel.setUser_id(userId);
			userlist.add(listModel);
		}
		return userlist;
	}

	@Override
	public List<Object> getListofStates(int roleId) {

		List<Object> userlist = new ArrayList<Object>();

		Roles roles = roleDao.getRoleDetails(roleId);

		if (roles != null) {

			String roleName = roles.getCode();

			int ecosystemId = roles.getEcosystemId();
			String ecosystem = roleDao.getEcosystem(ecosystemId).getCode();

			if ((roleName.equalsIgnoreCase("SLS")) || (roleName.equalsIgnoreCase("SLC"))
					|| (roleName.equalsIgnoreCase("SLSO")) || (ecosystem.equalsIgnoreCase("CL"))) {
				StateModel listModel = new StateModel(0, "ALL");
				userlist.add(listModel);
				return userlist;
			} else {

				List<State> usersList = roleDao.getStates();
				usersList.forEach(states -> {
					StateModel listModel = new StateModel(states.getStateId(), states.getStateName());
					userlist.add(listModel);
				});
			}
		}
		return userlist;
	}

	@Override
	public List<Object> showlistofStates() {

		List<Object> userlist = new ArrayList<Object>();

		StateModel listModel = new StateModel(0, "ALL");
		userlist.add(listModel);

		List<State> usersList = roleDao.getStates();
		usersList.forEach(states -> {
			StateModel listModel1 = new StateModel(states.getStateId(), states.getStateName());
			userlist.add(listModel1);
		});

		return userlist;
	}

	public List<Object> getListofRegion(int stateId, int roleId) {

		List<Object> regionList = new ArrayList<Object>();

		Roles roles = roleDao.getRoleDetails(roleId);

		if (roles != null) {

			String roleName = roles.getCode();

			if (stateId == 0 || roleName.equalsIgnoreCase("PRM")) {
				RegionsModel model = new RegionsModel(0, "ALL");
				regionList.add(model);
			} else {
				List<Region> associatedRegions = roleDao.getRegion(stateId);
				associatedRegions.forEach(regions -> {

					RegionsModel model = new RegionsModel(regions.getRegionId(), regions.getRegionName());

					model.setRegionId(regions.getRegionId());
					model.setRegionName(regions.getRegionName());
					regionList.add(model);
				});
			}
		}

		return regionList;
	}

	@Override
	public List<Object> showlistofStates(int stateId) {

		List<Object> regionList = new ArrayList<Object>();

		if (stateId == 0) {
			RegionsModel model = new RegionsModel(0, "ALL");
			regionList.add(model);
		} else {
			List<Region> associatedRegions = roleDao.getRegion(stateId);
			associatedRegions.forEach(regions -> {

				RegionsModel model = new RegionsModel(regions.getRegionId(), regions.getRegionName());

				model.setRegionId(regions.getRegionId());
				model.setRegionName(regions.getRegionName());
				regionList.add(model);
			});
		}
		return regionList;
	}

	@Override
	public List<Object> getListofRoles() {

		List<Object> list = new ArrayList<Object>();

		List<Roles> drKrishiRoles = roleDao.getAllRoles();
		drKrishiRoles.forEach(roles -> {

			UserModel model = new UserModel();
			model.setRoleId(roles.getId());
			model.setRoleName(roles.getName());
			list.add(model);
		});

		return list;
	}

	public String passwordEncrypt(String passwordToHash) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedPassword;
	}

	/*
	 * @Override public List<Roles> getJoineeRole() {
	 *
	 * List<Roles> list = new ArrayList<Roles>();
	 *
	 * list.add(roleDao.getJoineeRole());
	 *
	 * return list; }
	 */

	/*
	 * @Override public List<Roles> getListofAssigningroles(UserModel userModel) {
	 *
	 * String ecosystemName = roleDao.getEcosystemName(userModel.getRoleId());
	 *
	 * if (ecosystemName.equalsIgnoreCase("System Admin") ||
	 * ecosystemName.equalsIgnoreCase("Joinee")) {
	 *
	 * return roleDao.getAllRolesWithNoAdmin();
	 *
	 * } else { return roleDao.getRolesByEcosystemName(ecosystemName); } }
	 */

	@Override
	public List<Region> getRegions() {
		return roleDao.getRegions();
	}

	/*
	 * @Override public List<Object> ListOfTileIdGromRegionId(int stateId, int
	 * regionId) {
	 *
	 * String stateName = roleDao.getStateName(stateId);
	 *
	 * List<Object> list = new ArrayList<Object>();
	 *
	 * List<Region> drKrishiRegions = roleDao.getTileIdFromRegions(stateId,
	 * regionId);
	 *
	 * drKrishiRegions.forEach(regions -> {
	 *
	 * regions.getRegionId(); regions.getStateId();
	 *
	 * String tileIdDescription = regions.getTileId() + " ( " + stateName + ")";
	 *
	 * RegionModel regionModel = new RegionModel(stateId, regions.getTileId(),
	 * tileIdDescription);
	 *
	 * list.add(regionModel);
	 *
	 * });
	 *
	 * return list; }
	 */

	@Override
	public String getRoleName(int roleId) {
		return roleDao.getRoleName(roleId);
	}

	/*
	 * @Override public String getEcoSystemName(int roleId) { return
	 * roleDao.getEcosystemName(roleId); }
	 */

	@Override
	public UserRoles getRole(int userId) {
		return roleDao.getUserRole(userId);
	}

	@Override
	public ResponseMessage forgotPasswordService(ForgotPasswordModel forgotpasswordModel) {

		String mobileNumber = forgotpasswordModel.getMobileNumber();

		Users user = userDao.getUserByMobileNumber(mobileNumber);

		if (user != null) {

			UserCredentials credientials = userDao.getUserCredientials(user.getId());

			if (user.getStatus() == 2 || user.getStatus() == 0) {

				ResponseMessage responseMessage = new ResponseMessage();

				if (user.getStatus() == 2)
					responseMessage
					.setError(new ErrorMessage("ERR01", "Your account is locked. Please contact admin."));
				else
					responseMessage
					.setError(new ErrorMessage("ERR02", "Your account is Inactive. Please contact admin."));

				return responseMessage;

			} else {

				String channel = forgotpasswordModel.getChannel();

				if (channel != null && channel.equalsIgnoreCase("android") ) {

					int roleId = roleDao.getUserRoleId( credientials.getUserId() );
					String roleCode = roleDao.getRoleDetails(roleId).getCode();
					
					System.out.println( roleCode  );

					if ( ( roleCode != null ) && ( roleCode.equalsIgnoreCase("PRS") || roleCode.equalsIgnoreCase("FLS")
							|| roleCode.equalsIgnoreCase("MLS") || roleCode.equalsIgnoreCase("MLT")) ) {

					}else {
						ResponseMessage responseMessage = new ResponseMessage();
						responseMessage.setError(new ErrorMessage("ERR01", "User is not authorized to login here."));
						return responseMessage;
					}
				}

				if (channel != null && channel.equalsIgnoreCase("web") ) {

					int roleId = roleDao.getUserRoleId( credientials.getUserId() );
					String roleCode = roleDao.getRoleDetails(roleId).getCode();
					
					System.out.println( roleCode  );
					
					if ( ( roleCode != null ) && ( roleCode.equalsIgnoreCase("PRS") || roleCode.equalsIgnoreCase("FLS") 
							|| roleCode.equalsIgnoreCase("MLS") || roleCode.equalsIgnoreCase("MLT")) ) {
						
						ResponseMessage responseMessage = new ResponseMessage();
						responseMessage.setError(new ErrorMessage("ERR01", "User is not authorized to login here."));
						return responseMessage;
					}

				}


				String password = generatePassword();
				String onetimepassword = passwordEncrypt(password.toString());

				String oldpass = credientials.getUserPassword();

				boolean oneTimePasswordUpdated = updateOneTimePassword(user.getId(), onetimepassword);

				if (oneTimePasswordUpdated) {

					smsServiceImpl.otpNotification(user.getFirstName(), password.toString(), mobileNumber);

					saveforgotPasswordDetailsintoAuditTable(oldpass, onetimepassword, user);

					ResponseMessage responseMessage = new ResponseMessage();
					if(channel != null && channel.equalsIgnoreCase("android")) {
						responseMessage.setStatus(new StatusMessage("ST01",
								"One time password will be sent to your registered Mobile Number. In case you do not receive a SMS, please contact the system administrator."));
					} else {
						responseMessage.setStatus(new StatusMessage("ST01",
								"One time password will be sent to your registered Mobile Number. In case you do not receive a SMS, please contact the system administrator. You will be redirected to the login page in 10 seconds. If not, then please click 'OK'."));
					}

						return responseMessage;
					} else {

						ResponseMessage responseMessage = new ResponseMessage();
						responseMessage.setError(new ErrorMessage("ERR01", "Error in updating One Time Password ... "));
						return responseMessage;
					}
				}


		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(
					new ErrorMessage("ERR01", "Invalid Mobile Number. Please enter the Mobile Number again."));
			return responseMessage;
		}
	}

	private void saveforgotPasswordDetailsintoAuditTable(String oldpass, String onetimepassword, Users user) {
		try {
			java.sql.Timestamp timestamp = new Timestamp(new Date().getTime());
			AuditTrialEntity auditTrialEntity = new AuditTrialEntity(null, "ForgotPassword", timestamp,
					oldpass.toString(), onetimepassword.toString(), "user_credentials", user.getId());
			auditTrialRepository.save(auditTrialEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public ResponseMessage resetPasswordService(int userId, String oldPassword, String newPassword) {

		String encryptedOldPassword = passwordEncrypt(oldPassword);
		boolean validUser = validateUserPassword(userId, encryptedOldPassword);

		if (validUser) {

			String encryptedPassword = passwordEncrypt(newPassword);

			UserCredentials userCredentials = userDao.getUserCredentialsByid(userId);

			String oldpass = userCredentials.getUserPassword();

			boolean passwordUpdated = passwordReset(userId, encryptedPassword);

			if (passwordUpdated) {

				Users user = viewUserDetails(userId);

//				smsServiceImpl.passwordUpdateNotification(user.getFirstName(), user.getMobileNumber());

				LoginUserDetailsModel loginUserDetailsModel = getLoginResponse(userId);

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "Your Password has been reset."));
				responseMessage.setData(loginUserDetailsModel);

				// ->INSERTING RESET PASSWORD INTO AUDIT TABLE

				// UserCredentials userCredentials=userDao.getUserCredentialsByid(userId);
				// String oldpass=userCredentials.getUserPassword();

				saveResetPasswordintoAuditTable(oldpass, encryptedPassword, userId);

				return responseMessage;

			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage));
				return responseMessage;
			}

		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "One time Password is incorrect."));
			return responseMessage;
		}

	}

	private void saveResetPasswordintoAuditTable(String oldpass, String encryptedPassword, int userId) {
		try {
			java.sql.Timestamp timestamp = new Timestamp(new Date().getTime());
			AuditTrialEntity AuditTrialEntity = new AuditTrialEntity(null, "Resetpassword", timestamp,
					oldpass.toString(), encryptedPassword.toString(), "user_credentials", userId);
			auditTrialRepository.save(AuditTrialEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LoginUserDetailsModel getLoginResponse(int userId) {

		Users user = viewUserDetails(userId);
		UserCredentials credientials = viewUserCredientialsDetails(userId);
		Object downloadList = new Object();

		if (user != null && credientials != null) {

			Calendar cal = Calendar.getInstance();
//			cal.set(2020, Calendar.MARCH, 8, 10, 11, 12); //Year, month and day of month
			cal.setTime(new Date());
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setMinimalDaysInFirstWeek(4);
			Date date = cal.getTime();
			System.out.println(date);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date formatdate = new Date();
			String actualDate = dateFormat.format(date);

			System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

			LocalDate localDate = LocalDate.now();
			System.out.println(localDate);

			String name = getUserFullName(user);
			int roleId = roleDao.getUserRoleId(userId);
			String roleName = roleDao.getRoleName(roleId);
			String roleCode = roleDao.getRoleDetails(roleId).getCode();
			List<MenuModel> roleUrl = getRoleUrl(roleId);
			int stateId = user.getStateId();

			int regionId = user.getRegionId();

			String regionName = (regionId == 0) ? "ALL" : roleDao.getRegionName(regionId);
			StaticData data=staticdataRepo.findByKey("masterDataApikey");
			LoginUserDetailsModel loginUserDetailsModel = new LoginUserDetailsModel(user.getId(),
					credientials.getModifiedDateTime(), user.getMobileNumber(), name, roleName, roleCode, roleUrl,
					credientials.getIs_force_pwd_change(), stateId, localDate, cal.get(Calendar.WEEK_OF_YEAR), regionName,regionId,data.getValue());
			
			return loginUserDetailsModel;
		}

		return null;
	}

	@Override
	public ResponseMessage changePasswordService(int userId, String oldPassword, String newPassword) {
		try {
		String encryptedPassword = passwordEncrypt(oldPassword);
		boolean validUser = validateUserPassword(userId, encryptedPassword);

		if (validUser) {

			String newEncryptedPassword = passwordEncrypt(newPassword);

			boolean passwordIschanged = passwordChange(userId, newEncryptedPassword);

			if (passwordIschanged) {
				Users user = viewUserDetails(userId);

//				smsServiceImpl.passwordUpdateNotification(user.getFirstName(), user.getMobileNumber());

				LoginUserDetailsModel loginUserDetailsModel = getLoginResponse(userId);

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "Password is Changed. "));
				responseMessage.setData(loginUserDetailsModel);
				return responseMessage;
			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR02", databaseErrorMessage));
				return responseMessage;
			}
		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "Old Password is incorrect."));
			return responseMessage;
		}
		} catch (Exception e) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR02", "Internal Server Error!"));
			return responseMessage;
		}

	}

	@Override
	public synchronized ResponseMessage loginService(String mobileNumber, String password, String channel, String jwtTokenUrl) {

		Users user = userDao.getUserByMobileNumber(mobileNumber);

		if (user != null) {
			long passwordResetDays = 0;
			int userId = user.getId();
			UserCredentials credientials = userDao.getUserCredientials(userId);

			if (user != null && user.getStatus() == 0) {

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", "Your account is InActive. Please contact admin."));
				return responseMessage;

			} else if (credientials != null && user.getStatus() == 2) {

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", "Your account is locked. Please contact admin."));
				return responseMessage;
			}

			if (credientials != null && credientials.getTransaction_type() == 1) {

				Date createdDate = credientials.getCreatedDateTime();
				Date currentDate = new Date();

				long hours = getHours(createdDate, currentDate);
				System.out.println(" Hours: " + hours);

//				if (hours >= 24) {
//
//					ResponseMessage responseMessage = new ResponseMessage();
//					responseMessage.setError(new ErrorMessage("ERR01",
//							"One Time Password has got expired. Please regenerate through Forgot Password link."));
//					return responseMessage;
//				}

			} else if (credientials != null && credientials.getTransaction_type() == 2) {

				Date modifiedDate = credientials.getModifiedDateTime();
				Date currentDate = new Date();

				System.out.println(" getTransaction_type() " + credientials.getTransaction_type() );
				System.out.println(" modifiedDate " + modifiedDate);
				System.out.println(" currentDate " + currentDate);

				long minutes = getMinutes(modifiedDate, currentDate);
				System.out.println(" Forgot Password Validity: Minutes: " + minutes);

				if (minutes >= 15) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01",
							"One Time Password has got expired. Please regenerate through Forgot Password link."));
					return responseMessage;
				}

			} else if (credientials != null && credientials.getTransaction_type() == 3) {

				Date modifiedDate = credientials.getModifiedDateTime();
				Date currentDate = new Date();

				long hours = getHours(modifiedDate, currentDate);

				System.out.println("minutes : 20"+ hours);
				if (hours > 24) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "One Time Password has got expired. Please regenerate through Forgot Password link."));
					return responseMessage;
				}

			} else {
//				Calendar cal = Calendar.getInstance();
//				cal.set(2020, Calendar.MARCH, 10);

				Date passwordResetDate = credientials.getPasswordResetDate();
				Date currentDate = new Date();

				passwordResetDays = getDays(passwordResetDate, currentDate);
				System.out.println(" passwordResetDays::   " + passwordResetDays);
			}

			UserRoles userRole = getRole(userId);

			if (userRole != null) {

				int roleId = userRole.getRoleId();
				String roleCode = roleDao.getRoleDetails(roleId).getCode();

				if (channel == null && (roleCode.equals("PRS") || roleCode.equals("MLS")
						|| roleCode.equals("FLS") || roleCode.equals("MLT") || roleCode.equals("AGS"))) {

					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "User is not authorized to login here."));
					return responseMessage;
				}

				if (channel != null && channel.equalsIgnoreCase("android")
						&& (roleCode.equals("SA") || roleCode.equals("PRM") || roleCode.equals("QAIV")
								|| roleCode.equals("CLS") || roleCode.equals("CLC") || roleCode.equals("CLSO") || roleCode.equals("CCTCDT")
								|| roleCode.equals("QAKML") || roleCode.equals("SLS") || roleCode.equals("SLC") || roleCode.equals("SLSO")
								|| roleCode.equals("RLC") || roleCode.equals("RLT") || roleCode.equals("CCTCDNT") || roleCode.equals("CCTCAT")
								|| roleCode.equals("CLCSO") || roleCode.equals("QAKYC") || roleCode.equals("RLM"))) {

					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "User is not authorized to login here."));
					return responseMessage;
				}
			}

			String encryptedPassword = passwordEncrypt(password);
			boolean validUser = validateUserPassword(userId, encryptedPassword);

			if (validUser) {

				updateInvalidLoginAttempts(userId, 0);

				RestTemplate restTemplate = new RestTemplate();

				JwtUser jwtuser = new JwtUser();
				jwtuser.setUserName(mobileNumber);
				jwtuser.setRole(mobileNumber);
				jwtuser.setId(userId);

				ResponseEntity<String> response = restTemplate.postForEntity(jwtTokenUrl, user, String.class);
				String jwtToken = response.getBody().toString();

				String passwordResetMessage = null;
				if (passwordResetDays >= 90) {
					passwordResetMessage = "Your password is expired and must be changed. You will be directed to change password page.";
				}

				LoginUserDetailsModel loginUserDetailsModel = getLoginResponse(userId);
				loginUserDetailsModel.setPasswordChangeMessage(passwordResetMessage);
				Config config = getConfig();
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "login is successful."));
				responseMessage.setData(loginUserDetailsModel);
				responseMessage.setHeader(new jwtToken(jwtToken));
				responseMessage.setConfig(config);

				if (channel != null){
				if (channel.equals("android")) {
					String userDataURL = null;
					Users users = userDao.getUserByUserId(userId);
					Map<String, Object> responseMap = new LinkedHashMap<>();
					List<Map<String, Object>> masterDetailsList = new ArrayList<>();
					List<Map<String, Object>> mediaDetailsList = new ArrayList<>();
					List<Map<String, Object>> mmpkList = new ArrayList<>();

					try {
						int roleId = userRole.getRoleId();
						List<Map<String, Object>> masterDetailsListMap = null;
						if (roleId != 0) {
							masterDetailsListMap = masterGstmSyncRepository.getMasterSyncDetailsByRoleId(roleId);

						}
						if (masterDetailsListMap != null && !masterDetailsListMap.isEmpty() && masterDetailsListMap.size() > 0) {

							for (Map<String, Object> masterDetails : masterDetailsListMap) {
								LinkedHashMap<String, Object> masterDetailsMap = new LinkedHashMap<>();
								masterDetailsMap.put("label", masterDetails.get("label"));
								masterDetailsMap.put("tableName", masterDetails.get("tableName"));


								if ("All".equals(String.valueOf(masterDetails.get("zippingLevel")))) {
									masterDetailsMap.put("url", appProperties.getBlobUrl() + masterDetails.get("tableName") +"-"+ ((masterDetails.get("lastSync")) != null ?String.valueOf(masterDetails.get("lastSync")) : "" )+ ".sql");
								} else if ("State".equals(String.valueOf(masterDetails.get("zippingLevel")))) {
									masterDetailsMap.put("url", appProperties.getBlobUrl() + masterDetails.get("tableName") + "-" + users.getStateId() +(masterDetails.get("roleId") != null ? "-".concat(String.valueOf(roleId)).concat("-") : "-")+ ((masterDetails.get("lastSync")) != null ?String.valueOf(masterDetails.get("lastSync")) : "")+".sql");
								} else if ("Region".equals(String.valueOf(masterDetails.get("zippingLevel")))) {
									masterDetailsMap.put("url", appProperties.getBlobUrl() + masterDetails.get("tableName") + "-" + user.getRegionId() +(masterDetails.get("roleId") != null ? "-".concat(String.valueOf(roleId)).concat("-") : "-")+ ((masterDetails.get("lastSync")) != null ?String.valueOf(masterDetails.get("lastSync")) : "")+ ".sql");
								}
								masterDetailsList.add(masterDetailsMap);
							}
						}
						List<Map<String, Object>> mediaDataList = masterGstmSyncRepository.getPlatPartAndSymptomsByRoleId(roleId);
						if (mediaDataList != null && !mediaDataList.isEmpty() && masterDetailsListMap.size() > 0) {
							Map<String, Object> plantplartDetails = mediaDataList.get(1);
							if (plantplartDetails != null) {
								for (int i = 1; i <= 2; i++) {

									LinkedHashMap<String, Object> plantPartMap = new LinkedHashMap<>();
									String labelName = (String) plantplartDetails.get("label");
									String tableName = (String) plantplartDetails.get("tableName");
									String url = (String) plantplartDetails.get("url");
									url = url.replace("<ENV>", appProperties.getPlantPartAndSymptomsUrl());

									plantPartMap.put("label", labelName.replace("<PARTID>", String.valueOf(i)));
									plantPartMap.put("tableName", tableName.replace("<REGIONID>_<PARTID>", users.getStateId() + "_" + i));
									plantPartMap.put("url", url.replace("<REGIONID>_<PARTID>", users.getStateId() + "_" + i));

									mediaDetailsList.add(plantPartMap);
								}
							}

							Map<String, Object> symptoms = mediaDataList.get(0);
							if (symptoms != null) {
								for (int i = 1; i <= 8; i++) {

									LinkedHashMap<String, Object> symptomMap = new LinkedHashMap<>();
									String labelName = (String) symptoms.get("label");
									String tableName = (String) symptoms.get("tableName");
									String url = (String) symptoms.get("url");
									url = url.replace("<ENV>", appProperties.getPlantPartAndSymptomsUrl());

									symptomMap.put("label", labelName.replace("<PARTID>", String.valueOf(i)));
									symptomMap.put("tableName", tableName.replace("<REGIONID>_<PARTID>", users.getStateId() + "_" + i));
									symptomMap.put("url", url.replace("<REGIONID>_<PARTID>", users.getStateId() + "_" + i));

									mediaDetailsList.add(symptomMap);
								}
							}
						}

						Map<String, Object> getMMpkList = masterGstmSyncRepository.getMmpkByuserId(userId);
						if (getMMpkList != null && !getMMpkList.isEmpty() && getMMpkList.size() > 0) {
							if (getMMpkList.get("mmpkPartCount")!= null){

								for (int i = 1; i <= (Integer) getMMpkList.get("mmpkPartCount"); i++) {
									String nums = String.format("%02d", i);
//								LOGGER.info("increment is {}", (char)a);
									LinkedHashMap<String, Object> mmpkMap = new LinkedHashMap<>();
									String labelName = (String) getMMpkList.get("label");
									mmpkMap.put("label", (labelName.replace("<PARTID>", nums)));
//								mmpkMap.put("tableName", ((i == 0) ? "filea" : "file.z" + nums));
									mmpkMap.put("tableName", "file"+i);

									String url = (String) getMMpkList.get("url");
									url = url.replace("<ENV>", appProperties.getMmpkBlobUrl());
									url = url.replace("<REGIONID>", String.valueOf(users.getRegionId()));
//								url = ((i == 0) ? url.replace("z.<PARTID>", "zip") : url.replace("z.<PARTID>", "z" + nums));
									url = url.replace("<PARTID>", String.valueOf(i));

									mmpkMap.put("url", url);
									mmpkList.add(mmpkMap);
								}
								String userData = masterGstmSyncRepository.getScoutUserDataAPI(roleId);
								if (roleId == 8) {
									userDataURL = appProperties.getFlsUrl() + userData + "/" + userId;
								} else {
									userDataURL = appProperties.getPrsUrl() + (userData != null? userData : "user-data-prs")+ "/" + userId;
								}
							} else {
								/**/
							}
						}



					} catch (Exception e) {
						e.printStackTrace();
					}

					responseMap.put("masters", masterDetailsList);
					responseMap.put("medias", mediaDetailsList);
					responseMap.put("mmpks", mmpkList);
					responseMap.put("userDataJsonURL", userDataURL);
					responseMessage.setDownloadList(responseMap);
				}
			}
				updateLoginTime(userId);

				String userpass = credientials.getUserPassword();
				String usermobilenumber = user.getMobileNumber();

				saveloginDetailsintoaditTable(userId, userpass, usermobilenumber);
				return responseMessage;
			} else {

				int count = countInvalidLoginAttempts(userId);

				if (count < 5) {

					count = count + 1;
					updateInvalidLoginAttempts(userId, count);
					count = countInvalidLoginAttempts(userId);
				}

				if (count == 5) {

					if (suspendUserAccount(userId)) {

//						smsServiceImpl.accountLockedNotification(user.getFirstName(), user.getMobileNumber());

						ResponseMessage responseMessage = new ResponseMessage();
						responseMessage.setError(
								new ErrorMessage("ERR01", "Your account is locked. Please contact admin."));
						return responseMessage;

					} else {
						ResponseMessage responseMessage = new ResponseMessage();
						responseMessage.setError(new ErrorMessage("ERR01", "Error in Suspending user account. "));
						return responseMessage;
					}
				} else {
//					smsServiceImpl.accountAccessNotification(user.getFirstName(), user.getMobileNumber());

					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "Invalid Password. You have made " + count
							+ " unsuccessful attempt(s). The maximum retry attempts allowed for login are 5"));
					return responseMessage;
				}
			}
		} else {

			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage
					.setError(new ErrorMessage("ERR01", "Invalid Mobile Number. Please enter valid Mobile Number"));
			return responseMessage;
		}

	}


	private Config getConfig() {

		Calendar calendar = Calendar.getInstance();

		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setMinimalDaysInFirstWeek(4);
		calendar2.setFirstDayOfWeek(Calendar.MONDAY);
		calendar2.add(Calendar.DAY_OF_MONTH, 7*3);

		int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
		int currentYear = calendar.get(Calendar.YEAR);;

		int endofweekyear = calendar2.get(Calendar.WEEK_OF_YEAR);
		int endYear = calendar2.get(Calendar.YEAR);


		Config config = new Config();
		WeekConfig startWeek = new WeekConfig();
//		if(weekofyear == 1) {
//			startWeek.setWeek(52);
//			startWeek.setYear(currentYear-1);
//		} else {
//			startWeek.setWeek(weekofyear-1);
//			startWeek.setYear(currentYear);
//		}
		startWeek.setWeek(weekofyear);
		startWeek.setYear(currentYear);
		config.setStartWeek(startWeek);

		WeekConfig endWeek = new WeekConfig();

		/*
		 * int endWeekNo = weekofyear == 50 ? 1 : weekofyear == 51 ? 2 : weekofyear ==
		 * 52 ? 3 : weekofyear + 3; if(weekofyear >= 50) { endWeek.setWeek(endWeekNo);
		 * endWeek.setYear(currentYear+1); } else { endWeek.setWeek(endWeekNo);
		 * endWeek.setYear(currentYear); }
		 */

		endWeek.setWeek(endofweekyear);
		endWeek.setYear(endYear);


		config.setEndWeek(endWeek);
		return config;

	}




	public static int getWeek(int year)
	{
		 Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, 2020);
		    cal.set(Calendar.MONTH, Calendar.DECEMBER);
		    cal.set(Calendar.DAY_OF_MONTH, 31);

		    int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
		    int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
		    int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
		    return numberOfWeeks;
	}

	private long getHours(Date d1, Date d2) {

		if (d1 != null && d2 != null) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(d1);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(d2); // Year, month and day of month

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
//			long diffInSecond = diffInMilis / 1000;
//			long diffInMinute = diffInMilis / (60 * 1000);
			long diffInHour = diffInMilis / (60 * 60 * 1000);
//			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);

			return diffInHour;
		}
		return 0;
	}

	private long getMinutes(Date d1, Date d2) {

		if (d1 != null && d2 != null) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(d1);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(d2); // Year, month and day of month

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
//			long diffInSecond = diffInMilis / 1000;
			long diffInMinute = diffInMilis / (60 * 1000);
//			long diffInHour = diffInMilis / (60 * 60 * 1000);
//			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);

			return diffInMinute;
		}

		return 0;
	}

	private long getDays(Date d1, Date d2) {

		if (d1 != null && d2 != null) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(d1);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(d2); // Year, month and day of month

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
//			long diffInSecond = diffInMilis / 1000;
//			long diffInMinute = diffInMilis / (60 * 1000);
//			long diffInHour = diffInMilis / (60 * 60 * 1000);
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);

			return diffInDays;
		}
		return 0;
	}

	private void saveloginDetailsintoaditTable(int userId, String userpass, String usermobilenumber) {

		try {
			java.sql.Timestamp timestamp = new Timestamp(new Date().getTime());
			StringBuffer sbold = new StringBuffer();

			sbold.append("user_mobileNumber :" + "  " + usermobilenumber + " ");
			sbold.append("user_password :" + "  " + userpass);

			AuditTrialEntity auditTrial = new AuditTrialEntity(null, "lOGIN", timestamp, sbold.toString(), null, "user",
					userId);
			auditTrialRepository.save(auditTrial);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<MenuModel> getRoleUrl(int roleId) {

		List<MenuModel> roleMenuList = new ArrayList<MenuModel>();

		List<RoleMenu> roleMenu = roleDao.getRoleMenu(roleId);

		roleMenu.forEach(rolemenu -> {

			AppMenu appmenu = roleDao.getAppMenu(rolemenu.getMenu_id());

			if (appmenu != null) {
				MenuModel menuModel = new MenuModel(appmenu.getName(), appmenu.getDisplay_name(), appmenu.getUrl(),
						appmenu.getParent_menu_id(), appmenu.getIcon(), appmenu.getSequence());

				roleMenuList.add(menuModel);
			}
		});

//		RoleMenu roleMenu = roleDao.getRoleMenuDetails(roleId);
//		AppMenu appmenu = roleDao.getAppMenu(roleMenu.getMenu_id());
//
//		MenuModel menuModel = new MenuModel(appmenu.getName(), appmenu.getDisplay_name(), appmenu.getUrl());

		/*
		 * String roleUrl = "";
		 *
		 * Roles role = roleDao.getRoleDetails(roleId); String roleName =
		 * role.getCode();
		 *
		 * if (roleName != null && roleName.equals("SA")) { roleUrl = "/list-of-users";
		 * } else if (roleName != null && roleName.equals("PRM")) { roleUrl =
		 * "/assignment-list"; } else if (roleName != null && roleName.equals("RLM")) {
		 * roleUrl = "/rlm-dashboard"; } else { roleUrl = "/list-of-users"; }
		 */

		return roleMenuList;
	}

	private String getUserFullName(Users user) {

		String fname = user.getFirstName() == null ? "" : user.getFirstName();
		String mname = user.getMiddleName() == null ? "" : user.getMiddleName();
		String lname = user.getLastName() == null ? "" : user.getLastName();

		String name = fname + " " + mname + " " + lname;

		return name;
	}

	@Override
	public ResponseMessage userListService(int userId) {

		List<Object> userlist = getSpecificUserList(userId);

		if (!userlist.isEmpty()) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(new StatusMessage("ST01", "User is Listed. "));
			responseMessage.setData(userlist);
			return responseMessage;
		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "There are no users to..."));
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage userActivationService(int userId, String comments) {

		Users user = viewUserDetails(userId);

		if (user != null) {

			ResponseMessage responseMessage = new ResponseMessage();

			int status = user.getStatus();

			if (status == 0) {

				if (activateUserAccount(userId, comments)) {

//					smsServiceImpl.userActivationNotification(user.getFirstName(), user.getMobileNumber());
					responseMessage.setStatus(new StatusMessage("ST01", "User had been activated successfully."));
				} else {
					responseMessage.setError(new ErrorMessage("ERR01", "User details had not been updated."));
				}

			} else if (status == 2) {

				String password = generatePassword();
				String newPassword = passwordEncrypt(password.toString());

				if (activateUserAccountFromLockedStatus(userId, comments, newPassword)) {
//					smsServiceImpl.userUnlockedNotification(user.getFirstName(), user.getMobileNumber(),
//							user.getMobileNumber(), password.toString());
					responseMessage.setStatus(new StatusMessage("ST01", "User had been activated successfully."));
				} else {
					responseMessage.setError(new ErrorMessage("ERR01", "User details had not been updated."));
				}

			} else if (status == 1) {
				responseMessage.setError(new ErrorMessage("ERR01", "User is already in active status."));
			}

			return responseMessage;

		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "Invalid user."));
			return responseMessage;
		}

		/*
		 * if (!isActive) {
		 *
		 * Users user = viewUserDetails(userId);
		 *
		 * char password[] = generatePassword(); String newPassword =
		 * passwordEncrypt(password.toString());
		 *
		 * boolean activated = activateUserAccount(userId, comments, newPassword);
		 *
		 * if (activated) {
		 *
		 *
		 * if (status == 0) {
		 *
		 *
		 * smsServiceImpl.userActivationNotification(user.getFirstName(),
		 * user.getMobileNumber());
		 *
		 * } else if (status == 2) {
		 *
		 * smsServiceImpl.userUnlockedNotification(user.getFirstName(),
		 * user.getMobileNumber(), user.getMobileNumber(), password.toString()); }
		 *
		 * ResponseMessage responseMessage = new ResponseMessage();
		 * responseMessage.setStatus(new StatusMessage("ST01", "User is activated. "));
		 * return responseMessage;
		 *
		 * } else {
		 *
		 * ResponseMessage responseMessage = new ResponseMessage();
		 * responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage ));
		 * return responseMessage; }
		 *
		 * } else { ResponseMessage responseMessage = new ResponseMessage();
		 * responseMessage.setError(new ErrorMessage("ERR01",
		 * "User is already in active status ")); return responseMessage; }
		 */

	}

	@Override
	public ResponseMessage userInActivationService(int userId, String comments) {

		Users drkuser = viewUserDetails(userId);
		boolean isInActive = true;

//		UserCredentials userCredientials = viewUserCredientialsDetails(userId);

		int status = drkuser.getStatus();

		if (status == 0) {
			isInActive = true;
		} else if (status == 1) {
			isInActive = false;
		} else if (status == 2) {
			isInActive = false;
		}

		if (!isInActive) {

			int roleId = roleDao.getUserRoleId(userId);
			Roles role = roleDao.getRoleDetails(roleId);

			// boolean isManager = role.getIsManager();

			/*
			 * if (role != null && role.getIsManager()) {
			 *
			 * List<Users> users = userDao.getSpecificUserList(userId);
			 *
			 * if (users.size() != 0) { ResponseMessage responseMessage = new
			 * ResponseMessage(); responseMessage.setError(new ErrorMessage("ERR01",
			 * "Cannot Deactivate User: Please move users to other \"Reporting To\" before deactivating user"
			 * )); return responseMessage; } }
			 *
			 * if (role != null && !role.getCode().equals("PRS")) {
			 *
			 * boolean taskCompleted = checkTaskCompletedAssigned(userId);
			 *
			 * if (!taskCompleted) { ResponseMessage responseMessage = new
			 * ResponseMessage(); responseMessage.setError(new ErrorMessage("ERR01",
			 * "Cannot Deactivate User: Please complete/move pending task before deactivating user"
			 * )); return responseMessage; } }
			 *
			 * if (role != null && role.getCode().equals("PRS")) { boolean taskCompleted =
			 * checkPrsTaskCompletedAssigned(userId);
			 *
			 * if (!taskCompleted) { ResponseMessage responseMessage = new
			 * ResponseMessage(); responseMessage.setError(new ErrorMessage("ERR01",
			 * "Cannot Deactivate User: Please complete/move pending task before deactivating user"
			 * )); return responseMessage; } }
			 */

			boolean inactivated = inActivateUserAccount(userId, comments);

			if (inactivated) {

				Users user = viewUserDetails(userId);

				if (status == 1) {
//					smsServiceImpl.userInActivationNotification(user.getFirstName(), user.getMobileNumber());
				} else if (status == 2) {
//					smsServiceImpl.userDeactivationFromLocked(user.getFirstName(), user.getMobileNumber());
				}

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "User is InActivated. "));

				return responseMessage;
			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage));
				return responseMessage;
			}
		} else {

			if (status == 0) {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", "User is already in InActive status "));
				return responseMessage;
			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", " "));
				return responseMessage;
			}
		}
	}

	@Override
	public ResponseMessage createUserRoleService(int userId, int roleId) {

		Users user = viewUserDetails(userId);
		int oldRoleId = roleDao.getUserRoleId(userId);
		String oldRole = roleDao.getRoleName(oldRoleId);

		String roleName = roleDao.getRoleName(roleId);

		if (roleName != null && roleName != null) {

			boolean roleUpdated = saveUserToRole(userId, roleId);

			if (roleUpdated) {

				String newRole = roleName;

//				smsServiceImpl.roleUpdateNotification(user.getFirstName(), user.getMobileNumber(), oldRole, newRole);

				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "user role is updated successfully."));
				return responseMessage;
			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage));
				return responseMessage;
			}
		}

		return null;
	}

	@Override
	public ResponseMessage createUserService(CreateUserModel model) {

		boolean mobileNumberExists = validateMobileNumb(model.getMobileNumber());

		if (mobileNumberExists == false) {

			String emailAddress = model.getEmailAddress();

			if (emailAddress != null && !emailAddress.equals("")) {

				boolean emailAddressExists = validateEmailAddress(model.getEmailAddress());

				if (emailAddressExists) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "This Email address already exists."));
					return responseMessage;
				}
			}

			// ->WAY FOR AUDIT TABLE CODE
			boolean userCreated = createUser(model);
			ResponseMessage responseMessage = new ResponseMessage();

			if (userCreated) {
				responseMessage.setStatus(new StatusMessage("ST01", "user is created successfully."));
			} else {
				responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage));
			}
			return responseMessage;

		} else {

			ResponseMessage responseMessage = new ResponseMessage();

			if (mobileNumberExists) {
				responseMessage.setError(new ErrorMessage("ERR01", "This Mobile number already exists."));
			} else {
				responseMessage.setError(new ErrorMessage("ERR01", ""));
			}
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage updateUserService(UpdateUserModel userModel) {

		String mobileNumber = userModel.getMobileNumber();
		String emailAddress = userModel.getEmailAddress();

		boolean mobileNumberExists = validateUpdateMobileNumber(mobileNumber, userModel.getUser_id());

		if (mobileNumberExists) {

			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "This Mobile number already exists."));
			return responseMessage;

		} else {

			if (emailAddress != null && !emailAddress.equals("")) {

				boolean emailAddressExists = validateUpdateEmailAddress(emailAddress, userModel.getUser_id());

				if (emailAddressExists) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01", "This Email address already exists."));
					return responseMessage;
				}
			}

			int userId = userModel.getUser_id();
			int roleId = roleDao.getUserRoleId(userId);
			Roles role = roleDao.getRoleDetails(roleId);
			boolean isManager = role.getIsManager();

			int newRoleId = userModel.getRoleId();

			if ((roleId != newRoleId) && (isManager)) {

				List<Users> users = userDao.getSpecificUserList(userId);

				if (users.size() != 0) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01",
							"Cannot Change Role: Please move users to other \"Reporting To\" before changing role"));
					return responseMessage;
				}
			}

			if ((role != null) && (roleId != newRoleId) && !role.getCode().equals("PRS")) {
				System.out.println(role.getCode());
				boolean taskCompleted = true;//checkTaskCompletedAssigned(userId);

				if (!taskCompleted) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01",
							"Cannot Change Role: Please complete/move pending task before changing role"));
					return responseMessage;
				}
			}

			if ((role != null) && (roleId != newRoleId) && role.getCode().equals("PRS")) {

				boolean taskCompleted = true;//checkPrsTaskCompletedAssigned(userId);

				if (!taskCompleted) {
					ResponseMessage responseMessage = new ResponseMessage();
					responseMessage.setError(new ErrorMessage("ERR01",
							"Cannot Change Role: Please complete/move pending task before changing role"));
					return responseMessage;
				}
			}

//->WAY FOR FINDIT CODE FOR AUDIT TABLE
			boolean userUpdated = updateUser(userModel);

			if (userUpdated) {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setStatus(new StatusMessage("ST01", "user is updated successfully. "));
				return responseMessage;
			} else {
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage.setError(new ErrorMessage("ERR01", databaseErrorMessage));
				return responseMessage;
			}
		}

	}

	@Override
	public List<Object> listOfRoles() {
		return roleDao.listOfRoles();
	}

	@Override
	public List<Object> getListofReportingRole(int roleId) {

		List<Object> reportingRole = new ArrayList<Object>();

		Roles roleDetails = roleDao.getRoleDetails(roleId);

		if (roleDetails != null) {
			int reportingTo = roleDetails.getReportingTo();
			Roles sendRole = roleDao.getRoleDetails(reportingTo);

			if (sendRole != null) {
				ReportingRoleModel reportingRoleModel = new ReportingRoleModel(sendRole.getId(), sendRole.getName());
				reportingRole.add(reportingRoleModel);
			}
		}
		return reportingRole;
	}

	@Override
	public List<Object> reportingToService(int reportingRoleId, int state, int region) {

		List<Users> users = new ArrayList<Users>();
		List<Object> reportingTo = new ArrayList<Object>();

		Roles roles = roleDao.getRoleDetails(reportingRoleId);

		if (roles != null) {

			String roleName = roles.getCode();

			if (roleName != null && (roleName.equalsIgnoreCase("SA"))) {

				//int userId = roleDao.getUserIdByRoleId(reportingRoleId).getUserId();
				List<Integer> userIds = roleDao.getListOfUserIdByRoleId(reportingRoleId);
				userIds.forEach(id -> {
					List<Users> u = userDao.getReportingTo(id);
					users.addAll(u);
				});
				//users = userDao.getReportingTo(userId);

			} else if (roleName != null && (roleName.equalsIgnoreCase("PRM"))) {
				List<Integer> prmUserId = roleDao.getListOfUserIdByRoleId(reportingRoleId);
				for (int userId : prmUserId) {

					Users DrKrishiUser = userDao.getReportingToByState(userId, state);
					if (DrKrishiUser != null)
						users.add(DrKrishiUser);
				}
			} else if (roleName != null && (roleName.equalsIgnoreCase("RLM"))) {

				List<Integer> rlmUserId = roleDao.getListOfUserIdByRoleId(reportingRoleId);
				for (int userId : rlmUserId) {
					Users DrKrishiUser = userDao.getReportingToByStateRegion(userId, state, region);
					if (DrKrishiUser != null)
						users.add(DrKrishiUser);
				}
			} else {
				System.out.println(" CL USER .........  ");
				List<Integer> otherUserId = roleDao.getListOfUserIdByRoleId(reportingRoleId);
				for (int userId : otherUserId) {

					Users DrKrishiUser = userDao.viewUserDetails(userId);
					if (DrKrishiUser != null)
						users.add(DrKrishiUser);
				}

			}

			users.forEach(user -> {
				String fullName = getUserFullName(user);
				ReportingToModel reportingToModel = new ReportingToModel(user.getId(), fullName);
				reportingTo.add(reportingToModel);
			});
		}

		return reportingTo;
	}

	@Override
	public List<Object> changeRole(int userId) {
		List<Object> list = new ArrayList<Object>();

		/*
		 * List<TaskAllocation> taskAllocation = roleDao.getTaskAllocation(userId);
		 * List<TaskAllocation> assignedTask = taskAllocation.stream().filter(t ->
		 * t.getStatus() < 2).collect(Collectors.toList()); if(assignedTask.size() > 0)
		 * { return list; }
		 */

		String changeRole = "";
		int roleId = roleDao.getUserRoleId(userId);
		Roles roles = roleDao.getRoleDetails(roleId);
		String roleCode = roles.getCode();

		if (roleCode != null && roleCode.equals("FLS")) {
			changeRole = "MLS";
		} else if (roleCode != null && roleCode.equals("MLS")) {
			changeRole = "FLS";
		} else if (roleCode != null && roleCode.equals("RLT")) {
			changeRole = "MLT";
		} else if (roleCode != null && roleCode.equals("MLT")) {
			changeRole = "RLT";
		}

		if (!changeRole.equals("")) {
			List<Roles> role = roleDao.getRolesByCode(changeRole);

			role.forEach(changerole -> {
				RolesModel rolesModel = new RolesModel(changerole.getId(), changerole.getName());
				list.add(rolesModel);
			});
		}

		return list;
	}

	@Override
	public boolean checkTaskCompletedAssigned(int userId) {

		System.out.println(" UserId " + userId);
		boolean taskCompleted = true;

		List<TaskAllocation> taskAllocation = roleDao.getTaskAllocation(userId);

		taskAllocation.forEach(tasks -> {
			System.out.println(tasks.getAssigneeId() + " " + tasks.getStatus());
		});

		// 0 is assigned 1 is cancelled/ dropped 2 is Completed
		List<TaskAllocation> completedTasks = taskAllocation.stream().filter(tasks -> tasks.getStatus() == 0)
				.collect(Collectors.toList());

		if (completedTasks.size() != 0) {
			taskCompleted = false;
		} else {
			taskCompleted = true;
		}

		System.out.println(" taskCompleted " + taskCompleted);

		return taskCompleted;
	}

	@Override
	public boolean checkPrsTaskCompletedAssigned(int userId) {

		boolean taskCompleted = true;
		List<Integer> taskAssigned = new ArrayList<Integer>();

		List<PrVillageAssigment> prsAssignmentId = roleDao.getPrsAssignmentyId(userId);

		prsAssignmentId.forEach(assignmentId -> {

			VillageTask prs_villageTask = roleDao.getPrsTask(assignmentId.getAssigmentId());

			if (prs_villageTask != null && prs_villageTask.getIsCompleted() == 0) {
				taskAssigned.add(prs_villageTask.getIsCompleted());
			}
		});

		if (taskAssigned.size() != 0) {
			taskCompleted = false;
		}

		return taskCompleted;
	}

	@Override
	public boolean getStatus(int userId) {

		Users user = viewUserDetails(userId);

		if (user.getStatus() == 0 || user.getStatus() == 2) {
			return false;
		} else if (user.getStatus() == 1) {
			return true;
		}

		return false;
	}

}
