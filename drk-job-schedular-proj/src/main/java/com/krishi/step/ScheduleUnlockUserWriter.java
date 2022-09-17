package com.krishi.step;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.entity.DrKrishiUserCredientials;
import com.krishi.entity.DrKrishiUsers;
import com.krishi.repository.DrKrishiUserCredientialsRepository;
import com.krishi.repository.DrKrishiUsersRepository;
import com.krishi.service.SmsService;

public class ScheduleUnlockUserWriter implements ItemWriter<List<DrKrishiUsers>> {
	
	@Autowired
	private DrKrishiUsersRepository drKrishiUsersRepository;
	
	@Autowired
	private DrKrishiUserCredientialsRepository drKrishiUserCredientialsRepository;
	
	@Autowired
	private SmsService smsService;
	
	private static int passwordLength = 10;
	
	/*password encryption*/
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
	
	/*Generate new password*/
	public char[] generatePassword() {
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String Small_chars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%^&*_=+-/.?<>)";
		String values = Capital_chars + Small_chars + numbers + symbols;
		Random rndm_method = new Random();
		char[] password = new char[passwordLength];

		for (int i = 0; i < passwordLength; i++) {
			password[i] = values.charAt(rndm_method.nextInt(values.length()));
		}
		return password;
	}
	

	/*Unlock the user and update into database*/
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void write(List<? extends List<DrKrishiUsers>> items) throws Exception {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		for(List<DrKrishiUsers> users:items) {
			for(DrKrishiUsers user:users) {
				user.setStatus(1);
				
				user.setModifiedDateTime(stamp);
				drKrishiUsersRepository.saveAndFlush(user);
				DrKrishiUserCredientials credential = drKrishiUserCredientialsRepository.findByUserId(user.getId());
				credential.setInvalidAttempts(0);
				String newPassword = generatePassword().toString();
				String encNewPassword = passwordEncrypt(newPassword);
				credential.setIsForcedPwdChange(0);
				credential.setUserPassword(encNewPassword);
				credential.setModifiedDateTime(stamp);
				credential.setTransactionType(3);
				
				drKrishiUserCredientialsRepository.saveAndFlush(credential);
				smsService.autoAccountUnlockNotification(user.getFirstName(), user.getMobileNumber(), user.getMobileNumber(), newPassword);
			}
		}
	}

}
