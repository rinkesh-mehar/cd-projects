package com.drkrishi.usermanagement.service;



public interface SmsService {

	public void userCreationNotification(String user, String mobileNumber, String loginUsername, String loginPassword);

	public void accountAccessNotification(String user, String mobileNumber);

	public void otpNotification(String user, String password, String mobileNumber);

	public void passwordUpdateNotification(String user, String mobileNumber);

	public void userActivationNotification(String user, String mobileNumber);

	public void userInActivationNotification(String user, String mobileNumber);

	public void userDeactivationFromLocked(String user, String mobileNumber);


	public void userUnlockedNotification(String user , String mobileNumber, String loginUsername, String loginPassword);

	public void accountLockedNotification(String user, String mobileNumber);

	public void roleUpdateNotification(String user, String mobileNumber, String oldRole, String newRole);

}
