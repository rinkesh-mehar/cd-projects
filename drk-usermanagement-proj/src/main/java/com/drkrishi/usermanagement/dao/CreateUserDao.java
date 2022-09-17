package com.drkrishi.usermanagement.dao;

import java.util.List;

import com.drkrishi.usermanagement.entity.UserCredentials;
import com.drkrishi.usermanagement.entity.Users;

public interface CreateUserDao {

	public int createUser(Users model);
	
	public boolean createUserCredientials(UserCredentials credientials );
	
//	public void createRole(RoleModel model, UserModel userModel);
	
	public boolean updateUserCredientials(UserCredentials credientials );
	
	public boolean updateUserDetails(Users credientials );
	
	public Users getUserByMobileNumber(String mobileNumber);
	
	public Users getUserByEmailAddress(String emailAddress);
	
	public Users viewUserDetails(int userId);
	
	public List<Users> getUserListByUserId(int userId);
	
	public int getInvalidLoginAttemptCount(int userId);
	
	public boolean validateEmailAddress(String emailAddress);
	
	public boolean validateMobileNumber(String password);
	
	public boolean validateUpdateMobileNumber(String mobileNumber, int userId);
	
	public boolean validateUpdateEmailAddress(String emailAddress, int userId);
	
	
	public boolean validateUserPassword(int userId, String password);
		
	public UserCredentials getUserCredientials(int userId );
	
	public List<Integer> getUserIdListByUserId();
	
	public List<Users> getAllUserList();
	
	public List<Users> getSpecificUserList(int userId);
	
	public List<Users> getlistOfReportingTo(int stateId, int regionId, int reportingRoleId);
	
	//public boolean userPasswordChange(DrKrishiUserCredientials krishiUserCredientials);
	
	//public boolean suspendUserAccount(DrKrishiUserCredientials credientials);
	
	//	public boolean updateInvalidLoginAttempt(DrKrishiUserCredientials credientials);
	//	public DrKrishiUserCredientials viewUserCredientialsDetails(int userId);	
	
	public List<Users> getReportingTo(int userId);
	
	public Users getReportingToByState(int userId, int state);
	
	public Users getReportingToByStateRegion(int userId, int state, int region);
	
	
	public List<Users> getReportingTo(int userId, int state, int region);

	public Users findById(int userId);

	public UserCredentials getUserCredentialsByid(int userId);

	public Users getUserByUserId(int userId);
	
	
}
