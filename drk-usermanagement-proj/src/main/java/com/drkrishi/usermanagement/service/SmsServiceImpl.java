package com.drkrishi.usermanagement.service;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drkrishi.usermanagement.dao.SMSDao;
import com.drkrishi.usermanagement.entity.SmsMessage;
import com.drkrishi.usermanagement.entity.SmsTemplate;



@Service
public class SmsServiceImpl implements SmsService {

	private static final Logger LOGGER = LogManager.getLogger(SmsServiceImpl.class);

	@Autowired
	private SMSDao smsDao;

	String namePlaceHolder = "[name]";
	String passwordPlaceHolder = "[password]";

	String oldRolePlaceHolder = "[oldrole]";
	String newRolePlaceHolder = "[newrole]";

	String loginUsernamePlaceHolder = "[username]";
	String loginPasswordPlaceHolder = "[password]";

	@Override
	public void accountAccessNotification(String user, String mobileNumber) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("AccountAccessAttempt");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}
			LOGGER.debug(message);

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

	}

	@Override
	public void accountLockedNotification(String user, String mobileNumber) {

		try {

			SmsTemplate smsTemplate = smsDao.getSMSTemplate("AccountLocked");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			LOGGER.debug(message);

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
	}

	@Override
	public void otpNotification(String user, String password, String mobileNumber) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("OneTimePassword");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			if ( message.contains(passwordPlaceHolder) ) {
				message = message.replace(passwordPlaceHolder, password );
			}

			LOGGER.debug(message);

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
	}

	@Override
	public void passwordUpdateNotification(String user, String mobileNumber) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("PasswordUpdate");
			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

	}

	@Override
	public void roleUpdateNotification(String user, String mobileNumber, String oldRole, String newRole) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("RoleUpdate");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			if ( message.contains(oldRolePlaceHolder) ) {
				message = message.replace(oldRolePlaceHolder, oldRole );
			}

			if ( message.contains(newRolePlaceHolder) ) {
				message = message.replace(newRolePlaceHolder, newRole );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
	}

	@Override
	public void userActivationNotification(String user, String mobileNumber) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("UserActivation");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

	}


	@Override
	public void userInActivationNotification(String user, String mobileNumber) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("UserDeactivation");
			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}



	}

	@Override
	public void userDeactivationFromLocked(String user, String mobileNumber) {

		try {

			SmsTemplate smsTemplate = smsDao.getSMSTemplate("UserDeactivationFromLocked");
			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

	}

	@Override
	public void userUnlockedNotification(String user, String mobileNumber, String loginUsername, String loginPassword) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("UserUnLocked");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			if ( message.contains(loginUsernamePlaceHolder) ) {
				message = message.replace(loginUsernamePlaceHolder, loginUsername );
			}

			if ( message.contains(loginPasswordPlaceHolder) ) {
				message = message.replace(loginPasswordPlaceHolder, loginPassword );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
	}

	@Override
	public void userCreationNotification(String user, String mobileNumber, String loginUsername, String loginPassword) {

		try {
			SmsTemplate smsTemplate = smsDao.getSMSTemplate("UserCreation");

			String message = smsTemplate.getMessage();

			if ( message.contains(namePlaceHolder) ) {
				message = message.replace(namePlaceHolder, user );
			}

			if ( message.contains(loginUsernamePlaceHolder) ) {
				message = message.replace(loginUsernamePlaceHolder, loginUsername );
			}

			if ( message.contains(loginPasswordPlaceHolder) ) {
				message = message.replace(loginPasswordPlaceHolder, loginPassword );
			}

			SmsMessage smsMessage = new SmsMessage(0, mobileNumber, message, 0, 0, 0, "", new Timestamp(new Date().getTime()));
			smsDao.saveSMSMessage(smsMessage);

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

	}
}
