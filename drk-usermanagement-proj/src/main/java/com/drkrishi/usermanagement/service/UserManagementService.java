package com.drkrishi.usermanagement.service;

import java.util.List;

import com.drkrishi.usermanagement.entity.Region;
import com.drkrishi.usermanagement.entity.UserCredentials;
import com.drkrishi.usermanagement.entity.UserRoles;
import com.drkrishi.usermanagement.entity.Users;
import com.drkrishi.usermanagement.model.CreateUserModel;
import com.drkrishi.usermanagement.model.ForgotPasswordModel;
import com.drkrishi.usermanagement.model.ResponseMessage;
import com.drkrishi.usermanagement.model.UpdateUserModel;
import com.drkrishi.usermanagement.model.UserModel;
import com.drkrishi.usermanagement.model.ViewUserModel;

public interface UserManagementService {

	public boolean createUser(CreateUserModel model);
	
	public boolean updateUser (UpdateUserModel userModel);
	
	public Users getLoginUserDetails(String emailAddress);

	public boolean validateEmailAddress(String emailAddress);
	
	public boolean validateMobileNumb(String mobileNumber);
	
	public boolean validateUpdateMobileNumber(String mobileNumber, int userId);
	
	public boolean validateUpdateEmailAddress(String emailAddress, int userId);
	
	public boolean validateUserPassword(int userId, String password); 
	
	public String passwordEncrypt(String passwordToHash);
	
	public boolean passwordChange(int userId, String password);
	
	public boolean passwordReset(int userId, String newPassword);
	
	public int countInvalidLoginAttempts(int userId);
	
	public boolean updateInvalidLoginAttempts(int userId,int count);
	
	public boolean updateLoginTime(int userId);
	
	public boolean suspendUserAccount(int userId);
	
	public boolean checkUserIsSuspended(int userId);
	
	public boolean checkUserIsInActive(int userId);
	
	public boolean activateUserAccount(int userId, String comments);
	
	public boolean inActivateUserAccount(int userId, String comments);
	
	public Users viewUserDetails(int userId);
	
	public UserCredentials viewUserCredientialsDetails(int userId);

//	public boolean saveRole(String roleTitle, String roleDescription);
	
	public boolean saveUserToRole(int userId, int roleId);
	
	public String generatePassword();
	
	public boolean updateOneTimePassword(int userId, String password);
	
	public String getUserRole(int userId);
	
	public ViewUserModel findUserDetails(int userId);
	
	public List<Object>  getListofReporting();
	
	public List<Object> getListofRegion(int stateId, int roleId);
	
	public List<Object> getListofStates(int roleId);
	
	public List<Object> getListofRoles();

//	public List<Roles> getJoineeRole();
	
	public List<Object> getListofReportingTo(UserModel userModel);

//	public List<Roles> getListofAssigningroles(UserModel userModel);
	
	public boolean newUserActivationPassword(int userId, String password);
	
	public List<Region> getRegions();
	
//	public List<Object> ListOfTileIdGromRegionId(int stateId, int regionId);

	public String getRoleName(int roleId);
	
//	public String getEcoSystemName(int roleId);
	
	public List<Object> getselfReportingTo(UserModel userModel);
	
	public UserRoles getRole(int userId);
	
	public ResponseMessage forgotPasswordService(ForgotPasswordModel forgotPasswordModel);
	
	public ResponseMessage loginService(String emailAddress, String password, String channel, String jwtTokenUrl);
	
	public ResponseMessage resetPasswordService(int userId, String oldPassword, String newPassword);
	
	public ResponseMessage changePasswordService(int userId, String oldPassword, String newPassword);
	
	public ResponseMessage createUserService(CreateUserModel userModel);
	
	public ResponseMessage updateUserService(UpdateUserModel userModel);
	
	public ResponseMessage userListService(int userId);
	
	public ResponseMessage userActivationService(int userId, String comments);
	
	public ResponseMessage userInActivationService(int userId, String comments);
	
	public ResponseMessage createUserRoleService(int userId, int roleId);
	
	public List<Object> listOfRoles();

	public List<Object> getListofReportingRole(int roleId);

	public List<Object> reportingToService(  int reportingRoleId, int state, int region );
	
	public List<Object> showlistofStates();
	
	public List<Object> showlistofStates(int stateId);	
	
	public List<Object> changeRole(int userId);
	
	public boolean checkTaskCompletedAssigned( int userId );
	
	public boolean checkPrsTaskCompletedAssigned(int userId);
	
	public boolean getStatus( int userId );
	
}
